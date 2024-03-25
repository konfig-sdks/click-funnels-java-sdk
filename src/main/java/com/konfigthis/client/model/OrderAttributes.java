/*
 * ClickFunnels API
 * # Introduction  The ClickFunnels v2 API lets you:  - import data from other apps and sources into ClickFunnels and export data that you need somewhere else - extend the ClickFunnels platform to your own needs and embed it in your own applications - act on behalf of other ClickFunnels users via OAuth to offer extended services to other fellow ClickFunnels entrepreneurs  We are starting with exposing a given set of resources but the goal is to converge in terms of functionality with what the actual app is offering and also offering functionality on top.  For any feedback, please drop us a line at:  - https://feedback.myclickfunnels.com/feature-requests?category=api  For issues and support you can currently go here:  - https://help.clickfunnels.com/hc/en-us  # Authentication  Making your first request is easiest with a Bearer token:  ```shell $ curl 'https://myteam.myclickfunnels.com/api/v2/teams' \\ --header 'Authorization: Bearer AVJrj0ZMJ-xoraUk1xxVM6UuL9KXmsWmnJvvSosUO6X' [{\"id\":3,\"name\":\"My Team\", # ... more output...}] ```  How to get your API key step by step:  https://developers.myclickfunnels.com/docs/getting-started  # Rate limiting  The rate limit is currently set per IP address.  The actual rate limit and the approach on how this is handled is subject to change in future releases. Please let us know if you have special request limit needs.  # Pagination and Ordering  In order to paginate through a large list response, you can use our cursor-based pagination using the `id` field of a given object in the list.  There is a limit of 20 objects per list response ordered ascending by ID. So, you can get to items after the last one in the list, by taking the last item's ID and submitting it in a list request as the value of an `after` URL parameter. For example:  ```shell # The first 20 contacts are returned without any pagination nor ordering params: $ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts' --header 'Authorization: Bearer ...' [{\"id\": 1, \"email_address\": \"first@contact.com\" ...}, {\"id\": 4, ...} ... {\"id\": 55, \"email_address\": \"last@contact.com\", ...}]  $ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?after=55' --header 'Authorization: Bearer ...' [{\"id\": 56, ...}] # There is one more record after ID 55. ```  The `after` param always acts as if you are \"turning the next page\". So if you order in a descending order, you will also use `after` to get to the next records:  ```shell $ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?sort_order=desc' --header 'Authorization: Bearer ...' [{\"id\": 56, ...},  {\"id\": 55, ...}, {\"id\": 4, ...}] # All contacts in descending order.  $ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?sort_order=desc&after=4' --header 'Authorization: Bearer ...' [{\"id\": 1, ...}] # There is one more contact on the next page after ID 55. ```  You can also use the `Pagination-Next` header to get the last ID value directly:  ```http request # Example header. Pagination-Next: 55 ```  And you can use the `Link` header to get the next page directly without needing to calculate it yourself:  ```http request # Example header. Link: <https://localteam.myclickfunnels.com/api/v2/workspaces/3/contacts?after=55>; rel=\"next\" ```  # Filtering  **Current filters**  If filtering is available for a specific endpoint, 'filter' will be listed as one of the options in the query parameters section of the Request area. Attributes by which you can filter will be listed as well.  **How it works**  There is a filter mechanism that adheres to some simple conventions. The filters provided on list endpoints, like `filter[email_address]` and `filter[id]` on the `Contacts` list endpoint, need to be \"simple\" and \"fast\". These filters are supposed to be easy to use and allow you to filter by one or more concrete values.  Here's an example of how you could use the filter to find a contact with a certain email address:  ```shell $ curl -g 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?filter[email_address]=you@cf.com' --header 'Authorization: Bearer ...' [{\"email_address\": \"you@cf.com\",...}] ```  You can also filter by multiple values:  ```shell $ curl -g 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?filter[email_address]=you@cf.com,u2@cf.com' --header 'Authorization: Bearer ...' [{\"email_address\": \"you@cf.com\",...}, {\"email_address\": \"u2@cf.com\",...}] ```  You can also filter by multiple attributes. Similar to filters that you might be familiar with when using GitHub (e.g.: filtering PRs by closed and assignee), those filters are `AND` filters, which give you the intersection of multiple records:  ```shell # If you@cf.com comes with an ID of 1, you will only see this record for this API call: $ curl -g 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?filter[email_address]=you@cf.com,u2@cf.com&filter[id]=1' --header 'Authorization: Bearer ...' [{\"email_address\": \"you@cf.com\",...}]  # u2@cf.com is not included because it has a different ID that is not included in the filter. ```  > Please let us know your use case if you need more filter or complex search capabilities, we are  > actively improving these areas: https://feedback.myclickfunnels.com/feature-requests?category=api  # Webhooks  ClickFunnels webhooks allow you to react to many events in the ClickFunnels app on your own server,  Zapier and other similar tools.  You need to configure one or more endpoints within the ClickFunnels API by using the [Webhooks::Outgoing::Endpoints](https://apidocs.myclickfunnels.com/tag/Webhooks::Outgoing::Endpoint)  endpoint with the `event_type_ids` that you want to listen to (see below for all types).  Once configured, you will receive POST requrests from us to the configured endpoint URL with the [Webhooks::Outgoing::Event](https://apidocs.myclickfunnels.com/tag/Webhooks::Outgoing::Event#operation/getWebhooksOutgoingEvents)  payload, that will contain the subject payload in the `data` property. Like here for the `contact.identified` webhook in V2 version:  ```json {   \"id\": null,   \"public_id\": \"YVIOwX\",   \"workspace_id\": 32,   \"uuid\": \"94856650751bb2c141fc38436fd699cb\",   \"event_type_id\": \"contact.identified\",   \"subject_id\": 100,   \"subject_type\": \"Contact\",   \"data\": {     \"id\": 12,     \"public_id\": \"fdPJAZ\",     \"workspace_id\": 32,     \"anonymous\": null,     \"email_address\": \"joe.doe@example.com\",     \"first_name\": \"Joe\",     \"last_name\": \"Doe\",     \"phone_number\": \"1-241-822-5555\",     \"time_zone\": \"Pacific Time (US & Canada)\",     \"uuid\": \"26281ba2-7d3b-524d-8ea3-b01ff8414120\",     \"unsubscribed_at\": null,     \"last_notification_email_sent_at\": null,     \"fb_url\": \"https://www.facebook.com/example\",     \"twitter_url\": \"https://twitter.com/example\",     \"instagram_url\": \"https://instagram.com/example\",     \"linkedin_url\": \"https://www.linkedin.com/in/example\",     \"website_url\": \"https://example.com\",     \"created_at\": \"2023-12-31T18:57:40.871Z\",     \"updated_at\": \"2023-12-31T18:57:40.872Z\",     \"tags\": [       {         \"id\": 20,         \"public_id\": \"bRkQrc\",         \"name\": \"Example Tag\",         \"color\": \"#59b0a8\"       }     ]   },   \"created_at\": \"2023-12-31T18:57:41.872Z\" } ```  The content of the `data` property will vary depending on the event type that you are receiving.  Event types are structured like this: `subject.action`. So, for a `contact.identified` webhook, your `data` payload will contain data that you can source from [the contact response schema/example in the documentation](https://apidocs.myclickfunnels.com/tag/Contact#operation/getContacts). Similarly, for webhooks like `order.created` and `one-time-order.identified`, you will find the documentation in [the Order resource description](https://apidocs.myclickfunnels.com/tag/Order#operation/getOrders).  **Contact webhooks**  Are delivered with [the Contact data payload](https://apidocs.myclickfunnels.com/tag/Contact#operation/getContacts).  | <div style=\"width:375px;\">Event type</div>| Versions available | Description                                                            |  |--------------------------------------------------|--------------------|------------------------------------------------------------------------| | ***Contact***                                    |                    |                                                                        | | `contact.created`                                | V1, V2             | Sent when a Contact is created                                         | | `contact.updated`                                | V1, V2             | Sent when a Contact is updated                                         | | `contact.deleted`                                | V1, V2             | Sent when a Contact is deleted                                         | | `contact.identified`                             | V1, V2             | Sent when a Contact is identified by email address and/or phone number | | `contact.unsubscribed`                           | V1, V2             | Sent when a Contact unsubscribes from getting communications from the ClickFunnels workspace                         |  **Contact::AppliedTag webhooks**  Are delivered with [the Contact::AppliedTag data payload](https://apidocs.myclickfunnels.com/tag/Contacts::AppliedTag#operation/getContactsAppliedTags)  | <div style=\"width:375px;\">Event type</div>| Versions available | Description                                                            | |--------------------------------------------------|--------------------|------------------------------------------------------------------------| | ***Contacts::AppliedTag***                       |                    |                                                                        | | `contact/applied_tag.created`                    | V2                 | Sent when a Contacts::AppliedTag is created                            | | `contact/applied_tag.deleted`                    | V2                 | Sent when a Contacts::AppliedTag is deleted  **Courses webhooks**  Payloads correspond to the respective API resources:  - [Course](https://apidocs.myclickfunnels.com/tag/Course#operation/getCourses) - [Courses::Enrollment](https://apidocs.myclickfunnels.com/tag/Courses::Enrollment#operation/getCoursesEnrollments) - [Courses::Section](https://apidocs.myclickfunnels.com/tag/Courses::Section#operation/getCoursesSections) - [Courses::Lesson](https://apidocs.myclickfunnels.com/tag/Courses::Lesson#operation/getCoursesLessons)  | <div style=\"width:375px;\">Event type</div>| Versions available | Description                                                            |  |--------------------------------------------------|--------------------|------------------------------------------------------------------------| | ***Course***                                     |                    |                                                                        | | `course.created`                                 | V2             | Sent when a Course is created                                          | | `course.updated`                                 | V2             | Sent when a Course is updated                                          | | `course.deleted`                                 | V2             | Sent when a Course is deleted                                          | | `course.published`                               | V2                 | Sent when a Course has been published                                  | | ***Courses::Enrollment***                        |                    |                                                                        | | `courses/enrollment.created`                     | V2             | Sent when a Course::Enrollment is created                              | | `courses/enrollment.updated`                     | V2             | Sent when a Course::Enrollment is updated                              | | `courses/enrollment.deleted`                     | V2             | Sent when a Course::Enrollment is deleted                              | | `courses/enrollment.suspended`                   | V2                 | Sent when a Course::Enrollment has been suspended                      | | `courses/enrollment.course_completed`                   | V2                 | Sent when a Course::Enrollment completes a course                      | |  ***Courses::Section***                           |                    |                                                                        | | `courses/section.created`                        | V2             | Sent when a Courses::Section is created                                | | `courses/section.updated`                        | V2             | Sent when a Courses::Section is updated                                | | `courses/section.deleted`                        | V2             | Sent when a Courses::Section is deleted                                | | `courses/section.published`                       | V2                 | Sent when a Courses::Lesson has been published                         | |                      | ***Courses::Lesson***                            |                    |                                                                        | | `courses/lesson.created`                         | V2             | Sent when a Courses::Lesson is created                                 | | `courses/lesson.updated`                         | V2             | Sent when a Courses::Lesson is updated                                 | | `courses/lesson.deleted`                         | V2             | Sent when a Courses::Lesson is deleted                                 | | `courses/lesson.published`                       | V2                 | Sent when a Courses::Lesson has been published                         | |                      |  **Form submission webhooks**  Currently only available in V1 with the following JSON payload sample:  ```json {   \"data\": {     \"id\": \"4892034\",     \"type\": \"form_submission\",     \"attributes\": {       \"id\": 9874322,       \"data\": {         \"action\": \"submit\",         \"contact\": {           \"email\": \"joe.doe@example.com\",           \"aff_sub\": \"43242122e8c15480e9117143ce806d111\"         },         \"controller\": \"user_pages/pages\",         \"redirect_to\": \"https://www.example.com/thank-you-newsletter\"       },       \"page_id\": 2342324,       \"contact_id\": 234424,       \"created_at\": \"2023-11-14T23:25:54.070Z\",       \"updated_at\": \"2023-11-14T23:25:54.134Z\",       \"workspace_id\": 11     }   },   \"event_id\": \"bb50ab45-3da8-4532-9d7e-1c85d159ee71\",   \"event_type\": \"form_submission.created\",   \"subject_id\": 9894793,   \"subject_type\": \"FormSubmission\" } ```  | <div style=\"width:375px;\">Event type</div>| Versions available | Description                             |  |--------------------------------------------------|--------------------|-----------------------------------------| | ***Form::Submission***                           |                    |                                         | | `form/submission.created`                        | V1                 | Sent when a Form::Submission is created |  **Order webhooks**  Subscriptions and orders are all of type \"Order\" and their payload will be as in the [Order resources response payload](https://apidocs.myclickfunnels.com/tag/Order#operation/getOrders).  | <div style=\"width:375px;\">Event type</div> | Versions available | Description                                                                                               |  |--------------------------------------------|--------------------|-----------------------------------------------------------------------------------------------------------| | ***Order***                                |                    |                                                                                                           | | `order.created`                            | V1, V2             | Sent when an Order has been created                                                                       | | `order.updated`                            | V1, V2             | Sent when an Order has been updated                                                                       | | `order.deleted`                            | V1, V2             | Sent when an Order has been deleted                                                                       | | `order.completed`                          | V1, V2             | Sent when a one-time order was paid or a subscription order's service period has concluded                | | ***One-Time Order***                       |                    |                                                                                                           | | `one-time-order.completed`                 | V1, V2             | Sent when an Order of `order_type: \"one-time-order\"` has been completed                                   | | `one_time_order.refunded`                  | V1, V2             | Sent when an Order of `order_type: \"one-time-order\"` refund has been issued                               | | ***Subscription***                         |                    |                                                                                                           | | `subscription.canceled`                    | V1, V2             | Sent when an Order of `order_type: \"subscription\"` has been canceled                                      | | `subscription.reactivated`                 | V1, V2             | Sent when an Order of `order_type: \"subscription\"` that was canceled was reactivated                      | | `subscription.downgraded`                  | V1, V2             | Sent when an Order of `order_type: \"subscription\"` is changed to a product of smaller value               | | `subscription.upgraded`                    | V1, V2             | Sent when an Order of `order_type: \"subscription\"` is changed to a product of higher value                | | `subscription.churned`                     | V1, V2             | Sent when an Order of `order_type: \"subscription\"` has been churned                                       | | `subscription.modified`                    | V1, V2             | Sent when an Order of `order_type: \"subscription\"` has been modified                                      | | `subscription.activated`                   | V1, V2             | Sent when an Order of `order_type: \"subscription\"` has been activated                                     | | `subscription.completed`                   | V1, V2             | Sent when an Order of `order_type: \"subscription\"` has been completed                                     | | `subscription.first_payment_received`      | V1, V2             | Sent when an Order of `order_type: \"subscription\"` received first non-setup payment for subscription item |  **Orders::Transaction Webhooks**  Orders transactions are currently not part of our V2 API, but you can refer to this sample V2 webhook data payload:   ```json {   \"id\": 1821233,   \"arn\": null,   \"amount\": \"200.00\",   \"reason\": null,   \"result\": \"approved\",   \"status\": \"completed\",   \"currency\": \"USD\",   \"order_id\": 110030,   \"is_rebill\": false,   \"public_id\": \"asLOAY\",   \"created_at\": \"2024-01-30T06:25:06.754Z\",   \"updated_at\": \"2024-01-30T06:25:06.754Z\",   \"external_id\": \"txn_01HNCGNQE2C234PCFNERD2AVFZ\",   \"external_type\": \"sale\",   \"rebill_number\": 0,   \"adjusted_transaction_id\": null,   \"billing_payment_instruction_id\": 1333223,   \"billing_payment_instruction_type\": \"Billing::PaymentMethod\" } ```  | <div style=\"width:375px;\">Event type</div>                            | Versions available | Description                                       |  |---------------------------------------|--------------------|---------------------------------------------------| | ***Orders::Transaction***                      |                    |                                                   | | `orders/transaction.created`                   | V1, V2             | Sent when an Orders::Transaction has been created | | `orders/transaction.updated`                   | V1, V2             | Sent when an Orders::Transaction has been updated |  **Invoice webhooks**  With the [Invoice payload](https://apidocs.myclickfunnels.com/tag/Orders::Invoice).  | <div style=\"width:375px;\">Event type</div>   | Versions available   | Description                                                                 |  |----------------------------------------------|----------------------|-----------------------------------------------------------------------------| | ***Orders::Invoice***                        |                      |                                                                             | | `orders/invoice.created`                     | V1, V2               | Sent when an Orders::Invoice has been created                               | | `orders/invoice.updated`                     | V1, V2               | Sent when an Orders::Invoice has been updated                               | | `orders/invoice.refunded`                    | V1, V2               | Sent when an Orders::Invoice has been refunded                              | | `renewal-invoice-payment-declined`           | V1, V2               | Issued when a renewal Orders::Invoice payment has been declined             | | ***OneTimeOrder::Invoice***                  |                      |                                                                             | | `one-time-order.invoice.paid`                | V1, V2               | Sent when an Order::Invoice of `order_type: \"one-time-order\"` has been paid | | ***Subscription::Invoice***                  |                      |                                                                             | | `subscription/invoice.paid`                  | V1, V2               | Sent when an Order of `order_type: \"subscription\"` has been paid            |  **Workflow-based webhooks**  These are mostly used for [the UI-based ClickFunnels Workflows functionality](https://support.myclickfunnels.com/support/solutions/articles/150000156983-using-the-webhook-step-in-a-workflow).  | <div style=\"width:375px;\">Event type</div>| Versions available | Description                                                        |  |--------------------------------------------------|--------------------|--------------------------------------------------------------------|  | ***Runs::Step***                                 |                    |                                                                    | | `runs/step.dontrunme`                            | V1                 | Issued when the `dontrunme` step has been ran on a Workflow        | | ***Workflows::Integration::Step***               |                    |                                                                    | | `workflows_integration_step.executed`            | V1                 | Sent when a Workflows::Integration::Step has been executed         | | ***Workflows::Steps::IntegrationStep***          |                    |                                                                    | | `workflows/steps/integration_step.executed`      | V1                 | Sent when a Workflows::Steps::IntegrationStep has been executed    | | ***Workflows::Steps::DeliverWebhookStep***       |                    |                                                                    | | `workflows/steps/deliver_webhook_step.executed`  | V1                 | Sent when a Workflows::Steps::DeliverWebhookStep has been executed | 
 *
 * The version of the OpenAPI document: V2
 * 
 *
 * NOTE: This class is auto generated by Konfig (https://konfigthis.com).
 * Do not edit the class manually.
 */


