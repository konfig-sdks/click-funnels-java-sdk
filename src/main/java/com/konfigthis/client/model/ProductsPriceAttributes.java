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
 * Prices
 */
@ApiModel(description = "Prices")@javax.annotation.Generated(value = "Generated by https://konfigthis.com")
public class ProductsPriceAttributes {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_PUBLIC_ID = "public_id";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ID)
  private Object publicId = null;

  public static final String SERIALIZED_NAME_VARIANT_ID = "variant_id";
  @SerializedName(SERIALIZED_NAME_VARIANT_ID)
  private Object variantId = null;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private Object name = null;

  public static final String SERIALIZED_NAME_AMOUNT = "amount";
  @SerializedName(SERIALIZED_NAME_AMOUNT)
  private String amount;

  public static final String SERIALIZED_NAME_COST = "cost";
  @SerializedName(SERIALIZED_NAME_COST)
  private Object cost = null;

  public static final String SERIALIZED_NAME_CURRENCY = "currency";
  @SerializedName(SERIALIZED_NAME_CURRENCY)
  private String currency;

  public static final String SERIALIZED_NAME_DURATION = "duration";
  @SerializedName(SERIALIZED_NAME_DURATION)
  private Integer duration;

  public static final String SERIALIZED_NAME_INTERVAL = "interval";
  @SerializedName(SERIALIZED_NAME_INTERVAL)
  private String interval;

  public static final String SERIALIZED_NAME_TRIAL_INTERVAL = "trial_interval";
  @SerializedName(SERIALIZED_NAME_TRIAL_INTERVAL)
  private String trialInterval;

  public static final String SERIALIZED_NAME_TRIAL_DURATION = "trial_duration";
  @SerializedName(SERIALIZED_NAME_TRIAL_DURATION)
  private String trialDuration;

  public static final String SERIALIZED_NAME_TRIAL_AMOUNT = "trial_amount";
  @SerializedName(SERIALIZED_NAME_TRIAL_AMOUNT)
  private String trialAmount;

  public static final String SERIALIZED_NAME_SETUP_AMOUNT = "setup_amount";
  @SerializedName(SERIALIZED_NAME_SETUP_AMOUNT)
  private Object setupAmount = null;

  public static final String SERIALIZED_NAME_SELF_CANCEL = "self_cancel";
  @SerializedName(SERIALIZED_NAME_SELF_CANCEL)
  private Object selfCancel = null;

  public static final String SERIALIZED_NAME_SELF_UPGRADE = "self_upgrade";
  @SerializedName(SERIALIZED_NAME_SELF_UPGRADE)
  private Object selfUpgrade = null;

  public static final String SERIALIZED_NAME_SELF_DOWNGRADE = "self_downgrade";
  @SerializedName(SERIALIZED_NAME_SELF_DOWNGRADE)
  private Object selfDowngrade = null;

  public static final String SERIALIZED_NAME_SELF_REACTIVATE = "self_reactivate";
  @SerializedName(SERIALIZED_NAME_SELF_REACTIVATE)
  private Object selfReactivate = null;

  public static final String SERIALIZED_NAME_INTERVAL_COUNT = "interval_count";
  @SerializedName(SERIALIZED_NAME_INTERVAL_COUNT)
  private Integer intervalCount;

  /**
   * Payment Type
   */
  @JsonAdapter(PaymentTypeEnum.Adapter.class)
 public enum PaymentTypeEnum {
    ONE_TIME("one_time"),
    
    SUBSCRIPTION("subscription"),
    
    PAYMENT_PLAN("payment_plan");

    private String value;

    PaymentTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static PaymentTypeEnum fromValue(String value) {
      for (PaymentTypeEnum b : PaymentTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<PaymentTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PaymentTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PaymentTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return PaymentTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_PAYMENT_TYPE = "payment_type";
  @SerializedName(SERIALIZED_NAME_PAYMENT_TYPE)
  private PaymentTypeEnum paymentType;

  public static final String SERIALIZED_NAME_VISIBLE = "visible";
  @SerializedName(SERIALIZED_NAME_VISIBLE)
  private Object visible = null;

  public static final String SERIALIZED_NAME_COMPARE_AT_AMOUNT = "compare_at_amount";
  @SerializedName(SERIALIZED_NAME_COMPARE_AT_AMOUNT)
  private Object compareAtAmount = null;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private Object key = null;

  public static final String SERIALIZED_NAME_ARCHIVED = "archived";
  @SerializedName(SERIALIZED_NAME_ARCHIVED)
  private Object archived = null;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private Object createdAt = null;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private Object updatedAt = null;

  public ProductsPriceAttributes() {
  }

  public ProductsPriceAttributes id(Integer id) {
    
    
    
    
    this.id = id;
    return this;
  }

   /**
   * ID
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "14", required = true, value = "ID")

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    
    
    
    this.id = id;
  }


  public ProductsPriceAttributes publicId(Object publicId) {
    
    
    
    
    this.publicId = publicId;
    return this;
  }

   /**
   * Price public ID
   * @return publicId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Price public ID")

  public Object getPublicId() {
    return publicId;
  }


  public void setPublicId(Object publicId) {
    
    
    
    this.publicId = publicId;
  }


  public ProductsPriceAttributes variantId(Object variantId) {
    
    
    
    
    this.variantId = variantId;
    return this;
  }

   /**
   * Variant
   * @return variantId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Variant")

  public Object getVariantId() {
    return variantId;
  }


  public void setVariantId(Object variantId) {
    
    
    
    this.variantId = variantId;
  }


  public ProductsPriceAttributes name(Object name) {
    
    
    
    
    this.name = name;
    return this;
  }

   /**
   * Name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Name")

  public Object getName() {
    return name;
  }


  public void setName(Object name) {
    
    
    
    this.name = name;
  }


  public ProductsPriceAttributes amount(String amount) {
    
    
    
    
    this.amount = amount;
    return this;
  }

   /**
   * Amount
   * @return amount
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "100.00", required = true, value = "Amount")

  public String getAmount() {
    return amount;
  }


  public void setAmount(String amount) {
    
    
    
    this.amount = amount;
  }


  public ProductsPriceAttributes cost(Object cost) {
    
    
    
    
    this.cost = cost;
    return this;
  }

   /**
   * Cost
   * @return cost
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Cost")

  public Object getCost() {
    return cost;
  }


  public void setCost(Object cost) {
    
    
    
    this.cost = cost;
  }


  public ProductsPriceAttributes currency(String currency) {
    
    
    
    
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


  public ProductsPriceAttributes duration(Integer duration) {
    
    
    
    
    this.duration = duration;
    return this;
  }

   /**
   * Duration
   * @return duration
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "1", required = true, value = "Duration")

  public Integer getDuration() {
    return duration;
  }


  public void setDuration(Integer duration) {
    
    
    
    this.duration = duration;
  }


  public ProductsPriceAttributes interval(String interval) {
    
    
    
    
    this.interval = interval;
    return this;
  }

   /**
   * Interval
   * @return interval
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "months", required = true, value = "Interval")

  public String getInterval() {
    return interval;
  }


  public void setInterval(String interval) {
    
    
    
    this.interval = interval;
  }


  public ProductsPriceAttributes trialInterval(String trialInterval) {
    
    
    
    
    this.trialInterval = trialInterval;
    return this;
  }

   /**
   * Trial interval
   * @return trialInterval
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Trial interval")

  public String getTrialInterval() {
    return trialInterval;
  }


  public void setTrialInterval(String trialInterval) {
    
    
    
    this.trialInterval = trialInterval;
  }


  public ProductsPriceAttributes trialDuration(String trialDuration) {
    
    
    
    
    this.trialDuration = trialDuration;
    return this;
  }

   /**
   * Trial duration
   * @return trialDuration
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Trial duration")

  public String getTrialDuration() {
    return trialDuration;
  }


  public void setTrialDuration(String trialDuration) {
    
    
    
    this.trialDuration = trialDuration;
  }


  public ProductsPriceAttributes trialAmount(String trialAmount) {
    
    
    
    
    this.trialAmount = trialAmount;
    return this;
  }

   /**
   * Trial Amount
   * @return trialAmount
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "0.00", required = true, value = "Trial Amount")

  public String getTrialAmount() {
    return trialAmount;
  }


  public void setTrialAmount(String trialAmount) {
    
    
    
    this.trialAmount = trialAmount;
  }


  public ProductsPriceAttributes setupAmount(Object setupAmount) {
    
    
    
    
    this.setupAmount = setupAmount;
    return this;
  }

   /**
   * Setup amount
   * @return setupAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Setup amount")

  public Object getSetupAmount() {
    return setupAmount;
  }


  public void setSetupAmount(Object setupAmount) {
    
    
    
    this.setupAmount = setupAmount;
  }


  public ProductsPriceAttributes selfCancel(Object selfCancel) {
    
    
    
    
    this.selfCancel = selfCancel;
    return this;
  }

   /**
   * Customer can cancel
   * @return selfCancel
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Customer can cancel")

  public Object getSelfCancel() {
    return selfCancel;
  }


  public void setSelfCancel(Object selfCancel) {
    
    
    
    this.selfCancel = selfCancel;
  }


  public ProductsPriceAttributes selfUpgrade(Object selfUpgrade) {
    
    
    
    
    this.selfUpgrade = selfUpgrade;
    return this;
  }

   /**
   * Customer can upgrade
   * @return selfUpgrade
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Customer can upgrade")

  public Object getSelfUpgrade() {
    return selfUpgrade;
  }


  public void setSelfUpgrade(Object selfUpgrade) {
    
    
    
    this.selfUpgrade = selfUpgrade;
  }


  public ProductsPriceAttributes selfDowngrade(Object selfDowngrade) {
    
    
    
    
    this.selfDowngrade = selfDowngrade;
    return this;
  }

   /**
   * Customer can downgrade
   * @return selfDowngrade
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Customer can downgrade")

  public Object getSelfDowngrade() {
    return selfDowngrade;
  }


  public void setSelfDowngrade(Object selfDowngrade) {
    
    
    
    this.selfDowngrade = selfDowngrade;
  }


  public ProductsPriceAttributes selfReactivate(Object selfReactivate) {
    
    
    
    
    this.selfReactivate = selfReactivate;
    return this;
  }

   /**
   * Customer can reactivate
   * @return selfReactivate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Customer can reactivate")

  public Object getSelfReactivate() {
    return selfReactivate;
  }


  public void setSelfReactivate(Object selfReactivate) {
    
    
    
    this.selfReactivate = selfReactivate;
  }


  public ProductsPriceAttributes intervalCount(Integer intervalCount) {
    
    
    
    
    this.intervalCount = intervalCount;
    return this;
  }

   /**
   * Interval count
   * @return intervalCount
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "1", required = true, value = "Interval count")

  public Integer getIntervalCount() {
    return intervalCount;
  }


  public void setIntervalCount(Integer intervalCount) {
    
    
    
    this.intervalCount = intervalCount;
  }


  public ProductsPriceAttributes paymentType(PaymentTypeEnum paymentType) {
    
    
    
    
    this.paymentType = paymentType;
    return this;
  }

   /**
   * Payment Type
   * @return paymentType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Payment Type")

  public PaymentTypeEnum getPaymentType() {
    return paymentType;
  }


  public void setPaymentType(PaymentTypeEnum paymentType) {
    
    
    
    this.paymentType = paymentType;
  }


  public ProductsPriceAttributes visible(Object visible) {
    
    
    
    
    this.visible = visible;
    return this;
  }

   /**
   * Visible
   * @return visible
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Visible")

  public Object getVisible() {
    return visible;
  }


  public void setVisible(Object visible) {
    
    
    
    this.visible = visible;
  }


  public ProductsPriceAttributes compareAtAmount(Object compareAtAmount) {
    
    
    
    
    this.compareAtAmount = compareAtAmount;
    return this;
  }

   /**
   * Compare at amount
   * @return compareAtAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Compare at amount")

  public Object getCompareAtAmount() {
    return compareAtAmount;
  }


  public void setCompareAtAmount(Object compareAtAmount) {
    
    
    
    this.compareAtAmount = compareAtAmount;
  }


  public ProductsPriceAttributes key(Object key) {
    
    
    
    
    this.key = key;
    return this;
  }

   /**
   * Key
   * @return key
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Key")

  public Object getKey() {
    return key;
  }


  public void setKey(Object key) {
    
    
    
    this.key = key;
  }


  public ProductsPriceAttributes archived(Object archived) {
    
    
    
    
    this.archived = archived;
    return this;
  }

   /**
   * Archived
   * @return archived
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Archived")

  public Object getArchived() {
    return archived;
  }


  public void setArchived(Object archived) {
    
    
    
    this.archived = archived;
  }


  public ProductsPriceAttributes createdAt(Object createdAt) {
    
    
    
    
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


  public ProductsPriceAttributes updatedAt(Object updatedAt) {
    
    
    
    
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
   * @return the ProductsPriceAttributes instance itself
   */
  public ProductsPriceAttributes putAdditionalProperty(String key, Object value) {
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
    ProductsPriceAttributes productsPriceAttributes = (ProductsPriceAttributes) o;
    return Objects.equals(this.id, productsPriceAttributes.id) &&
        Objects.equals(this.publicId, productsPriceAttributes.publicId) &&
        Objects.equals(this.variantId, productsPriceAttributes.variantId) &&
        Objects.equals(this.name, productsPriceAttributes.name) &&
        Objects.equals(this.amount, productsPriceAttributes.amount) &&
        Objects.equals(this.cost, productsPriceAttributes.cost) &&
        Objects.equals(this.currency, productsPriceAttributes.currency) &&
        Objects.equals(this.duration, productsPriceAttributes.duration) &&
        Objects.equals(this.interval, productsPriceAttributes.interval) &&
        Objects.equals(this.trialInterval, productsPriceAttributes.trialInterval) &&
        Objects.equals(this.trialDuration, productsPriceAttributes.trialDuration) &&
        Objects.equals(this.trialAmount, productsPriceAttributes.trialAmount) &&
        Objects.equals(this.setupAmount, productsPriceAttributes.setupAmount) &&
        Objects.equals(this.selfCancel, productsPriceAttributes.selfCancel) &&
        Objects.equals(this.selfUpgrade, productsPriceAttributes.selfUpgrade) &&
        Objects.equals(this.selfDowngrade, productsPriceAttributes.selfDowngrade) &&
        Objects.equals(this.selfReactivate, productsPriceAttributes.selfReactivate) &&
        Objects.equals(this.intervalCount, productsPriceAttributes.intervalCount) &&
        Objects.equals(this.paymentType, productsPriceAttributes.paymentType) &&
        Objects.equals(this.visible, productsPriceAttributes.visible) &&
        Objects.equals(this.compareAtAmount, productsPriceAttributes.compareAtAmount) &&
        Objects.equals(this.key, productsPriceAttributes.key) &&
        Objects.equals(this.archived, productsPriceAttributes.archived) &&
        Objects.equals(this.createdAt, productsPriceAttributes.createdAt) &&
        Objects.equals(this.updatedAt, productsPriceAttributes.updatedAt)&&
        Objects.equals(this.additionalProperties, productsPriceAttributes.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publicId, variantId, name, amount, cost, currency, duration, interval, trialInterval, trialDuration, trialAmount, setupAmount, selfCancel, selfUpgrade, selfDowngrade, selfReactivate, intervalCount, paymentType, visible, compareAtAmount, key, archived, createdAt, updatedAt, additionalProperties);
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
    sb.append("class ProductsPriceAttributes {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    publicId: ").append(toIndentedString(publicId)).append("\n");
    sb.append("    variantId: ").append(toIndentedString(variantId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
    sb.append("    trialInterval: ").append(toIndentedString(trialInterval)).append("\n");
    sb.append("    trialDuration: ").append(toIndentedString(trialDuration)).append("\n");
    sb.append("    trialAmount: ").append(toIndentedString(trialAmount)).append("\n");
    sb.append("    setupAmount: ").append(toIndentedString(setupAmount)).append("\n");
    sb.append("    selfCancel: ").append(toIndentedString(selfCancel)).append("\n");
    sb.append("    selfUpgrade: ").append(toIndentedString(selfUpgrade)).append("\n");
    sb.append("    selfDowngrade: ").append(toIndentedString(selfDowngrade)).append("\n");
    sb.append("    selfReactivate: ").append(toIndentedString(selfReactivate)).append("\n");
    sb.append("    intervalCount: ").append(toIndentedString(intervalCount)).append("\n");
    sb.append("    paymentType: ").append(toIndentedString(paymentType)).append("\n");
    sb.append("    visible: ").append(toIndentedString(visible)).append("\n");
    sb.append("    compareAtAmount: ").append(toIndentedString(compareAtAmount)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("public_id");
    openapiFields.add("variant_id");
    openapiFields.add("name");
    openapiFields.add("amount");
    openapiFields.add("cost");
    openapiFields.add("currency");
    openapiFields.add("duration");
    openapiFields.add("interval");
    openapiFields.add("trial_interval");
    openapiFields.add("trial_duration");
    openapiFields.add("trial_amount");
    openapiFields.add("setup_amount");
    openapiFields.add("self_cancel");
    openapiFields.add("self_upgrade");
    openapiFields.add("self_downgrade");
    openapiFields.add("self_reactivate");
    openapiFields.add("interval_count");
    openapiFields.add("payment_type");
    openapiFields.add("visible");
    openapiFields.add("compare_at_amount");
    openapiFields.add("key");
    openapiFields.add("archived");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("amount");
    openapiRequiredFields.add("currency");
    openapiRequiredFields.add("duration");
    openapiRequiredFields.add("interval");
    openapiRequiredFields.add("trial_interval");
    openapiRequiredFields.add("trial_duration");
    openapiRequiredFields.add("trial_amount");
    openapiRequiredFields.add("interval_count");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to ProductsPriceAttributes
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!ProductsPriceAttributes.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ProductsPriceAttributes is not found in the empty JSON string", ProductsPriceAttributes.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ProductsPriceAttributes.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (!jsonObj.get("amount").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `amount` to be a primitive type in the JSON string but got `%s`", jsonObj.get("amount").toString()));
      }
      if (!jsonObj.get("currency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `currency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("currency").toString()));
      }
      if (!jsonObj.get("interval").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `interval` to be a primitive type in the JSON string but got `%s`", jsonObj.get("interval").toString()));
      }
      if (!jsonObj.get("trial_interval").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `trial_interval` to be a primitive type in the JSON string but got `%s`", jsonObj.get("trial_interval").toString()));
      }
      if (!jsonObj.get("trial_duration").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `trial_duration` to be a primitive type in the JSON string but got `%s`", jsonObj.get("trial_duration").toString()));
      }
      if (!jsonObj.get("trial_amount").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `trial_amount` to be a primitive type in the JSON string but got `%s`", jsonObj.get("trial_amount").toString()));
      }
      if ((jsonObj.get("payment_type") != null && !jsonObj.get("payment_type").isJsonNull()) && !jsonObj.get("payment_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `payment_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("payment_type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ProductsPriceAttributes.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ProductsPriceAttributes' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ProductsPriceAttributes> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ProductsPriceAttributes.class));

       return (TypeAdapter<T>) new TypeAdapter<ProductsPriceAttributes>() {
           @Override
           public void write(JsonWriter out, ProductsPriceAttributes value) throws IOException {
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
           public ProductsPriceAttributes read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             ProductsPriceAttributes instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of ProductsPriceAttributes given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ProductsPriceAttributes
  * @throws IOException if the JSON string is invalid with respect to ProductsPriceAttributes
  */
  public static ProductsPriceAttributes fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ProductsPriceAttributes.class);
  }

 /**
  * Convert an instance of ProductsPriceAttributes to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

