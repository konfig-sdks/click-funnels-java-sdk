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
import com.konfigthis.client.model.LineItemsProperty;
import com.konfigthis.client.model.LineItemsProperty1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
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
 * Line items
 */
@ApiModel(description = "Line items")@javax.annotation.Generated(value = "Generated by https://konfigthis.com")
public class OrdersInvoicesLineItemAttributes {
  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private Object description = null;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_PUBLIC_ID = "public_id";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ID)
  private Object publicId = null;

  public static final String SERIALIZED_NAME_INVOICE_ID = "invoice_id";
  @SerializedName(SERIALIZED_NAME_INVOICE_ID)
  private Integer invoiceId;

  public static final String SERIALIZED_NAME_EXTERNAL_ID = "external_id";
  @SerializedName(SERIALIZED_NAME_EXTERNAL_ID)
  private String externalId;

  public static final String SERIALIZED_NAME_PAYMENT_TYPE = "payment_type";
  @SerializedName(SERIALIZED_NAME_PAYMENT_TYPE)
  private Object paymentType = null;

  public static final String SERIALIZED_NAME_AMOUNT = "amount";
  @SerializedName(SERIALIZED_NAME_AMOUNT)
  private Object amount = null;

  public static final String SERIALIZED_NAME_QUANTITY = "quantity";
  @SerializedName(SERIALIZED_NAME_QUANTITY)
  private Object quantity = null;

  /**
   * Represents the current fulfillment status of the line item
   */
  @JsonAdapter(FulfillmentStatusEnum.Adapter.class)
 public enum FulfillmentStatusEnum {
    UNFULFILLED("unfulfilled"),
    
    PARTIALLY_FULFILLED("partially_fulfilled"),
    
    COMPLETE("complete"),
    
    IGNORED("ignored");

    private String value;

    FulfillmentStatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static FulfillmentStatusEnum fromValue(String value) {
      for (FulfillmentStatusEnum b : FulfillmentStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<FulfillmentStatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final FulfillmentStatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public FulfillmentStatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return FulfillmentStatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_FULFILLMENT_STATUS = "fulfillment_status";
  @SerializedName(SERIALIZED_NAME_FULFILLMENT_STATUS)
  private FulfillmentStatusEnum fulfillmentStatus;

  public static final String SERIALIZED_NAME_EXTERNAL_PRODUCT_ID = "external_product_id";
  @SerializedName(SERIALIZED_NAME_EXTERNAL_PRODUCT_ID)
  private Object externalProductId = null;

  public static final String SERIALIZED_NAME_DISCOUNT_AMOUNT = "discount_amount";
  @SerializedName(SERIALIZED_NAME_DISCOUNT_AMOUNT)
  private Object discountAmount = null;

  public static final String SERIALIZED_NAME_STATE_TAX_AMOUNT = "state_tax_amount";
  @SerializedName(SERIALIZED_NAME_STATE_TAX_AMOUNT)
  private Object stateTaxAmount = null;

  public static final String SERIALIZED_NAME_COUNTY_TAX_AMOUNT = "county_tax_amount";
  @SerializedName(SERIALIZED_NAME_COUNTY_TAX_AMOUNT)
  private Object countyTaxAmount = null;

  public static final String SERIALIZED_NAME_CITY_TAX_AMOUNT = "city_tax_amount";
  @SerializedName(SERIALIZED_NAME_CITY_TAX_AMOUNT)
  private Object cityTaxAmount = null;

  public static final String SERIALIZED_NAME_DISTRICT_TAX_AMOUNT = "district_tax_amount";
  @SerializedName(SERIALIZED_NAME_DISTRICT_TAX_AMOUNT)
  private Object districtTaxAmount = null;

  public static final String SERIALIZED_NAME_STATE_TAX_RATE = "state_tax_rate";
  @SerializedName(SERIALIZED_NAME_STATE_TAX_RATE)
  private Object stateTaxRate = null;

  public static final String SERIALIZED_NAME_COUNTY_TAX_RATE = "county_tax_rate";
  @SerializedName(SERIALIZED_NAME_COUNTY_TAX_RATE)
  private Object countyTaxRate = null;

  public static final String SERIALIZED_NAME_CITY_TAX_RATE = "city_tax_rate";
  @SerializedName(SERIALIZED_NAME_CITY_TAX_RATE)
  private Object cityTaxRate = null;

  public static final String SERIALIZED_NAME_DISTRICT_TAX_RATE = "district_tax_rate";
  @SerializedName(SERIALIZED_NAME_DISTRICT_TAX_RATE)
  private Object districtTaxRate = null;

  public static final String SERIALIZED_NAME_COUNTRY_TAX_JURISDICTION = "country_tax_jurisdiction";
  @SerializedName(SERIALIZED_NAME_COUNTRY_TAX_JURISDICTION)
  private Object countryTaxJurisdiction = null;

  public static final String SERIALIZED_NAME_STATE_TAX_JURISDICTION = "state_tax_jurisdiction";
  @SerializedName(SERIALIZED_NAME_STATE_TAX_JURISDICTION)
  private Object stateTaxJurisdiction = null;

  public static final String SERIALIZED_NAME_COUNTY_TAX_JURISDICTION = "county_tax_jurisdiction";
  @SerializedName(SERIALIZED_NAME_COUNTY_TAX_JURISDICTION)
  private Object countyTaxJurisdiction = null;

  public static final String SERIALIZED_NAME_CITY_TAX_JURISDICTION = "city_tax_jurisdiction";
  @SerializedName(SERIALIZED_NAME_CITY_TAX_JURISDICTION)
  private Object cityTaxJurisdiction = null;

  public static final String SERIALIZED_NAME_PERIOD_START_AT = "period_start_at";
  @SerializedName(SERIALIZED_NAME_PERIOD_START_AT)
  private Object periodStartAt = null;

  public static final String SERIALIZED_NAME_PERIOD_END_AT = "period_end_at";
  @SerializedName(SERIALIZED_NAME_PERIOD_END_AT)
  private Object periodEndAt = null;

  public static final String SERIALIZED_NAME_PERIOD_NUMBER = "period_number";
  @SerializedName(SERIALIZED_NAME_PERIOD_NUMBER)
  private Object periodNumber = null;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private Object createdAt = null;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private Object updatedAt = null;

  public static final String SERIALIZED_NAME_PRODUCTS_PRICE = "products_price";
  @SerializedName(SERIALIZED_NAME_PRODUCTS_PRICE)
  private LineItemsProperty productsPrice;

  public static final String SERIALIZED_NAME_PRODUCTS_VARIANT = "products_variant";
  @SerializedName(SERIALIZED_NAME_PRODUCTS_VARIANT)
  private LineItemsProperty1 productsVariant;

  public OrdersInvoicesLineItemAttributes() {
  }

  public OrdersInvoicesLineItemAttributes description(Object description) {
    
    
    
    
    this.description = description;
    return this;
  }

   /**
   * Description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Description")

  public Object getDescription() {
    return description;
  }


  public void setDescription(Object description) {
    
    
    
    this.description = description;
  }


  public OrdersInvoicesLineItemAttributes id(Integer id) {
    
    
    
    
    this.id = id;
    return this;
  }

   /**
   * Line item ID
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "5", required = true, value = "Line item ID")

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    
    
    
    this.id = id;
  }


  public OrdersInvoicesLineItemAttributes publicId(Object publicId) {
    
    
    
    
    this.publicId = publicId;
    return this;
  }

   /**
   * Public ID
   * @return publicId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Public ID")

  public Object getPublicId() {
    return publicId;
  }


  public void setPublicId(Object publicId) {
    
    
    
    this.publicId = publicId;
  }


  public OrdersInvoicesLineItemAttributes invoiceId(Integer invoiceId) {
    
    
    
    
    this.invoiceId = invoiceId;
    return this;
  }

   /**
   * Invoice ID
   * @return invoiceId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "3", required = true, value = "Invoice ID")

  public Integer getInvoiceId() {
    return invoiceId;
  }


  public void setInvoiceId(Integer invoiceId) {
    
    
    
    this.invoiceId = invoiceId;
  }


  public OrdersInvoicesLineItemAttributes externalId(String externalId) {
    
    
    
    
    this.externalId = externalId;
    return this;
  }

   /**
   * External
   * @return externalId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "Example External ID", required = true, value = "External")

  public String getExternalId() {
    return externalId;
  }


  public void setExternalId(String externalId) {
    
    
    
    this.externalId = externalId;
  }


  public OrdersInvoicesLineItemAttributes paymentType(Object paymentType) {
    
    
    
    
    this.paymentType = paymentType;
    return this;
  }

   /**
   * Payment Type
   * @return paymentType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Payment Type")

  public Object getPaymentType() {
    return paymentType;
  }


  public void setPaymentType(Object paymentType) {
    
    
    
    this.paymentType = paymentType;
  }


  public OrdersInvoicesLineItemAttributes amount(Object amount) {
    
    
    
    
    this.amount = amount;
    return this;
  }

   /**
   * Amount
   * @return amount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Amount")

  public Object getAmount() {
    return amount;
  }


  public void setAmount(Object amount) {
    
    
    
    this.amount = amount;
  }


  public OrdersInvoicesLineItemAttributes quantity(Object quantity) {
    
    
    
    
    this.quantity = quantity;
    return this;
  }

   /**
   * Quantity
   * @return quantity
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Quantity")

  public Object getQuantity() {
    return quantity;
  }


  public void setQuantity(Object quantity) {
    
    
    
    this.quantity = quantity;
  }


  public OrdersInvoicesLineItemAttributes fulfillmentStatus(FulfillmentStatusEnum fulfillmentStatus) {
    
    
    
    
    this.fulfillmentStatus = fulfillmentStatus;
    return this;
  }

   /**
   * Represents the current fulfillment status of the line item
   * @return fulfillmentStatus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "UNFULFILLED", value = "Represents the current fulfillment status of the line item")

  public FulfillmentStatusEnum getFulfillmentStatus() {
    return fulfillmentStatus;
  }


  public void setFulfillmentStatus(FulfillmentStatusEnum fulfillmentStatus) {
    
    
    
    this.fulfillmentStatus = fulfillmentStatus;
  }


  public OrdersInvoicesLineItemAttributes externalProductId(Object externalProductId) {
    
    
    
    
    this.externalProductId = externalProductId;
    return this;
  }

   /**
   * External Product
   * @return externalProductId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "External Product")

  public Object getExternalProductId() {
    return externalProductId;
  }


  public void setExternalProductId(Object externalProductId) {
    
    
    
    this.externalProductId = externalProductId;
  }


  public OrdersInvoicesLineItemAttributes discountAmount(Object discountAmount) {
    
    
    
    
    this.discountAmount = discountAmount;
    return this;
  }

   /**
   * Discount Amount
   * @return discountAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Discount Amount")

  public Object getDiscountAmount() {
    return discountAmount;
  }


  public void setDiscountAmount(Object discountAmount) {
    
    
    
    this.discountAmount = discountAmount;
  }


  public OrdersInvoicesLineItemAttributes stateTaxAmount(Object stateTaxAmount) {
    
    
    
    
    this.stateTaxAmount = stateTaxAmount;
    return this;
  }

   /**
   * State Tax Amount
   * @return stateTaxAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "State Tax Amount")

  public Object getStateTaxAmount() {
    return stateTaxAmount;
  }


  public void setStateTaxAmount(Object stateTaxAmount) {
    
    
    
    this.stateTaxAmount = stateTaxAmount;
  }


  public OrdersInvoicesLineItemAttributes countyTaxAmount(Object countyTaxAmount) {
    
    
    
    
    this.countyTaxAmount = countyTaxAmount;
    return this;
  }

   /**
   * County Tax Amount
   * @return countyTaxAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "County Tax Amount")

  public Object getCountyTaxAmount() {
    return countyTaxAmount;
  }


  public void setCountyTaxAmount(Object countyTaxAmount) {
    
    
    
    this.countyTaxAmount = countyTaxAmount;
  }


  public OrdersInvoicesLineItemAttributes cityTaxAmount(Object cityTaxAmount) {
    
    
    
    
    this.cityTaxAmount = cityTaxAmount;
    return this;
  }

   /**
   * City Tax Amount
   * @return cityTaxAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "City Tax Amount")

  public Object getCityTaxAmount() {
    return cityTaxAmount;
  }


  public void setCityTaxAmount(Object cityTaxAmount) {
    
    
    
    this.cityTaxAmount = cityTaxAmount;
  }


  public OrdersInvoicesLineItemAttributes districtTaxAmount(Object districtTaxAmount) {
    
    
    
    
    this.districtTaxAmount = districtTaxAmount;
    return this;
  }

   /**
   * District Tax Amount
   * @return districtTaxAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "District Tax Amount")

  public Object getDistrictTaxAmount() {
    return districtTaxAmount;
  }


  public void setDistrictTaxAmount(Object districtTaxAmount) {
    
    
    
    this.districtTaxAmount = districtTaxAmount;
  }


  public OrdersInvoicesLineItemAttributes stateTaxRate(Object stateTaxRate) {
    
    
    
    
    this.stateTaxRate = stateTaxRate;
    return this;
  }

   /**
   * State Tax Rate
   * @return stateTaxRate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "State Tax Rate")

  public Object getStateTaxRate() {
    return stateTaxRate;
  }


  public void setStateTaxRate(Object stateTaxRate) {
    
    
    
    this.stateTaxRate = stateTaxRate;
  }


  public OrdersInvoicesLineItemAttributes countyTaxRate(Object countyTaxRate) {
    
    
    
    
    this.countyTaxRate = countyTaxRate;
    return this;
  }

   /**
   * County Tax Rate
   * @return countyTaxRate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "County Tax Rate")

  public Object getCountyTaxRate() {
    return countyTaxRate;
  }


  public void setCountyTaxRate(Object countyTaxRate) {
    
    
    
    this.countyTaxRate = countyTaxRate;
  }


  public OrdersInvoicesLineItemAttributes cityTaxRate(Object cityTaxRate) {
    
    
    
    
    this.cityTaxRate = cityTaxRate;
    return this;
  }

   /**
   * City Tax Rate
   * @return cityTaxRate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "City Tax Rate")

  public Object getCityTaxRate() {
    return cityTaxRate;
  }


  public void setCityTaxRate(Object cityTaxRate) {
    
    
    
    this.cityTaxRate = cityTaxRate;
  }


  public OrdersInvoicesLineItemAttributes districtTaxRate(Object districtTaxRate) {
    
    
    
    
    this.districtTaxRate = districtTaxRate;
    return this;
  }

   /**
   * District Tax Rate
   * @return districtTaxRate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "District Tax Rate")

  public Object getDistrictTaxRate() {
    return districtTaxRate;
  }


  public void setDistrictTaxRate(Object districtTaxRate) {
    
    
    
    this.districtTaxRate = districtTaxRate;
  }


  public OrdersInvoicesLineItemAttributes countryTaxJurisdiction(Object countryTaxJurisdiction) {
    
    
    
    
    this.countryTaxJurisdiction = countryTaxJurisdiction;
    return this;
  }

   /**
   * Country Tax Jurisdiction
   * @return countryTaxJurisdiction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Country Tax Jurisdiction")

  public Object getCountryTaxJurisdiction() {
    return countryTaxJurisdiction;
  }


  public void setCountryTaxJurisdiction(Object countryTaxJurisdiction) {
    
    
    
    this.countryTaxJurisdiction = countryTaxJurisdiction;
  }


  public OrdersInvoicesLineItemAttributes stateTaxJurisdiction(Object stateTaxJurisdiction) {
    
    
    
    
    this.stateTaxJurisdiction = stateTaxJurisdiction;
    return this;
  }

   /**
   * State Tax Jurisdiction
   * @return stateTaxJurisdiction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "State Tax Jurisdiction")

  public Object getStateTaxJurisdiction() {
    return stateTaxJurisdiction;
  }


  public void setStateTaxJurisdiction(Object stateTaxJurisdiction) {
    
    
    
    this.stateTaxJurisdiction = stateTaxJurisdiction;
  }


  public OrdersInvoicesLineItemAttributes countyTaxJurisdiction(Object countyTaxJurisdiction) {
    
    
    
    
    this.countyTaxJurisdiction = countyTaxJurisdiction;
    return this;
  }

   /**
   * County Tax Jurisdiction
   * @return countyTaxJurisdiction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "County Tax Jurisdiction")

  public Object getCountyTaxJurisdiction() {
    return countyTaxJurisdiction;
  }


  public void setCountyTaxJurisdiction(Object countyTaxJurisdiction) {
    
    
    
    this.countyTaxJurisdiction = countyTaxJurisdiction;
  }


  public OrdersInvoicesLineItemAttributes cityTaxJurisdiction(Object cityTaxJurisdiction) {
    
    
    
    
    this.cityTaxJurisdiction = cityTaxJurisdiction;
    return this;
  }

   /**
   * City Tax Jurisdiction
   * @return cityTaxJurisdiction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "City Tax Jurisdiction")

  public Object getCityTaxJurisdiction() {
    return cityTaxJurisdiction;
  }


  public void setCityTaxJurisdiction(Object cityTaxJurisdiction) {
    
    
    
    this.cityTaxJurisdiction = cityTaxJurisdiction;
  }


  public OrdersInvoicesLineItemAttributes periodStartAt(Object periodStartAt) {
    
    
    
    
    this.periodStartAt = periodStartAt;
    return this;
  }

   /**
   * Period Start At
   * @return periodStartAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Period Start At")

  public Object getPeriodStartAt() {
    return periodStartAt;
  }


  public void setPeriodStartAt(Object periodStartAt) {
    
    
    
    this.periodStartAt = periodStartAt;
  }


  public OrdersInvoicesLineItemAttributes periodEndAt(Object periodEndAt) {
    
    
    
    
    this.periodEndAt = periodEndAt;
    return this;
  }

   /**
   * Period End At
   * @return periodEndAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Period End At")

  public Object getPeriodEndAt() {
    return periodEndAt;
  }


  public void setPeriodEndAt(Object periodEndAt) {
    
    
    
    this.periodEndAt = periodEndAt;
  }


  public OrdersInvoicesLineItemAttributes periodNumber(Object periodNumber) {
    
    
    
    
    this.periodNumber = periodNumber;
    return this;
  }

   /**
   * Period Number
   * @return periodNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Period Number")

  public Object getPeriodNumber() {
    return periodNumber;
  }


  public void setPeriodNumber(Object periodNumber) {
    
    
    
    this.periodNumber = periodNumber;
  }


  public OrdersInvoicesLineItemAttributes createdAt(Object createdAt) {
    
    
    
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Added
   * @return createdAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Added")

  public Object getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(Object createdAt) {
    
    
    
    this.createdAt = createdAt;
  }


  public OrdersInvoicesLineItemAttributes updatedAt(Object updatedAt) {
    
    
    
    
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Updated
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Updated")

  public Object getUpdatedAt() {
    return updatedAt;
  }


  public void setUpdatedAt(Object updatedAt) {
    
    
    
    this.updatedAt = updatedAt;
  }


  public OrdersInvoicesLineItemAttributes productsPrice(LineItemsProperty productsPrice) {
    
    
    
    
    this.productsPrice = productsPrice;
    return this;
  }

   /**
   * Get productsPrice
   * @return productsPrice
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LineItemsProperty getProductsPrice() {
    return productsPrice;
  }


  public void setProductsPrice(LineItemsProperty productsPrice) {
    
    
    
    this.productsPrice = productsPrice;
  }


  public OrdersInvoicesLineItemAttributes productsVariant(LineItemsProperty1 productsVariant) {
    
    
    
    
    this.productsVariant = productsVariant;
    return this;
  }

   /**
   * Get productsVariant
   * @return productsVariant
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LineItemsProperty1 getProductsVariant() {
    return productsVariant;
  }


  public void setProductsVariant(LineItemsProperty1 productsVariant) {
    
    
    
    this.productsVariant = productsVariant;
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
   * @return the OrdersInvoicesLineItemAttributes instance itself
   */
  public OrdersInvoicesLineItemAttributes putAdditionalProperty(String key, Object value) {
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
    OrdersInvoicesLineItemAttributes ordersInvoicesLineItemAttributes = (OrdersInvoicesLineItemAttributes) o;
    return Objects.equals(this.description, ordersInvoicesLineItemAttributes.description) &&
        Objects.equals(this.id, ordersInvoicesLineItemAttributes.id) &&
        Objects.equals(this.publicId, ordersInvoicesLineItemAttributes.publicId) &&
        Objects.equals(this.invoiceId, ordersInvoicesLineItemAttributes.invoiceId) &&
        Objects.equals(this.externalId, ordersInvoicesLineItemAttributes.externalId) &&
        Objects.equals(this.paymentType, ordersInvoicesLineItemAttributes.paymentType) &&
        Objects.equals(this.amount, ordersInvoicesLineItemAttributes.amount) &&
        Objects.equals(this.quantity, ordersInvoicesLineItemAttributes.quantity) &&
        Objects.equals(this.fulfillmentStatus, ordersInvoicesLineItemAttributes.fulfillmentStatus) &&
        Objects.equals(this.externalProductId, ordersInvoicesLineItemAttributes.externalProductId) &&
        Objects.equals(this.discountAmount, ordersInvoicesLineItemAttributes.discountAmount) &&
        Objects.equals(this.stateTaxAmount, ordersInvoicesLineItemAttributes.stateTaxAmount) &&
        Objects.equals(this.countyTaxAmount, ordersInvoicesLineItemAttributes.countyTaxAmount) &&
        Objects.equals(this.cityTaxAmount, ordersInvoicesLineItemAttributes.cityTaxAmount) &&
        Objects.equals(this.districtTaxAmount, ordersInvoicesLineItemAttributes.districtTaxAmount) &&
        Objects.equals(this.stateTaxRate, ordersInvoicesLineItemAttributes.stateTaxRate) &&
        Objects.equals(this.countyTaxRate, ordersInvoicesLineItemAttributes.countyTaxRate) &&
        Objects.equals(this.cityTaxRate, ordersInvoicesLineItemAttributes.cityTaxRate) &&
        Objects.equals(this.districtTaxRate, ordersInvoicesLineItemAttributes.districtTaxRate) &&
        Objects.equals(this.countryTaxJurisdiction, ordersInvoicesLineItemAttributes.countryTaxJurisdiction) &&
        Objects.equals(this.stateTaxJurisdiction, ordersInvoicesLineItemAttributes.stateTaxJurisdiction) &&
        Objects.equals(this.countyTaxJurisdiction, ordersInvoicesLineItemAttributes.countyTaxJurisdiction) &&
        Objects.equals(this.cityTaxJurisdiction, ordersInvoicesLineItemAttributes.cityTaxJurisdiction) &&
        Objects.equals(this.periodStartAt, ordersInvoicesLineItemAttributes.periodStartAt) &&
        Objects.equals(this.periodEndAt, ordersInvoicesLineItemAttributes.periodEndAt) &&
        Objects.equals(this.periodNumber, ordersInvoicesLineItemAttributes.periodNumber) &&
        Objects.equals(this.createdAt, ordersInvoicesLineItemAttributes.createdAt) &&
        Objects.equals(this.updatedAt, ordersInvoicesLineItemAttributes.updatedAt) &&
        Objects.equals(this.productsPrice, ordersInvoicesLineItemAttributes.productsPrice) &&
        Objects.equals(this.productsVariant, ordersInvoicesLineItemAttributes.productsVariant)&&
        Objects.equals(this.additionalProperties, ordersInvoicesLineItemAttributes.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, id, publicId, invoiceId, externalId, paymentType, amount, quantity, fulfillmentStatus, externalProductId, discountAmount, stateTaxAmount, countyTaxAmount, cityTaxAmount, districtTaxAmount, stateTaxRate, countyTaxRate, cityTaxRate, districtTaxRate, countryTaxJurisdiction, stateTaxJurisdiction, countyTaxJurisdiction, cityTaxJurisdiction, periodStartAt, periodEndAt, periodNumber, createdAt, updatedAt, productsPrice, productsVariant, additionalProperties);
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
    sb.append("class OrdersInvoicesLineItemAttributes {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    publicId: ").append(toIndentedString(publicId)).append("\n");
    sb.append("    invoiceId: ").append(toIndentedString(invoiceId)).append("\n");
    sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
    sb.append("    paymentType: ").append(toIndentedString(paymentType)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    fulfillmentStatus: ").append(toIndentedString(fulfillmentStatus)).append("\n");
    sb.append("    externalProductId: ").append(toIndentedString(externalProductId)).append("\n");
    sb.append("    discountAmount: ").append(toIndentedString(discountAmount)).append("\n");
    sb.append("    stateTaxAmount: ").append(toIndentedString(stateTaxAmount)).append("\n");
    sb.append("    countyTaxAmount: ").append(toIndentedString(countyTaxAmount)).append("\n");
    sb.append("    cityTaxAmount: ").append(toIndentedString(cityTaxAmount)).append("\n");
    sb.append("    districtTaxAmount: ").append(toIndentedString(districtTaxAmount)).append("\n");
    sb.append("    stateTaxRate: ").append(toIndentedString(stateTaxRate)).append("\n");
    sb.append("    countyTaxRate: ").append(toIndentedString(countyTaxRate)).append("\n");
    sb.append("    cityTaxRate: ").append(toIndentedString(cityTaxRate)).append("\n");
    sb.append("    districtTaxRate: ").append(toIndentedString(districtTaxRate)).append("\n");
    sb.append("    countryTaxJurisdiction: ").append(toIndentedString(countryTaxJurisdiction)).append("\n");
    sb.append("    stateTaxJurisdiction: ").append(toIndentedString(stateTaxJurisdiction)).append("\n");
    sb.append("    countyTaxJurisdiction: ").append(toIndentedString(countyTaxJurisdiction)).append("\n");
    sb.append("    cityTaxJurisdiction: ").append(toIndentedString(cityTaxJurisdiction)).append("\n");
    sb.append("    periodStartAt: ").append(toIndentedString(periodStartAt)).append("\n");
    sb.append("    periodEndAt: ").append(toIndentedString(periodEndAt)).append("\n");
    sb.append("    periodNumber: ").append(toIndentedString(periodNumber)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    productsPrice: ").append(toIndentedString(productsPrice)).append("\n");
    sb.append("    productsVariant: ").append(toIndentedString(productsVariant)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("id");
    openapiFields.add("public_id");
    openapiFields.add("invoice_id");
    openapiFields.add("external_id");
    openapiFields.add("payment_type");
    openapiFields.add("amount");
    openapiFields.add("quantity");
    openapiFields.add("fulfillment_status");
    openapiFields.add("external_product_id");
    openapiFields.add("discount_amount");
    openapiFields.add("state_tax_amount");
    openapiFields.add("county_tax_amount");
    openapiFields.add("city_tax_amount");
    openapiFields.add("district_tax_amount");
    openapiFields.add("state_tax_rate");
    openapiFields.add("county_tax_rate");
    openapiFields.add("city_tax_rate");
    openapiFields.add("district_tax_rate");
    openapiFields.add("country_tax_jurisdiction");
    openapiFields.add("state_tax_jurisdiction");
    openapiFields.add("county_tax_jurisdiction");
    openapiFields.add("city_tax_jurisdiction");
    openapiFields.add("period_start_at");
    openapiFields.add("period_end_at");
    openapiFields.add("period_number");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");
    openapiFields.add("products_price");
    openapiFields.add("products_variant");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("invoice_id");
    openapiRequiredFields.add("external_id");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to OrdersInvoicesLineItemAttributes
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!OrdersInvoicesLineItemAttributes.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in OrdersInvoicesLineItemAttributes is not found in the empty JSON string", OrdersInvoicesLineItemAttributes.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : OrdersInvoicesLineItemAttributes.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (!jsonObj.get("external_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `external_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("external_id").toString()));
      }
      if ((jsonObj.get("fulfillment_status") != null && !jsonObj.get("fulfillment_status").isJsonNull()) && !jsonObj.get("fulfillment_status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `fulfillment_status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("fulfillment_status").toString()));
      }
      // validate the optional field `products_price`
      if (jsonObj.get("products_price") != null && !jsonObj.get("products_price").isJsonNull()) {
        LineItemsProperty.validateJsonObject(jsonObj.getAsJsonObject("products_price"));
      }
      // validate the optional field `products_variant`
      if (jsonObj.get("products_variant") != null && !jsonObj.get("products_variant").isJsonNull()) {
        LineItemsProperty1.validateJsonObject(jsonObj.getAsJsonObject("products_variant"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!OrdersInvoicesLineItemAttributes.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'OrdersInvoicesLineItemAttributes' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<OrdersInvoicesLineItemAttributes> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(OrdersInvoicesLineItemAttributes.class));

       return (TypeAdapter<T>) new TypeAdapter<OrdersInvoicesLineItemAttributes>() {
           @Override
           public void write(JsonWriter out, OrdersInvoicesLineItemAttributes value) throws IOException {
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
           public OrdersInvoicesLineItemAttributes read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             OrdersInvoicesLineItemAttributes instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of OrdersInvoicesLineItemAttributes given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of OrdersInvoicesLineItemAttributes
  * @throws IOException if the JSON string is invalid with respect to OrdersInvoicesLineItemAttributes
  */
  public static OrdersInvoicesLineItemAttributes fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, OrdersInvoicesLineItemAttributes.class);
  }

 /**
  * Convert an instance of OrdersInvoicesLineItemAttributes to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