package com.konfigthis.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.konfigthis.client.model.ContactAttributes;
import com.konfigthis.client.model.OrderContactGroupAttributes;
import com.konfigthis.client.model.OrderSegmentAttributes;
import com.konfigthis.client.model.OrdersLineItemAttributes;
import com.konfigthis.client.model.PagesProperty;
import com.konfigthis.client.model.WorkspaceAttributes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.konfigthis.client.JSON;

/**
 * Orders
 */
@ApiModel(description = "Orders")@javax.annotation.Generated(value = "Generated by https://konfigthis.com")
public class OrderAttributes {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_PUBLIC_ID = "public_id";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ID)
  private Object publicId = null;

  public static final String SERIALIZED_NAME_ORDER_NUMBER = "order_number";
  @SerializedName(SERIALIZED_NAME_ORDER_NUMBER)
  private Object orderNumber = null;

  public static final String SERIALIZED_NAME_WORKSPACE_ID = "workspace_id";
  @SerializedName(SERIALIZED_NAME_WORKSPACE_ID)
  private Integer workspaceId;

  public static final String SERIALIZED_NAME_CONTACT_ID = "contact_id";
  @SerializedName(SERIALIZED_NAME_CONTACT_ID)
  private Integer contactId;

  public static final String SERIALIZED_NAME_TOTAL_AMOUNT = "total_amount";
  @SerializedName(SERIALIZED_NAME_TOTAL_AMOUNT)
  private Object totalAmount = null;

  public static final String SERIALIZED_NAME_CURRENCY = "currency";
  @SerializedName(SERIALIZED_NAME_CURRENCY)
  private String currency;

  public static final String SERIALIZED_NAME_ORIGINATION_CHANNEL_ID = "origination_channel_id";
  @SerializedName(SERIALIZED_NAME_ORIGINATION_CHANNEL_ID)
  private Integer originationChannelId;

  public static final String SERIALIZED_NAME_ORIGINATION_CHANNEL_TYPE = "origination_channel_type";
  @SerializedName(SERIALIZED_NAME_ORIGINATION_CHANNEL_TYPE)
  private Object originationChannelType = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_FIRST_NAME = "shipping_address_first_name";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_FIRST_NAME)
  private Object shippingAddressFirstName = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_LAST_NAME = "shipping_address_last_name";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_LAST_NAME)
  private Object shippingAddressLastName = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_ORGANIZATION_NAME = "shipping_address_organization_name";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_ORGANIZATION_NAME)
  private Object shippingAddressOrganizationName = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_PHONE_NUMBER = "shipping_address_phone_number";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_PHONE_NUMBER)
  private Object shippingAddressPhoneNumber = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_STREET_ONE = "shipping_address_street_one";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_STREET_ONE)
  private Object shippingAddressStreetOne = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_STREET_TWO = "shipping_address_street_two";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_STREET_TWO)
  private Object shippingAddressStreetTwo = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_CITY = "shipping_address_city";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_CITY)
  private Object shippingAddressCity = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_REGION = "shipping_address_region";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_REGION)
  private Object shippingAddressRegion = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_COUNTRY = "shipping_address_country";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_COUNTRY)
  private Object shippingAddressCountry = null;

  public static final String SERIALIZED_NAME_SHIPPING_ADDRESS_POSTAL_CODE = "shipping_address_postal_code";
  @SerializedName(SERIALIZED_NAME_SHIPPING_ADDRESS_POSTAL_CODE)
  private Object shippingAddressPostalCode = null;

  public static final String SERIALIZED_NAME_BILLING_ADDRESS_STREET_ONE = "billing_address_street_one";
  @SerializedName(SERIALIZED_NAME_BILLING_ADDRESS_STREET_ONE)
  private Object billingAddressStreetOne = null;

  public static final String SERIALIZED_NAME_BILLING_ADDRESS_STREET_TWO = "billing_address_street_two";
  @SerializedName(SERIALIZED_NAME_BILLING_ADDRESS_STREET_TWO)
  private Object billingAddressStreetTwo = null;

  public static final String SERIALIZED_NAME_BILLING_ADDRESS_CITY = "billing_address_city";
  @SerializedName(SERIALIZED_NAME_BILLING_ADDRESS_CITY)
  private Object billingAddressCity = null;

  public static final String SERIALIZED_NAME_BILLING_ADDRESS_REGION = "billing_address_region";
  @SerializedName(SERIALIZED_NAME_BILLING_ADDRESS_REGION)
  private Object billingAddressRegion = null;

  public static final String SERIALIZED_NAME_BILLING_ADDRESS_COUNTRY = "billing_address_country";
  @SerializedName(SERIALIZED_NAME_BILLING_ADDRESS_COUNTRY)
  private Object billingAddressCountry = null;

  public static final String SERIALIZED_NAME_BILLING_ADDRESS_POSTAL_CODE = "billing_address_postal_code";
  @SerializedName(SERIALIZED_NAME_BILLING_ADDRESS_POSTAL_CODE)
  private Object billingAddressPostalCode = null;

  public static final String SERIALIZED_NAME_PAGE_ID = "page_id";
  @SerializedName(SERIALIZED_NAME_PAGE_ID)
  private Object pageId = null;

  public static final String SERIALIZED_NAME_NOTES = "notes";
  @SerializedName(SERIALIZED_NAME_NOTES)
  private Object notes = null;

  public static final String SERIALIZED_NAME_IN_TRIAL = "in_trial";
  @SerializedName(SERIALIZED_NAME_IN_TRIAL)
  private Object inTrial = null;

  public static final String SERIALIZED_NAME_BILLING_STATUS = "billing_status";
  @SerializedName(SERIALIZED_NAME_BILLING_STATUS)
  private Object billingStatus = null;

  public static final String SERIALIZED_NAME_SERVICE_STATUS = "service_status";
  @SerializedName(SERIALIZED_NAME_SERVICE_STATUS)
  private Object serviceStatus = null;

  public static final String SERIALIZED_NAME_ORDER_TYPE = "order_type";
  @SerializedName(SERIALIZED_NAME_ORDER_TYPE)
  private Object orderType = null;

  public static final String SERIALIZED_NAME_NEXT_CHARGE_AT = "next_charge_at";
  @SerializedName(SERIALIZED_NAME_NEXT_CHARGE_AT)
  private Object nextChargeAt = null;

  public static final String SERIALIZED_NAME_TAX_AMOUNT = "tax_amount";
  @SerializedName(SERIALIZED_NAME_TAX_AMOUNT)
  private Object taxAmount = null;

  public static final String SERIALIZED_NAME_TRIAL_END_AT = "trial_end_at";
  @SerializedName(SERIALIZED_NAME_TRIAL_END_AT)
  private Object trialEndAt = null;

  public static final String SERIALIZED_NAME_BILLING_PAYMENT_METHOD_ID = "billing_payment_method_id";
  @SerializedName(SERIALIZED_NAME_BILLING_PAYMENT_METHOD_ID)
  private Object billingPaymentMethodId = null;

  public static final String SERIALIZED_NAME_FUNNEL_NAME = "funnel_name";
  @SerializedName(SERIALIZED_NAME_FUNNEL_NAME)
  private Object funnelName = null;

  public static final String SERIALIZED_NAME_TAG_IDS = "tag_ids";
  @SerializedName(SERIALIZED_NAME_TAG_IDS)
  private Object tagIds = null;

  public static final String SERIALIZED_NAME_DISCOUNT_IDS = "discount_ids";
  @SerializedName(SERIALIZED_NAME_DISCOUNT_IDS)
  private Object discountIds = null;

  public static final String SERIALIZED_NAME_ACTIVATED_AT = "activated_at";
  @SerializedName(SERIALIZED_NAME_ACTIVATED_AT)
  private Object activatedAt = null;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private Object createdAt = null;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private Object updatedAt = null;

  public static final String SERIALIZED_NAME_PHONE_NUMBER = "phone_number";
  @SerializedName(SERIALIZED_NAME_PHONE_NUMBER)
  private Object phoneNumber = null;

  public static final String SERIALIZED_NAME_PAGE_NAME = "page_name";
  @SerializedName(SERIALIZED_NAME_PAGE_NAME)
  private Object pageName = null;

  public static final String SERIALIZED_NAME_ORIGINATION_CHANNEL_NAME = "origination_channel_name";
  @SerializedName(SERIALIZED_NAME_ORIGINATION_CHANNEL_NAME)
  private Object originationChannelName = null;

  public static final String SERIALIZED_NAME_ORDER_PAGE = "order_page";
  @SerializedName(SERIALIZED_NAME_ORDER_PAGE)
  private PagesProperty orderPage;

  public static final String SERIALIZED_NAME_CONTACT = "contact";
  @SerializedName(SERIALIZED_NAME_CONTACT)
  private ContactAttributes contact;

  public static final String SERIALIZED_NAME_CONTACT_GROUPS = "contact_groups";
  @SerializedName(SERIALIZED_NAME_CONTACT_GROUPS)
  private List<OrderContactGroupAttributes> contactGroups = null;

  public static final String SERIALIZED_NAME_WORKSPACE = "workspace";
  @SerializedName(SERIALIZED_NAME_WORKSPACE)
  private WorkspaceAttributes workspace;

  public static final String SERIALIZED_NAME_SEGMENTS = "segments";
  @SerializedName(SERIALIZED_NAME_SEGMENTS)
  private List<OrderSegmentAttributes> segments = null;

  public static final String SERIALIZED_NAME_LINE_ITEMS = "line_items";
  @SerializedName(SERIALIZED_NAME_LINE_ITEMS)
  private List<OrdersLineItemAttributes> lineItems = null;

  public static final String SERIALIZED_NAME_PREVIOUS_LINE_ITEM = "previous_line_item";
  @SerializedName(SERIALIZED_NAME_PREVIOUS_LINE_ITEM)
  private Object previousLineItem = null;

  public OrderAttributes() {
  }

  public OrderAttributes id(Integer id) {
    
    
    
    
    this.id = id;
    return this;
  }

   /**
   * ClickFunnels Order ID
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "2", required = true, value = "ClickFunnels Order ID")

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    
    
    
    this.id = id;
  }


  public OrderAttributes publicId(Object publicId) {
    
    
    
    
    this.publicId = publicId;
    return this;
  }

   /**
   * ClickFunnels Order public ID
   * @return publicId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "ClickFunnels Order public ID")

  public Object getPublicId() {
    return publicId;
  }


  public void setPublicId(Object publicId) {
    
    
    
    this.publicId = publicId;
  }


  public OrderAttributes orderNumber(Object orderNumber) {
    
    
    
    
    this.orderNumber = orderNumber;
    return this;
  }

   /**
   * Order Number
   * @return orderNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Order Number")

  public Object getOrderNumber() {
    return orderNumber;
  }


  public void setOrderNumber(Object orderNumber) {
    
    
    
    this.orderNumber = orderNumber;
  }


  public OrderAttributes workspaceId(Integer workspaceId) {
    
    
    
    
    this.workspaceId = workspaceId;
    return this;
  }

   /**
   * ClickFunnels Workspace ID
   * @return workspaceId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "42", required = true, value = "ClickFunnels Workspace ID")

  public Integer getWorkspaceId() {
    return workspaceId;
  }


  public void setWorkspaceId(Integer workspaceId) {
    
    
    
    this.workspaceId = workspaceId;
  }


  public OrderAttributes contactId(Integer contactId) {
    
    
    
    
    this.contactId = contactId;
    return this;
  }

   /**
   * Customer
   * @return contactId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "8", required = true, value = "Customer")

  public Integer getContactId() {
    return contactId;
  }


  public void setContactId(Integer contactId) {
    
    
    
    this.contactId = contactId;
  }


  public OrderAttributes totalAmount(Object totalAmount) {
    
    
    
    
    this.totalAmount = totalAmount;
    return this;
  }

   /**
   * Total
   * @return totalAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Total")

  public Object getTotalAmount() {
    return totalAmount;
  }


  public void setTotalAmount(Object totalAmount) {
    
    
    
    this.totalAmount = totalAmount;
  }


  public OrderAttributes currency(String currency) {
    
    
    
    
    this.currency = currency;
    return this;
  }

   /**
   * Currency
   * @return currency
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "USD", required = true, value = "Currency")

  public String getCurrency() {
    return currency;
  }


  public void setCurrency(String currency) {
    
    
    
    this.currency = currency;
  }


  public OrderAttributes originationChannelId(Integer originationChannelId) {
    
    
    
    
    this.originationChannelId = originationChannelId;
    return this;
  }

   /**
   * Origination Channel ID
   * @return originationChannelId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "3", required = true, value = "Origination Channel ID")

  public Integer getOriginationChannelId() {
    return originationChannelId;
  }


  public void setOriginationChannelId(Integer originationChannelId) {
    
    
    
    this.originationChannelId = originationChannelId;
  }


  public OrderAttributes originationChannelType(Object originationChannelType) {
    
    
    
    
    this.originationChannelType = originationChannelType;
    return this;
  }

   /**
   * Origination Channel Type
   * @return originationChannelType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Origination Channel Type")

  public Object getOriginationChannelType() {
    return originationChannelType;
  }


  public void setOriginationChannelType(Object originationChannelType) {
    
    
    
    this.originationChannelType = originationChannelType;
  }


  public OrderAttributes shippingAddressFirstName(Object shippingAddressFirstName) {
    
    
    
    
    this.shippingAddressFirstName = shippingAddressFirstName;
    return this;
  }

   /**
   * First Name
   * @return shippingAddressFirstName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "First Name")

  public Object getShippingAddressFirstName() {
    return shippingAddressFirstName;
  }


  public void setShippingAddressFirstName(Object shippingAddressFirstName) {
    
    
    
    this.shippingAddressFirstName = shippingAddressFirstName;
  }


  public OrderAttributes shippingAddressLastName(Object shippingAddressLastName) {
    
    
    
    
    this.shippingAddressLastName = shippingAddressLastName;
    return this;
  }

   /**
   * Last Name
   * @return shippingAddressLastName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Last Name")

  public Object getShippingAddressLastName() {
    return shippingAddressLastName;
  }


  public void setShippingAddressLastName(Object shippingAddressLastName) {
    
    
    
    this.shippingAddressLastName = shippingAddressLastName;
  }


  public OrderAttributes shippingAddressOrganizationName(Object shippingAddressOrganizationName) {
    
    
    
    
    this.shippingAddressOrganizationName = shippingAddressOrganizationName;
    return this;
  }

   /**
   * Organization Name
   * @return shippingAddressOrganizationName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Organization Name")

  public Object getShippingAddressOrganizationName() {
    return shippingAddressOrganizationName;
  }


  public void setShippingAddressOrganizationName(Object shippingAddressOrganizationName) {
    
    
    
    this.shippingAddressOrganizationName = shippingAddressOrganizationName;
  }


  public OrderAttributes shippingAddressPhoneNumber(Object shippingAddressPhoneNumber) {
    
    
    
    
    this.shippingAddressPhoneNumber = shippingAddressPhoneNumber;
    return this;
  }

   /**
   * Phone Number
   * @return shippingAddressPhoneNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Phone Number")

  public Object getShippingAddressPhoneNumber() {
    return shippingAddressPhoneNumber;
  }


  public void setShippingAddressPhoneNumber(Object shippingAddressPhoneNumber) {
    
    
    
    this.shippingAddressPhoneNumber = shippingAddressPhoneNumber;
  }


  public OrderAttributes shippingAddressStreetOne(Object shippingAddressStreetOne) {
    
    
    
    
    this.shippingAddressStreetOne = shippingAddressStreetOne;
    return this;
  }

   /**
   * Street One
   * @return shippingAddressStreetOne
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Street One")

  public Object getShippingAddressStreetOne() {
    return shippingAddressStreetOne;
  }


  public void setShippingAddressStreetOne(Object shippingAddressStreetOne) {
    
    
    
    this.shippingAddressStreetOne = shippingAddressStreetOne;
  }


  public OrderAttributes shippingAddressStreetTwo(Object shippingAddressStreetTwo) {
    
    
    
    
    this.shippingAddressStreetTwo = shippingAddressStreetTwo;
    return this;
  }

   /**
   * Street Two
   * @return shippingAddressStreetTwo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Street Two")

  public Object getShippingAddressStreetTwo() {
    return shippingAddressStreetTwo;
  }


  public void setShippingAddressStreetTwo(Object shippingAddressStreetTwo) {
    
    
    
    this.shippingAddressStreetTwo = shippingAddressStreetTwo;
  }


  public OrderAttributes shippingAddressCity(Object shippingAddressCity) {
    
    
    
    
    this.shippingAddressCity = shippingAddressCity;
    return this;
  }

   /**
   * City
   * @return shippingAddressCity
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "City")

  public Object getShippingAddressCity() {
    return shippingAddressCity;
  }


  public void setShippingAddressCity(Object shippingAddressCity) {
    
    
    
    this.shippingAddressCity = shippingAddressCity;
  }


  public OrderAttributes shippingAddressRegion(Object shippingAddressRegion) {
    
    
    
    
    this.shippingAddressRegion = shippingAddressRegion;
    return this;
  }

   /**
   * State
   * @return shippingAddressRegion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "State")

  public Object getShippingAddressRegion() {
    return shippingAddressRegion;
  }


  public void setShippingAddressRegion(Object shippingAddressRegion) {
    
    
    
    this.shippingAddressRegion = shippingAddressRegion;
  }


  public OrderAttributes shippingAddressCountry(Object shippingAddressCountry) {
    
    
    
    
    this.shippingAddressCountry = shippingAddressCountry;
    return this;
  }

   /**
   * Country
   * @return shippingAddressCountry
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Country")

  public Object getShippingAddressCountry() {
    return shippingAddressCountry;
  }


  public void setShippingAddressCountry(Object shippingAddressCountry) {
    
    
    
    this.shippingAddressCountry = shippingAddressCountry;
  }


  public OrderAttributes shippingAddressPostalCode(Object shippingAddressPostalCode) {
    
    
    
    
    this.shippingAddressPostalCode = shippingAddressPostalCode;
    return this;
  }

   /**
   * Postal Code
   * @return shippingAddressPostalCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Postal Code")

  public Object getShippingAddressPostalCode() {
    return shippingAddressPostalCode;
  }


  public void setShippingAddressPostalCode(Object shippingAddressPostalCode) {
    
    
    
    this.shippingAddressPostalCode = shippingAddressPostalCode;
  }


  public OrderAttributes billingAddressStreetOne(Object billingAddressStreetOne) {
    
    
    
    
    this.billingAddressStreetOne = billingAddressStreetOne;
    return this;
  }

   /**
   * Street One
   * @return billingAddressStreetOne
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Street One")

  public Object getBillingAddressStreetOne() {
    return billingAddressStreetOne;
  }


  public void setBillingAddressStreetOne(Object billingAddressStreetOne) {
    
    
    
    this.billingAddressStreetOne = billingAddressStreetOne;
  }


  public OrderAttributes billingAddressStreetTwo(Object billingAddressStreetTwo) {
    
    
    
    
    this.billingAddressStreetTwo = billingAddressStreetTwo;
    return this;
  }

   /**
   * Street Two
   * @return billingAddressStreetTwo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Street Two")

  public Object getBillingAddressStreetTwo() {
    return billingAddressStreetTwo;
  }


  public void setBillingAddressStreetTwo(Object billingAddressStreetTwo) {
    
    
    
    this.billingAddressStreetTwo = billingAddressStreetTwo;
  }


  public OrderAttributes billingAddressCity(Object billingAddressCity) {
    
    
    
    
    this.billingAddressCity = billingAddressCity;
    return this;
  }

   /**
   * City
   * @return billingAddressCity
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "City")

  public Object getBillingAddressCity() {
    return billingAddressCity;
  }


  public void setBillingAddressCity(Object billingAddressCity) {
    
    
    
    this.billingAddressCity = billingAddressCity;
  }


  public OrderAttributes billingAddressRegion(Object billingAddressRegion) {
    
    
    
    
    this.billingAddressRegion = billingAddressRegion;
    return this;
  }

   /**
   * State
   * @return billingAddressRegion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "State")

  public Object getBillingAddressRegion() {
    return billingAddressRegion;
  }


  public void setBillingAddressRegion(Object billingAddressRegion) {
    
    
    
    this.billingAddressRegion = billingAddressRegion;
  }


  public OrderAttributes billingAddressCountry(Object billingAddressCountry) {
    
    
    
    
    this.billingAddressCountry = billingAddressCountry;
    return this;
  }

   /**
   * Country
   * @return billingAddressCountry
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Country")

  public Object getBillingAddressCountry() {
    return billingAddressCountry;
  }


  public void setBillingAddressCountry(Object billingAddressCountry) {
    
    
    
    this.billingAddressCountry = billingAddressCountry;
  }


  public OrderAttributes billingAddressPostalCode(Object billingAddressPostalCode) {
    
    
    
    
    this.billingAddressPostalCode = billingAddressPostalCode;
    return this;
  }

   /**
   * Postal Code
   * @return billingAddressPostalCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Postal Code")

  public Object getBillingAddressPostalCode() {
    return billingAddressPostalCode;
  }


  public void setBillingAddressPostalCode(Object billingAddressPostalCode) {
    
    
    
    this.billingAddressPostalCode = billingAddressPostalCode;
  }


  public OrderAttributes pageId(Object pageId) {
    
    
    
    
    this.pageId = pageId;
    return this;
  }

   /**
   * Page ID
   * @return pageId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Page ID")

  public Object getPageId() {
    return pageId;
  }


  public void setPageId(Object pageId) {
    
    
    
    this.pageId = pageId;
  }


  public OrderAttributes notes(Object notes) {
    
    
    
    
    this.notes = notes;
    return this;
  }

   /**
   * Notes
   * @return notes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Notes")

  public Object getNotes() {
    return notes;
  }


  public void setNotes(Object notes) {
    
    
    
    this.notes = notes;
  }


  public OrderAttributes inTrial(Object inTrial) {
    
    
    
    
    this.inTrial = inTrial;
    return this;
  }

   /**
   * In Trial
   * @return inTrial
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "In Trial")

  public Object getInTrial() {
    return inTrial;
  }


  public void setInTrial(Object inTrial) {
    
    
    
    this.inTrial = inTrial;
  }


  public OrderAttributes billingStatus(Object billingStatus) {
    
    
    
    
    this.billingStatus = billingStatus;
    return this;
  }

   /**
   * Billing Status
   * @return billingStatus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Billing Status")

  public Object getBillingStatus() {
    return billingStatus;
  }


  public void setBillingStatus(Object billingStatus) {
    
    
    
    this.billingStatus = billingStatus;
  }


  public OrderAttributes serviceStatus(Object serviceStatus) {
    
    
    
    
    this.serviceStatus = serviceStatus;
    return this;
  }

   /**
   * Order Status
   * @return serviceStatus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Order Status")

  public Object getServiceStatus() {
    return serviceStatus;
  }


  public void setServiceStatus(Object serviceStatus) {
    
    
    
    this.serviceStatus = serviceStatus;
  }


  public OrderAttributes orderType(Object orderType) {
    
    
    
    
    this.orderType = orderType;
    return this;
  }

   /**
   * Order Type
   * @return orderType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Order Type")

  public Object getOrderType() {
    return orderType;
  }


  public void setOrderType(Object orderType) {
    
    
    
    this.orderType = orderType;
  }


  public OrderAttributes nextChargeAt(Object nextChargeAt) {
    
    
    
    
    this.nextChargeAt = nextChargeAt;
    return this;
  }

   /**
   * Next Charge At
   * @return nextChargeAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Next Charge At")

  public Object getNextChargeAt() {
    return nextChargeAt;
  }


  public void setNextChargeAt(Object nextChargeAt) {
    
    
    
    this.nextChargeAt = nextChargeAt;
  }


  public OrderAttributes taxAmount(Object taxAmount) {
    
    
    
    
    this.taxAmount = taxAmount;
    return this;
  }

   /**
   * Tax Amount
   * @return taxAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Tax Amount")

  public Object getTaxAmount() {
    return taxAmount;
  }


  public void setTaxAmount(Object taxAmount) {
    
    
    
    this.taxAmount = taxAmount;
  }


  public OrderAttributes trialEndAt(Object trialEndAt) {
    
    
    
    
    this.trialEndAt = trialEndAt;
    return this;
  }

   /**
   * Trial End At
   * @return trialEndAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Trial End At")

  public Object getTrialEndAt() {
    return trialEndAt;
  }


  public void setTrialEndAt(Object trialEndAt) {
    
    
    
    this.trialEndAt = trialEndAt;
  }


  public OrderAttributes billingPaymentMethodId(Object billingPaymentMethodId) {
    
    
    
    
    this.billingPaymentMethodId = billingPaymentMethodId;
    return this;
  }

   /**
   * Payment Method
   * @return billingPaymentMethodId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Payment Method")

  public Object getBillingPaymentMethodId() {
    return billingPaymentMethodId;
  }


  public void setBillingPaymentMethodId(Object billingPaymentMethodId) {
    
    
    
    this.billingPaymentMethodId = billingPaymentMethodId;
  }


  public OrderAttributes funnelName(Object funnelName) {
    
    
    
    
    this.funnelName = funnelName;
    return this;
  }

   /**
   * Funnel Name
   * @return funnelName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Funnel Name")

  public Object getFunnelName() {
    return funnelName;
  }


  public void setFunnelName(Object funnelName) {
    
    
    
    this.funnelName = funnelName;
  }


  public OrderAttributes tagIds(Object tagIds) {
    
    
    
    
    this.tagIds = tagIds;
    return this;
  }

   /**
   * Tags
   * @return tagIds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Tags")

  public Object getTagIds() {
    return tagIds;
  }


  public void setTagIds(Object tagIds) {
    
    
    
    this.tagIds = tagIds;
  }


  public OrderAttributes discountIds(Object discountIds) {
    
    
    
    
    this.discountIds = discountIds;
    return this;
  }

   /**
   * Discounts
   * @return discountIds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Discounts")

  public Object getDiscountIds() {
    return discountIds;
  }


  public void setDiscountIds(Object discountIds) {
    
    
    
    this.discountIds = discountIds;
  }


  public OrderAttributes activatedAt(Object activatedAt) {
    
    
    
    
    this.activatedAt = activatedAt;
    return this;
  }

   /**
   * Date Activated
   * @return activatedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Date Activated")

  public Object getActivatedAt() {
    return activatedAt;
  }


  public void setActivatedAt(Object activatedAt) {
    
    
    
    this.activatedAt = activatedAt;
  }


  public OrderAttributes createdAt(Object createdAt) {
    
    
    
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Date Ordered
   * @return createdAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Date Ordered")

  public Object getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(Object createdAt) {
    
    
    
    this.createdAt = createdAt;
  }


  public OrderAttributes updatedAt(Object updatedAt) {
    
    
    
    
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Date Updated
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Date Updated")

  public Object getUpdatedAt() {
    return updatedAt;
  }


  public void setUpdatedAt(Object updatedAt) {
    
    
    
    this.updatedAt = updatedAt;
  }


  public OrderAttributes phoneNumber(Object phoneNumber) {
    
    
    
    
    this.phoneNumber = phoneNumber;
    return this;
  }

   /**
   * Phone Number
   * @return phoneNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Phone Number")

  public Object getPhoneNumber() {
    return phoneNumber;
  }


  public void setPhoneNumber(Object phoneNumber) {
    
    
    
    this.phoneNumber = phoneNumber;
  }


  public OrderAttributes pageName(Object pageName) {
    
    
    
    
    this.pageName = pageName;
    return this;
  }

   /**
   * Page Name
   * @return pageName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Page Name")

  public Object getPageName() {
    return pageName;
  }


  public void setPageName(Object pageName) {
    
    
    
    this.pageName = pageName;
  }


  public OrderAttributes originationChannelName(Object originationChannelName) {
    
    
    
    
    this.originationChannelName = originationChannelName;
    return this;
  }

   /**
   * Origination Channel Name
   * @return originationChannelName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Origination Channel Name")

  public Object getOriginationChannelName() {
    return originationChannelName;
  }


  public void setOriginationChannelName(Object originationChannelName) {
    
    
    
    this.originationChannelName = originationChannelName;
  }


  public OrderAttributes orderPage(PagesProperty orderPage) {
    
    
    
    
    this.orderPage = orderPage;
    return this;
  }

   /**
   * Get orderPage
   * @return orderPage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public PagesProperty getOrderPage() {
    return orderPage;
  }


  public void setOrderPage(PagesProperty orderPage) {
    
    
    
    this.orderPage = orderPage;
  }


  public OrderAttributes contact(ContactAttributes contact) {
    
    
    
    
    this.contact = contact;
    return this;
  }

   /**
   * Customer Email
   * @return contact
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Customer Email")

  public ContactAttributes getContact() {
    return contact;
  }


  public void setContact(ContactAttributes contact) {
    
    
    
    this.contact = contact;
  }


  public OrderAttributes contactGroups(List<OrderContactGroupAttributes> contactGroups) {
    
    
    
    
    this.contactGroups = contactGroups;
    return this;
  }

  public OrderAttributes addContactGroupsItem(OrderContactGroupAttributes contactGroupsItem) {
    if (this.contactGroups == null) {
      this.contactGroups = new ArrayList<>();
    }
    this.contactGroups.add(contactGroupsItem);
    return this;
  }

   /**
   * Contact Groups
   * @return contactGroups
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Contact Groups")

  public List<OrderContactGroupAttributes> getContactGroups() {
    return contactGroups;
  }


  public void setContactGroups(List<OrderContactGroupAttributes> contactGroups) {
    
    
    
    this.contactGroups = contactGroups;
  }


  public OrderAttributes workspace(WorkspaceAttributes workspace) {
    
    
    
    
    this.workspace = workspace;
    return this;
  }

   /**
   * Workspace
   * @return workspace
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Workspace")

  public WorkspaceAttributes getWorkspace() {
    return workspace;
  }


  public void setWorkspace(WorkspaceAttributes workspace) {
    
    
    
    this.workspace = workspace;
  }


  public OrderAttributes segments(List<OrderSegmentAttributes> segments) {
    
    
    
    
    this.segments = segments;
    return this;
  }

  public OrderAttributes addSegmentsItem(OrderSegmentAttributes segmentsItem) {
    if (this.segments == null) {
      this.segments = new ArrayList<>();
    }
    this.segments.add(segmentsItem);
    return this;
  }

   /**
   * Segments
   * @return segments
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Segments")

  public List<OrderSegmentAttributes> getSegments() {
    return segments;
  }


  public void setSegments(List<OrderSegmentAttributes> segments) {
    
    
    
    this.segments = segments;
  }


  public OrderAttributes lineItems(List<OrdersLineItemAttributes> lineItems) {
    
    
    
    
    this.lineItems = lineItems;
    return this;
  }

  public OrderAttributes addLineItemsItem(OrdersLineItemAttributes lineItemsItem) {
    if (this.lineItems == null) {
      this.lineItems = new ArrayList<>();
    }
    this.lineItems.add(lineItemsItem);
    return this;
  }

   /**
   * Order Line Items
   * @return lineItems
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Order Line Items")

  public List<OrdersLineItemAttributes> getLineItems() {
    return lineItems;
  }


  public void setLineItems(List<OrdersLineItemAttributes> lineItems) {
    
    
    
    this.lineItems = lineItems;
  }


  public OrderAttributes previousLineItem(Object previousLineItem) {
    
    
    
    
    this.previousLineItem = previousLineItem;
    return this;
  }

   /**
   * An additional field, only available in outgoing subscription.upgrade/subscription.downgrade webhooks. It enables you to identify the former line item which is otherwise removed from the regular subscription line items when an upgrade or downgrade happens. Thus you can see the data of the previously active subscription. This property will not appear in regular API requests, but only in the event of a subscription upgrade or downgrade.
   * @return previousLineItem
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An additional field, only available in outgoing subscription.upgrade/subscription.downgrade webhooks. It enables you to identify the former line item which is otherwise removed from the regular subscription line items when an upgrade or downgrade happens. Thus you can see the data of the previously active subscription. This property will not appear in regular API requests, but only in the event of a subscription upgrade or downgrade.")

  public Object getPreviousLineItem() {
    return previousLineItem;
  }


  public void setPreviousLineItem(Object previousLineItem) {
    
    
    
    this.previousLineItem = previousLineItem;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   *
   * @param key name of the property
   * @param value value of the property
   * @return the OrderAttributes instance itself
   */
  public OrderAttributes putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   *
   * @return a map of objects
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   *
   * @param key name of the property
   * @return an object
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderAttributes orderAttributes = (OrderAttributes) o;
    return Objects.equals(this.id, orderAttributes.id) &&
        Objects.equals(this.publicId, orderAttributes.publicId) &&
        Objects.equals(this.orderNumber, orderAttributes.orderNumber) &&
        Objects.equals(this.workspaceId, orderAttributes.workspaceId) &&
        Objects.equals(this.contactId, orderAttributes.contactId) &&
        Objects.equals(this.totalAmount, orderAttributes.totalAmount) &&
        Objects.equals(this.currency, orderAttributes.currency) &&
        Objects.equals(this.originationChannelId, orderAttributes.originationChannelId) &&
        Objects.equals(this.originationChannelType, orderAttributes.originationChannelType) &&
        Objects.equals(this.shippingAddressFirstName, orderAttributes.shippingAddressFirstName) &&
        Objects.equals(this.shippingAddressLastName, orderAttributes.shippingAddressLastName) &&
        Objects.equals(this.shippingAddressOrganizationName, orderAttributes.shippingAddressOrganizationName) &&
        Objects.equals(this.shippingAddressPhoneNumber, orderAttributes.shippingAddressPhoneNumber) &&
        Objects.equals(this.shippingAddressStreetOne, orderAttributes.shippingAddressStreetOne) &&
        Objects.equals(this.shippingAddressStreetTwo, orderAttributes.shippingAddressStreetTwo) &&
        Objects.equals(this.shippingAddressCity, orderAttributes.shippingAddressCity) &&
        Objects.equals(this.shippingAddressRegion, orderAttributes.shippingAddressRegion) &&
        Objects.equals(this.shippingAddressCountry, orderAttributes.shippingAddressCountry) &&
        Objects.equals(this.shippingAddressPostalCode, orderAttributes.shippingAddressPostalCode) &&
        Objects.equals(this.billingAddressStreetOne, orderAttributes.billingAddressStreetOne) &&
        Objects.equals(this.billingAddressStreetTwo, orderAttributes.billingAddressStreetTwo) &&
        Objects.equals(this.billingAddressCity, orderAttributes.billingAddressCity) &&
        Objects.equals(this.billingAddressRegion, orderAttributes.billingAddressRegion) &&
        Objects.equals(this.billingAddressCountry, orderAttributes.billingAddressCountry) &&
        Objects.equals(this.billingAddressPostalCode, orderAttributes.billingAddressPostalCode) &&
        Objects.equals(this.pageId, orderAttributes.pageId) &&
        Objects.equals(this.notes, orderAttributes.notes) &&
        Objects.equals(this.inTrial, orderAttributes.inTrial) &&
        Objects.equals(this.billingStatus, orderAttributes.billingStatus) &&
        Objects.equals(this.serviceStatus, orderAttributes.serviceStatus) &&
        Objects.equals(this.orderType, orderAttributes.orderType) &&
        Objects.equals(this.nextChargeAt, orderAttributes.nextChargeAt) &&
        Objects.equals(this.taxAmount, orderAttributes.taxAmount) &&
        Objects.equals(this.trialEndAt, orderAttributes.trialEndAt) &&
        Objects.equals(this.billingPaymentMethodId, orderAttributes.billingPaymentMethodId) &&
        Objects.equals(this.funnelName, orderAttributes.funnelName) &&
        Objects.equals(this.tagIds, orderAttributes.tagIds) &&
        Objects.equals(this.discountIds, orderAttributes.discountIds) &&
        Objects.equals(this.activatedAt, orderAttributes.activatedAt) &&
        Objects.equals(this.createdAt, orderAttributes.createdAt) &&
        Objects.equals(this.updatedAt, orderAttributes.updatedAt) &&
        Objects.equals(this.phoneNumber, orderAttributes.phoneNumber) &&
        Objects.equals(this.pageName, orderAttributes.pageName) &&
        Objects.equals(this.originationChannelName, orderAttributes.originationChannelName) &&
        Objects.equals(this.orderPage, orderAttributes.orderPage) &&
        Objects.equals(this.contact, orderAttributes.contact) &&
        Objects.equals(this.contactGroups, orderAttributes.contactGroups) &&
        Objects.equals(this.workspace, orderAttributes.workspace) &&
        Objects.equals(this.segments, orderAttributes.segments) &&
        Objects.equals(this.lineItems, orderAttributes.lineItems) &&
        Objects.equals(this.previousLineItem, orderAttributes.previousLineItem)&&
        Objects.equals(this.additionalProperties, orderAttributes.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publicId, orderNumber, workspaceId, contactId, totalAmount, currency, originationChannelId, originationChannelType, shippingAddressFirstName, shippingAddressLastName, shippingAddressOrganizationName, shippingAddressPhoneNumber, shippingAddressStreetOne, shippingAddressStreetTwo, shippingAddressCity, shippingAddressRegion, shippingAddressCountry, shippingAddressPostalCode, billingAddressStreetOne, billingAddressStreetTwo, billingAddressCity, billingAddressRegion, billingAddressCountry, billingAddressPostalCode, pageId, notes, inTrial, billingStatus, serviceStatus, orderType, nextChargeAt, taxAmount, trialEndAt, billingPaymentMethodId, funnelName, tagIds, discountIds, activatedAt, createdAt, updatedAt, phoneNumber, pageName, originationChannelName, orderPage, contact, contactGroups, workspace, segments, lineItems, previousLineItem, additionalProperties);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderAttributes {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    publicId: ").append(toIndentedString(publicId)).append("\n");
    sb.append("    orderNumber: ").append(toIndentedString(orderNumber)).append("\n");
    sb.append("    workspaceId: ").append(toIndentedString(workspaceId)).append("\n");
    sb.append("    contactId: ").append(toIndentedString(contactId)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    originationChannelId: ").append(toIndentedString(originationChannelId)).append("\n");
    sb.append("    originationChannelType: ").append(toIndentedString(originationChannelType)).append("\n");
    sb.append("    shippingAddressFirstName: ").append(toIndentedString(shippingAddressFirstName)).append("\n");
    sb.append("    shippingAddressLastName: ").append(toIndentedString(shippingAddressLastName)).append("\n");
    sb.append("    shippingAddressOrganizationName: ").append(toIndentedString(shippingAddressOrganizationName)).append("\n");
    sb.append("    shippingAddressPhoneNumber: ").append(toIndentedString(shippingAddressPhoneNumber)).append("\n");
    sb.append("    shippingAddressStreetOne: ").append(toIndentedString(shippingAddressStreetOne)).append("\n");
    sb.append("    shippingAddressStreetTwo: ").append(toIndentedString(shippingAddressStreetTwo)).append("\n");
    sb.append("    shippingAddressCity: ").append(toIndentedString(shippingAddressCity)).append("\n");
    sb.append("    shippingAddressRegion: ").append(toIndentedString(shippingAddressRegion)).append("\n");
    sb.append("    shippingAddressCountry: ").append(toIndentedString(shippingAddressCountry)).append("\n");
    sb.append("    shippingAddressPostalCode: ").append(toIndentedString(shippingAddressPostalCode)).append("\n");
    sb.append("    billingAddressStreetOne: ").append(toIndentedString(billingAddressStreetOne)).append("\n");
    sb.append("    billingAddressStreetTwo: ").append(toIndentedString(billingAddressStreetTwo)).append("\n");
    sb.append("    billingAddressCity: ").append(toIndentedString(billingAddressCity)).append("\n");
    sb.append("    billingAddressRegion: ").append(toIndentedString(billingAddressRegion)).append("\n");
    sb.append("    billingAddressCountry: ").append(toIndentedString(billingAddressCountry)).append("\n");
    sb.append("    billingAddressPostalCode: ").append(toIndentedString(billingAddressPostalCode)).append("\n");
    sb.append("    pageId: ").append(toIndentedString(pageId)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    inTrial: ").append(toIndentedString(inTrial)).append("\n");
    sb.append("    billingStatus: ").append(toIndentedString(billingStatus)).append("\n");
    sb.append("    serviceStatus: ").append(toIndentedString(serviceStatus)).append("\n");
    sb.append("    orderType: ").append(toIndentedString(orderType)).append("\n");
    sb.append("    nextChargeAt: ").append(toIndentedString(nextChargeAt)).append("\n");
    sb.append("    taxAmount: ").append(toIndentedString(taxAmount)).append("\n");
    sb.append("    trialEndAt: ").append(toIndentedString(trialEndAt)).append("\n");
    sb.append("    billingPaymentMethodId: ").append(toIndentedString(billingPaymentMethodId)).append("\n");
    sb.append("    funnelName: ").append(toIndentedString(funnelName)).append("\n");
    sb.append("    tagIds: ").append(toIndentedString(tagIds)).append("\n");
    sb.append("    discountIds: ").append(toIndentedString(discountIds)).append("\n");
    sb.append("    activatedAt: ").append(toIndentedString(activatedAt)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    pageName: ").append(toIndentedString(pageName)).append("\n");
    sb.append("    originationChannelName: ").append(toIndentedString(originationChannelName)).append("\n");
    sb.append("    orderPage: ").append(toIndentedString(orderPage)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    contactGroups: ").append(toIndentedString(contactGroups)).append("\n");
    sb.append("    workspace: ").append(toIndentedString(workspace)).append("\n");
    sb.append("    segments: ").append(toIndentedString(segments)).append("\n");
    sb.append("    lineItems: ").append(toIndentedString(lineItems)).append("\n");
    sb.append("    previousLineItem: ").append(toIndentedString(previousLineItem)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("id");
    openapiFields.add("public_id");
    openapiFields.add("order_number");
    openapiFields.add("workspace_id");
    openapiFields.add("contact_id");
    openapiFields.add("total_amount");
    openapiFields.add("currency");
    openapiFields.add("origination_channel_id");
    openapiFields.add("origination_channel_type");
    openapiFields.add("shipping_address_first_name");
    openapiFields.add("shipping_address_last_name");
    openapiFields.add("shipping_address_organization_name");
    openapiFields.add("shipping_address_phone_number");
    openapiFields.add("shipping_address_street_one");
    openapiFields.add("shipping_address_street_two");
    openapiFields.add("shipping_address_city");
    openapiFields.add("shipping_address_region");
    openapiFields.add("shipping_address_country");
    openapiFields.add("shipping_address_postal_code");
    openapiFields.add("billing_address_street_one");
    openapiFields.add("billing_address_street_two");
    openapiFields.add("billing_address_city");
    openapiFields.add("billing_address_region");
    openapiFields.add("billing_address_country");
    openapiFields.add("billing_address_postal_code");
    openapiFields.add("page_id");
    openapiFields.add("notes");
    openapiFields.add("in_trial");
    openapiFields.add("billing_status");
    openapiFields.add("service_status");
    openapiFields.add("order_type");
    openapiFields.add("next_charge_at");
    openapiFields.add("tax_amount");
    openapiFields.add("trial_end_at");
    openapiFields.add("billing_payment_method_id");
    openapiFields.add("funnel_name");
    openapiFields.add("tag_ids");
    openapiFields.add("discount_ids");
    openapiFields.add("activated_at");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");
    openapiFields.add("phone_number");
    openapiFields.add("page_name");
    openapiFields.add("origination_channel_name");
    openapiFields.add("order_page");
    openapiFields.add("contact");
    openapiFields.add("contact_groups");
    openapiFields.add("workspace");
    openapiFields.add("segments");
    openapiFields.add("line_items");
    openapiFields.add("previous_line_item");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("workspace_id");
    openapiRequiredFields.add("contact_id");
    openapiRequiredFields.add("currency");
    openapiRequiredFields.add("origination_channel_id");
    openapiRequiredFields.add("contact");
    openapiRequiredFields.add("workspace");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to OrderAttributes
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!OrderAttributes.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in OrderAttributes is not found in the empty JSON string", OrderAttributes.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : OrderAttributes.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (!jsonObj.get("currency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `currency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("currency").toString()));
      }
      // validate the optional field `order_page`
      if (jsonObj.get("order_page") != null && !jsonObj.get("order_page").isJsonNull()) {
        PagesProperty.validateJsonObject(jsonObj.getAsJsonObject("order_page"));
      }
      // validate the required field `contact`
      ContactAttributes.validateJsonObject(jsonObj.getAsJsonObject("contact"));
      if (jsonObj.get("contact_groups") != null && !jsonObj.get("contact_groups").isJsonNull()) {
        JsonArray jsonArraycontactGroups = jsonObj.getAsJsonArray("contact_groups");
        if (jsonArraycontactGroups != null) {
          // ensure the json data is an array
          if (!jsonObj.get("contact_groups").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `contact_groups` to be an array in the JSON string but got `%s`", jsonObj.get("contact_groups").toString()));
          }

          // validate the optional field `contact_groups` (array)
          for (int i = 0; i < jsonArraycontactGroups.size(); i++) {
            OrderContactGroupAttributes.validateJsonObject(jsonArraycontactGroups.get(i).getAsJsonObject());
          };
        }
      }
      // validate the required field `workspace`
      WorkspaceAttributes.validateJsonObject(jsonObj.getAsJsonObject("workspace"));
      if (jsonObj.get("segments") != null && !jsonObj.get("segments").isJsonNull()) {
        JsonArray jsonArraysegments = jsonObj.getAsJsonArray("segments");
        if (jsonArraysegments != null) {
          // ensure the json data is an array
          if (!jsonObj.get("segments").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `segments` to be an array in the JSON string but got `%s`", jsonObj.get("segments").toString()));
          }

          // validate the optional field `segments` (array)
          for (int i = 0; i < jsonArraysegments.size(); i++) {
            OrderSegmentAttributes.validateJsonObject(jsonArraysegments.get(i).getAsJsonObject());
          };
        }
      }
      if (jsonObj.get("line_items") != null && !jsonObj.get("line_items").isJsonNull()) {
        JsonArray jsonArraylineItems = jsonObj.getAsJsonArray("line_items");
        if (jsonArraylineItems != null) {
          // ensure the json data is an array
          if (!jsonObj.get("line_items").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `line_items` to be an array in the JSON string but got `%s`", jsonObj.get("line_items").toString()));
          }

          // validate the optional field `line_items` (array)
          for (int i = 0; i < jsonArraylineItems.size(); i++) {
            OrdersLineItemAttributes.validateJsonObject(jsonArraylineItems.get(i).getAsJsonObject());
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!OrderAttributes.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'OrderAttributes' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<OrderAttributes> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(OrderAttributes.class));

       return (TypeAdapter<T>) new TypeAdapter<OrderAttributes>() {
           @Override
           public void write(JsonWriter out, OrderAttributes value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additonal properties
             if (value.getAdditionalProperties() != null) {
               for (Map.Entry<String, Object> entry : value.getAdditionalProperties().entrySet()) {
                 if (entry.getValue() instanceof String)
                   obj.addProperty(entry.getKey(), (String) entry.getValue());
                 else if (entry.getValue() instanceof Number)
                   obj.addProperty(entry.getKey(), (Number) entry.getValue());
                 else if (entry.getValue() instanceof Boolean)
                   obj.addProperty(entry.getKey(), (Boolean) entry.getValue());
                 else if (entry.getValue() instanceof Character)
                   obj.addProperty(entry.getKey(), (Character) entry.getValue());
                 else {
                   obj.add(entry.getKey(), gson.toJsonTree(entry.getValue()).getAsJsonObject());
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public OrderAttributes read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             OrderAttributes instance = thisAdapter.fromJsonTree(jsonObj);
             for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
               if (!openapiFields.contains(entry.getKey())) {
                 if (entry.getValue().isJsonPrimitive()) { // primitive type
                   if (entry.getValue().getAsJsonPrimitive().isString())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsString());
                   else if (entry.getValue().getAsJsonPrimitive().isNumber())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsNumber());
                   else if (entry.getValue().getAsJsonPrimitive().isBoolean())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsBoolean());
                   else
                     throw new IllegalArgumentException(String.format("The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else if (entry.getValue().isJsonArray()) {
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), List.class));
                 } else { // JSON object
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), HashMap.class));
                 }
               }
             }
             return instance;
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of OrderAttributes given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of OrderAttributes
  * @throws IOException if the JSON string is invalid with respect to OrderAttributes
  */
  public static OrderAttributes fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, OrderAttributes.class);
  }

 /**
  * Convert an instance of OrderAttributes to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

