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
import com.konfigthis.client.model.ContactTagAttributes;
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
 * Contacts
 */
@ApiModel(description = "Contacts")@javax.annotation.Generated(value = "Generated by https://konfigthis.com")
public class ContactAttributes {
  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<ContactTagAttributes> tags = null;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_PUBLIC_ID = "public_id";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ID)
  private Object publicId = null;

  public static final String SERIALIZED_NAME_WORKSPACE_ID = "workspace_id";
  @SerializedName(SERIALIZED_NAME_WORKSPACE_ID)
  private Integer workspaceId;

  public static final String SERIALIZED_NAME_ANONYMOUS = "anonymous";
  @SerializedName(SERIALIZED_NAME_ANONYMOUS)
  private Object anonymous = null;

  public static final String SERIALIZED_NAME_EMAIL_ADDRESS = "email_address";
  @SerializedName(SERIALIZED_NAME_EMAIL_ADDRESS)
  private Object emailAddress = null;

  public static final String SERIALIZED_NAME_FIRST_NAME = "first_name";
  @SerializedName(SERIALIZED_NAME_FIRST_NAME)
  private Object firstName = null;

  public static final String SERIALIZED_NAME_LAST_NAME = "last_name";
  @SerializedName(SERIALIZED_NAME_LAST_NAME)
  private Object lastName = null;

  public static final String SERIALIZED_NAME_PHONE_NUMBER = "phone_number";
  @SerializedName(SERIALIZED_NAME_PHONE_NUMBER)
  private Object phoneNumber = null;

  public static final String SERIALIZED_NAME_TIME_ZONE = "time_zone";
  @SerializedName(SERIALIZED_NAME_TIME_ZONE)
  private Object timeZone = null;

  public static final String SERIALIZED_NAME_UUID = "uuid";
  @SerializedName(SERIALIZED_NAME_UUID)
  private String uuid;

  public static final String SERIALIZED_NAME_UNSUBSCRIBED_AT = "unsubscribed_at";
  @SerializedName(SERIALIZED_NAME_UNSUBSCRIBED_AT)
  private Object unsubscribedAt = null;

  public static final String SERIALIZED_NAME_LAST_NOTIFICATION_EMAIL_SENT_AT = "last_notification_email_sent_at";
  @SerializedName(SERIALIZED_NAME_LAST_NOTIFICATION_EMAIL_SENT_AT)
  private Object lastNotificationEmailSentAt = null;

  public static final String SERIALIZED_NAME_FB_URL = "fb_url";
  @SerializedName(SERIALIZED_NAME_FB_URL)
  private Object fbUrl = null;

  public static final String SERIALIZED_NAME_TWITTER_URL = "twitter_url";
  @SerializedName(SERIALIZED_NAME_TWITTER_URL)
  private Object twitterUrl = null;

  public static final String SERIALIZED_NAME_INSTAGRAM_URL = "instagram_url";
  @SerializedName(SERIALIZED_NAME_INSTAGRAM_URL)
  private Object instagramUrl = null;

  public static final String SERIALIZED_NAME_LINKEDIN_URL = "linkedin_url";
  @SerializedName(SERIALIZED_NAME_LINKEDIN_URL)
  private Object linkedinUrl = null;

  public static final String SERIALIZED_NAME_WEBSITE_URL = "website_url";
  @SerializedName(SERIALIZED_NAME_WEBSITE_URL)
  private Object websiteUrl = null;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private Object createdAt = null;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private Object updatedAt = null;

  public ContactAttributes() {
  }

  public ContactAttributes tags(List<ContactTagAttributes> tags) {
    
    
    
    
    this.tags = tags;
    return this;
  }

  public ContactAttributes addTagsItem(ContactTagAttributes tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Tags
   * @return tags
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Tags")

  public List<ContactTagAttributes> getTags() {
    return tags;
  }


  public void setTags(List<ContactTagAttributes> tags) {
    
    
    
    this.tags = tags;
  }


  public ContactAttributes id(Integer id) {
    
    
    
    
    this.id = id;
    return this;
  }

   /**
   * Contact ID
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "1", required = true, value = "Contact ID")

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    
    
    
    this.id = id;
  }


  public ContactAttributes publicId(Object publicId) {
    
    
    
    
    this.publicId = publicId;
    return this;
  }

   /**
   * Contact public ID
   * @return publicId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Contact public ID")

  public Object getPublicId() {
    return publicId;
  }


  public void setPublicId(Object publicId) {
    
    
    
    this.publicId = publicId;
  }


  public ContactAttributes workspaceId(Integer workspaceId) {
    
    
    
    
    this.workspaceId = workspaceId;
    return this;
  }

   /**
   * Workspace ID
   * @return workspaceId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "42", required = true, value = "Workspace ID")

  public Integer getWorkspaceId() {
    return workspaceId;
  }


  public void setWorkspaceId(Integer workspaceId) {
    
    
    
    this.workspaceId = workspaceId;
  }


  public ContactAttributes anonymous(Object anonymous) {
    
    
    
    
    this.anonymous = anonymous;
    return this;
  }

   /**
   * Anonymous
   * @return anonymous
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Anonymous")

  public Object getAnonymous() {
    return anonymous;
  }


  public void setAnonymous(Object anonymous) {
    
    
    
    this.anonymous = anonymous;
  }


  public ContactAttributes emailAddress(Object emailAddress) {
    
    
    
    
    this.emailAddress = emailAddress;
    return this;
  }

   /**
   * Email address
   * @return emailAddress
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Email address")

  public Object getEmailAddress() {
    return emailAddress;
  }


  public void setEmailAddress(Object emailAddress) {
    
    
    
    this.emailAddress = emailAddress;
  }


  public ContactAttributes firstName(Object firstName) {
    
    
    
    
    this.firstName = firstName;
    return this;
  }

   /**
   * First name
   * @return firstName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "First name")

  public Object getFirstName() {
    return firstName;
  }


  public void setFirstName(Object firstName) {
    
    
    
    this.firstName = firstName;
  }


  public ContactAttributes lastName(Object lastName) {
    
    
    
    
    this.lastName = lastName;
    return this;
  }

   /**
   * Last name
   * @return lastName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Last name")

  public Object getLastName() {
    return lastName;
  }


  public void setLastName(Object lastName) {
    
    
    
    this.lastName = lastName;
  }


  public ContactAttributes phoneNumber(Object phoneNumber) {
    
    
    
    
    this.phoneNumber = phoneNumber;
    return this;
  }

   /**
   * Phone number
   * @return phoneNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Phone number")

  public Object getPhoneNumber() {
    return phoneNumber;
  }


  public void setPhoneNumber(Object phoneNumber) {
    
    
    
    this.phoneNumber = phoneNumber;
  }


  public ContactAttributes timeZone(Object timeZone) {
    
    
    
    
    this.timeZone = timeZone;
    return this;
  }

   /**
   * Time Zone
   * @return timeZone
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Time Zone")

  public Object getTimeZone() {
    return timeZone;
  }


  public void setTimeZone(Object timeZone) {
    
    
    
    this.timeZone = timeZone;
  }


  public ContactAttributes uuid(String uuid) {
    
    
    
    
    this.uuid = uuid;
    return this;
  }

   /**
   * UUID
   * @return uuid
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "e02e0741-bbb1-4889-8d77-08364dcc341c", required = true, value = "UUID")

  public String getUuid() {
    return uuid;
  }


  public void setUuid(String uuid) {
    
    
    
    this.uuid = uuid;
  }


  public ContactAttributes unsubscribedAt(Object unsubscribedAt) {
    
    
    
    
    this.unsubscribedAt = unsubscribedAt;
    return this;
  }

   /**
   * Unsubscribed
   * @return unsubscribedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Unsubscribed")

  public Object getUnsubscribedAt() {
    return unsubscribedAt;
  }


  public void setUnsubscribedAt(Object unsubscribedAt) {
    
    
    
    this.unsubscribedAt = unsubscribedAt;
  }


  public ContactAttributes lastNotificationEmailSentAt(Object lastNotificationEmailSentAt) {
    
    
    
    
    this.lastNotificationEmailSentAt = lastNotificationEmailSentAt;
    return this;
  }

   /**
   * Last notification email sent
   * @return lastNotificationEmailSentAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Last notification email sent")

  public Object getLastNotificationEmailSentAt() {
    return lastNotificationEmailSentAt;
  }


  public void setLastNotificationEmailSentAt(Object lastNotificationEmailSentAt) {
    
    
    
    this.lastNotificationEmailSentAt = lastNotificationEmailSentAt;
  }


  public ContactAttributes fbUrl(Object fbUrl) {
    
    
    
    
    this.fbUrl = fbUrl;
    return this;
  }

   /**
   * Facebook URL
   * @return fbUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Facebook URL")

  public Object getFbUrl() {
    return fbUrl;
  }


  public void setFbUrl(Object fbUrl) {
    
    
    
    this.fbUrl = fbUrl;
  }


  public ContactAttributes twitterUrl(Object twitterUrl) {
    
    
    
    
    this.twitterUrl = twitterUrl;
    return this;
  }

   /**
   * Twitter URL
   * @return twitterUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Twitter URL")

  public Object getTwitterUrl() {
    return twitterUrl;
  }


  public void setTwitterUrl(Object twitterUrl) {
    
    
    
    this.twitterUrl = twitterUrl;
  }


  public ContactAttributes instagramUrl(Object instagramUrl) {
    
    
    
    
    this.instagramUrl = instagramUrl;
    return this;
  }

   /**
   * Instagram URL
   * @return instagramUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Instagram URL")

  public Object getInstagramUrl() {
    return instagramUrl;
  }


  public void setInstagramUrl(Object instagramUrl) {
    
    
    
    this.instagramUrl = instagramUrl;
  }


  public ContactAttributes linkedinUrl(Object linkedinUrl) {
    
    
    
    
    this.linkedinUrl = linkedinUrl;
    return this;
  }

   /**
   * Linkedin URL
   * @return linkedinUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Linkedin URL")

  public Object getLinkedinUrl() {
    return linkedinUrl;
  }


  public void setLinkedinUrl(Object linkedinUrl) {
    
    
    
    this.linkedinUrl = linkedinUrl;
  }


  public ContactAttributes websiteUrl(Object websiteUrl) {
    
    
    
    
    this.websiteUrl = websiteUrl;
    return this;
  }

   /**
   * Website URL
   * @return websiteUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Website URL")

  public Object getWebsiteUrl() {
    return websiteUrl;
  }


  public void setWebsiteUrl(Object websiteUrl) {
    
    
    
    this.websiteUrl = websiteUrl;
  }


  public ContactAttributes createdAt(Object createdAt) {
    
    
    
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Date added
   * @return createdAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Date added")

  public Object getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(Object createdAt) {
    
    
    
    this.createdAt = createdAt;
  }


  public ContactAttributes updatedAt(Object updatedAt) {
    
    
    
    
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Last updated
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Last updated")

  public Object getUpdatedAt() {
    return updatedAt;
  }


  public void setUpdatedAt(Object updatedAt) {
    
    
    
    this.updatedAt = updatedAt;
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
   * @return the ContactAttributes instance itself
   */
  public ContactAttributes putAdditionalProperty(String key, Object value) {
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
    ContactAttributes contactAttributes = (ContactAttributes) o;
    return Objects.equals(this.tags, contactAttributes.tags) &&
        Objects.equals(this.id, contactAttributes.id) &&
        Objects.equals(this.publicId, contactAttributes.publicId) &&
        Objects.equals(this.workspaceId, contactAttributes.workspaceId) &&
        Objects.equals(this.anonymous, contactAttributes.anonymous) &&
        Objects.equals(this.emailAddress, contactAttributes.emailAddress) &&
        Objects.equals(this.firstName, contactAttributes.firstName) &&
        Objects.equals(this.lastName, contactAttributes.lastName) &&
        Objects.equals(this.phoneNumber, contactAttributes.phoneNumber) &&
        Objects.equals(this.timeZone, contactAttributes.timeZone) &&
        Objects.equals(this.uuid, contactAttributes.uuid) &&
        Objects.equals(this.unsubscribedAt, contactAttributes.unsubscribedAt) &&
        Objects.equals(this.lastNotificationEmailSentAt, contactAttributes.lastNotificationEmailSentAt) &&
        Objects.equals(this.fbUrl, contactAttributes.fbUrl) &&
        Objects.equals(this.twitterUrl, contactAttributes.twitterUrl) &&
        Objects.equals(this.instagramUrl, contactAttributes.instagramUrl) &&
        Objects.equals(this.linkedinUrl, contactAttributes.linkedinUrl) &&
        Objects.equals(this.websiteUrl, contactAttributes.websiteUrl) &&
        Objects.equals(this.createdAt, contactAttributes.createdAt) &&
        Objects.equals(this.updatedAt, contactAttributes.updatedAt)&&
        Objects.equals(this.additionalProperties, contactAttributes.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(tags, id, publicId, workspaceId, anonymous, emailAddress, firstName, lastName, phoneNumber, timeZone, uuid, unsubscribedAt, lastNotificationEmailSentAt, fbUrl, twitterUrl, instagramUrl, linkedinUrl, websiteUrl, createdAt, updatedAt, additionalProperties);
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
    sb.append("class ContactAttributes {\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    publicId: ").append(toIndentedString(publicId)).append("\n");
    sb.append("    workspaceId: ").append(toIndentedString(workspaceId)).append("\n");
    sb.append("    anonymous: ").append(toIndentedString(anonymous)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    timeZone: ").append(toIndentedString(timeZone)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    unsubscribedAt: ").append(toIndentedString(unsubscribedAt)).append("\n");
    sb.append("    lastNotificationEmailSentAt: ").append(toIndentedString(lastNotificationEmailSentAt)).append("\n");
    sb.append("    fbUrl: ").append(toIndentedString(fbUrl)).append("\n");
    sb.append("    twitterUrl: ").append(toIndentedString(twitterUrl)).append("\n");
    sb.append("    instagramUrl: ").append(toIndentedString(instagramUrl)).append("\n");
    sb.append("    linkedinUrl: ").append(toIndentedString(linkedinUrl)).append("\n");
    sb.append("    websiteUrl: ").append(toIndentedString(websiteUrl)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
    openapiFields.add("tags");
    openapiFields.add("id");
    openapiFields.add("public_id");
    openapiFields.add("workspace_id");
    openapiFields.add("anonymous");
    openapiFields.add("email_address");
    openapiFields.add("first_name");
    openapiFields.add("last_name");
    openapiFields.add("phone_number");
    openapiFields.add("time_zone");
    openapiFields.add("uuid");
    openapiFields.add("unsubscribed_at");
    openapiFields.add("last_notification_email_sent_at");
    openapiFields.add("fb_url");
    openapiFields.add("twitter_url");
    openapiFields.add("instagram_url");
    openapiFields.add("linkedin_url");
    openapiFields.add("website_url");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("workspace_id");
    openapiRequiredFields.add("uuid");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to ContactAttributes
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!ContactAttributes.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ContactAttributes is not found in the empty JSON string", ContactAttributes.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ContactAttributes.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonNull()) {
        JsonArray jsonArraytags = jsonObj.getAsJsonArray("tags");
        if (jsonArraytags != null) {
          // ensure the json data is an array
          if (!jsonObj.get("tags").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
          }

          // validate the optional field `tags` (array)
          for (int i = 0; i < jsonArraytags.size(); i++) {
            ContactTagAttributes.validateJsonObject(jsonArraytags.get(i).getAsJsonObject());
          };
        }
      }
      if (!jsonObj.get("uuid").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `uuid` to be a primitive type in the JSON string but got `%s`", jsonObj.get("uuid").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ContactAttributes.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ContactAttributes' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ContactAttributes> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ContactAttributes.class));

       return (TypeAdapter<T>) new TypeAdapter<ContactAttributes>() {
           @Override
           public void write(JsonWriter out, ContactAttributes value) throws IOException {
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
           public ContactAttributes read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             ContactAttributes instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of ContactAttributes given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ContactAttributes
  * @throws IOException if the JSON string is invalid with respect to ContactAttributes
  */
  public static ContactAttributes fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ContactAttributes.class);
  }

 /**
  * Convert an instance of ContactAttributes to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

