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
import com.konfigthis.client.model.OrdersInvoicesLineItemAttributes;
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
 * Invoices
 */
@ApiModel(description = "Invoices")@javax.annotation.Generated(value = "Generated by https://konfigthis.com")
public class OrdersInvoiceAttributes {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_PUBLIC_ID = "public_id";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ID)
  private Object publicId = null;

  public static final String SERIALIZED_NAME_ORDER_ID = "order_id";
  @SerializedName(SERIALIZED_NAME_ORDER_ID)
  private Integer orderId;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_DUE_AMOUNT = "due_amount";
  @SerializedName(SERIALIZED_NAME_DUE_AMOUNT)
  private Object dueAmount = null;

  public static final String SERIALIZED_NAME_TOTAL_AMOUNT = "total_amount";
  @SerializedName(SERIALIZED_NAME_TOTAL_AMOUNT)
  private Object totalAmount = null;

  public static final String SERIALIZED_NAME_SUBTOTAL_AMOUNT = "subtotal_amount";
  @SerializedName(SERIALIZED_NAME_SUBTOTAL_AMOUNT)
  private Object subtotalAmount = null;

  public static final String SERIALIZED_NAME_TAX_AMOUNT = "tax_amount";
  @SerializedName(SERIALIZED_NAME_TAX_AMOUNT)
  private Object taxAmount = null;

  public static final String SERIALIZED_NAME_SHIPPING_AMOUNT = "shipping_amount";
  @SerializedName(SERIALIZED_NAME_SHIPPING_AMOUNT)
  private Object shippingAmount = null;

  public static final String SERIALIZED_NAME_DISCOUNT_AMOUNT = "discount_amount";
  @SerializedName(SERIALIZED_NAME_DISCOUNT_AMOUNT)
  private Object discountAmount = null;

  public static final String SERIALIZED_NAME_CURRENCY = "currency";
  @SerializedName(SERIALIZED_NAME_CURRENCY)
  private Object currency = null;

  public static final String SERIALIZED_NAME_ISSUED_AT = "issued_at";
  @SerializedName(SERIALIZED_NAME_ISSUED_AT)
  private Object issuedAt = null;

  public static final String SERIALIZED_NAME_DUE_AT = "due_at";
  @SerializedName(SERIALIZED_NAME_DUE_AT)
  private Object dueAt = null;

  public static final String SERIALIZED_NAME_PAID_AT = "paid_at";
  @SerializedName(SERIALIZED_NAME_PAID_AT)
  private Object paidAt = null;

  /**
   * Invoice Type
   */
  @JsonAdapter(InvoiceTypeEnum.Adapter.class)
 public enum InvoiceTypeEnum {
    INITIAL("initial"),
    
    RENEWAL("renewal"),
    
    INTERIM("interim"),
    
    CANCELLATION("cancellation"),
    
    ONE_TIME("one_time"),
    
    REFUND("refund"),
    
    CHARGE("charge"),
    
    ONE_TIME_SALE("one_time_sale");

    private String value;

    InvoiceTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static InvoiceTypeEnum fromValue(String value) {
      for (InvoiceTypeEnum b : InvoiceTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<InvoiceTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final InvoiceTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public InvoiceTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return InvoiceTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_INVOICE_TYPE = "invoice_type";
  @SerializedName(SERIALIZED_NAME_INVOICE_TYPE)
  private InvoiceTypeEnum invoiceType;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private Object createdAt = null;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private Object updatedAt = null;

  public static final String SERIALIZED_NAME_LINE_ITEMS = "line_items";
  @SerializedName(SERIALIZED_NAME_LINE_ITEMS)
  private List<OrdersInvoicesLineItemAttributes> lineItems = null;

  public OrdersInvoiceAttributes() {
  }

  public OrdersInvoiceAttributes id(Integer id) {
    
    
    
    
    this.id = id;
    return this;
  }

   /**
   * Invoice ID
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "2", required = true, value = "Invoice ID")

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    
    
    
    this.id = id;
  }


  public OrdersInvoiceAttributes publicId(Object publicId) {
    
    
    
    
    this.publicId = publicId;
    return this;
  }

   /**
   * Invoice public ID
   * @return publicId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Invoice public ID")

  public Object getPublicId() {
    return publicId;
  }


  public void setPublicId(Object publicId) {
    
    
    
    this.publicId = publicId;
  }


  public OrdersInvoiceAttributes orderId(Integer orderId) {
    
    
    
    
    this.orderId = orderId;
    return this;
  }

   /**
   * Creative concept ID
   * @return orderId
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "4", required = true, value = "Creative concept ID")

  public Integer getOrderId() {
    return orderId;
  }


  public void setOrderId(Integer orderId) {
    
    
    
    this.orderId = orderId;
  }


  public OrdersInvoiceAttributes status(String status) {
    
    
    
    
    this.status = status;
    return this;
  }

   /**
   * Status
   * @return status
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "paid", required = true, value = "Status")

  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    
    
    
    this.status = status;
  }


  public OrdersInvoiceAttributes dueAmount(Object dueAmount) {
    
    
    
    
    this.dueAmount = dueAmount;
    return this;
  }

   /**
   * Due Amount
   * @return dueAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Due Amount")

  public Object getDueAmount() {
    return dueAmount;
  }


  public void setDueAmount(Object dueAmount) {
    
    
    
    this.dueAmount = dueAmount;
  }


  public OrdersInvoiceAttributes totalAmount(Object totalAmount) {
    
    
    
    
    this.totalAmount = totalAmount;
    return this;
  }

   /**
   * Total Amt
   * @return totalAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Total Amt")

  public Object getTotalAmount() {
    return totalAmount;
  }


  public void setTotalAmount(Object totalAmount) {
    
    
    
    this.totalAmount = totalAmount;
  }


  public OrdersInvoiceAttributes subtotalAmount(Object subtotalAmount) {
    
    
    
    
    this.subtotalAmount = subtotalAmount;
    return this;
  }

   /**
   * Subtotal Amount
   * @return subtotalAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Subtotal Amount")

  public Object getSubtotalAmount() {
    return subtotalAmount;
  }


  public void setSubtotalAmount(Object subtotalAmount) {
    
    
    
    this.subtotalAmount = subtotalAmount;
  }


  public OrdersInvoiceAttributes taxAmount(Object taxAmount) {
    
    
    
    
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


  public OrdersInvoiceAttributes shippingAmount(Object shippingAmount) {
    
    
    
    
    this.shippingAmount = shippingAmount;
    return this;
  }

   /**
   * Shipping Amount
   * @return shippingAmount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Shipping Amount")

  public Object getShippingAmount() {
    return shippingAmount;
  }


  public void setShippingAmount(Object shippingAmount) {
    
    
    
    this.shippingAmount = shippingAmount;
  }


  public OrdersInvoiceAttributes discountAmount(Object discountAmount) {
    
    
    
    
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


  public OrdersInvoiceAttributes currency(Object currency) {
    
    
    
    
    this.currency = currency;
    return this;
  }

   /**
   * Currency
   * @return currency
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Currency")

  public Object getCurrency() {
    return currency;
  }


  public void setCurrency(Object currency) {
    
    
    
    this.currency = currency;
  }


  public OrdersInvoiceAttributes issuedAt(Object issuedAt) {
    
    
    
    
    this.issuedAt = issuedAt;
    return this;
  }

   /**
   * Issued At
   * @return issuedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Issued At")

  public Object getIssuedAt() {
    return issuedAt;
  }


  public void setIssuedAt(Object issuedAt) {
    
    
    
    this.issuedAt = issuedAt;
  }


  public OrdersInvoiceAttributes dueAt(Object dueAt) {
    
    
    
    
    this.dueAt = dueAt;
    return this;
  }

   /**
   * Due At
   * @return dueAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Due At")

  public Object getDueAt() {
    return dueAt;
  }


  public void setDueAt(Object dueAt) {
    
    
    
    this.dueAt = dueAt;
  }


  public OrdersInvoiceAttributes paidAt(Object paidAt) {
    
    
    
    
    this.paidAt = paidAt;
    return this;
  }

   /**
   * Paid At
   * @return paidAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Paid At")

  public Object getPaidAt() {
    return paidAt;
  }


  public void setPaidAt(Object paidAt) {
    
    
    
    this.paidAt = paidAt;
  }


  public OrdersInvoiceAttributes invoiceType(InvoiceTypeEnum invoiceType) {
    
    
    
    
    this.invoiceType = invoiceType;
    return this;
  }

   /**
   * Invoice Type
   * @return invoiceType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Invoice Type")

  public InvoiceTypeEnum getInvoiceType() {
    return invoiceType;
  }


  public void setInvoiceType(InvoiceTypeEnum invoiceType) {
    
    
    
    this.invoiceType = invoiceType;
  }


  public OrdersInvoiceAttributes createdAt(Object createdAt) {
    
    
    
    
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


  public OrdersInvoiceAttributes updatedAt(Object updatedAt) {
    
    
    
    
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


  public OrdersInvoiceAttributes lineItems(List<OrdersInvoicesLineItemAttributes> lineItems) {
    
    
    
    
    this.lineItems = lineItems;
    return this;
  }

  public OrdersInvoiceAttributes addLineItemsItem(OrdersInvoicesLineItemAttributes lineItemsItem) {
    if (this.lineItems == null) {
      this.lineItems = new ArrayList<>();
    }
    this.lineItems.add(lineItemsItem);
    return this;
  }

   /**
   * Invoice Line Items
   * @return lineItems
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Invoice Line Items")

  public List<OrdersInvoicesLineItemAttributes> getLineItems() {
    return lineItems;
  }


  public void setLineItems(List<OrdersInvoicesLineItemAttributes> lineItems) {
    
    
    
    this.lineItems = lineItems;
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
   * @return the OrdersInvoiceAttributes instance itself
   */
  public OrdersInvoiceAttributes putAdditionalProperty(String key, Object value) {
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
    OrdersInvoiceAttributes ordersInvoiceAttributes = (OrdersInvoiceAttributes) o;
    return Objects.equals(this.id, ordersInvoiceAttributes.id) &&
        Objects.equals(this.publicId, ordersInvoiceAttributes.publicId) &&
        Objects.equals(this.orderId, ordersInvoiceAttributes.orderId) &&
        Objects.equals(this.status, ordersInvoiceAttributes.status) &&
        Objects.equals(this.dueAmount, ordersInvoiceAttributes.dueAmount) &&
        Objects.equals(this.totalAmount, ordersInvoiceAttributes.totalAmount) &&
        Objects.equals(this.subtotalAmount, ordersInvoiceAttributes.subtotalAmount) &&
        Objects.equals(this.taxAmount, ordersInvoiceAttributes.taxAmount) &&
        Objects.equals(this.shippingAmount, ordersInvoiceAttributes.shippingAmount) &&
        Objects.equals(this.discountAmount, ordersInvoiceAttributes.discountAmount) &&
        Objects.equals(this.currency, ordersInvoiceAttributes.currency) &&
        Objects.equals(this.issuedAt, ordersInvoiceAttributes.issuedAt) &&
        Objects.equals(this.dueAt, ordersInvoiceAttributes.dueAt) &&
        Objects.equals(this.paidAt, ordersInvoiceAttributes.paidAt) &&
        Objects.equals(this.invoiceType, ordersInvoiceAttributes.invoiceType) &&
        Objects.equals(this.createdAt, ordersInvoiceAttributes.createdAt) &&
        Objects.equals(this.updatedAt, ordersInvoiceAttributes.updatedAt) &&
        Objects.equals(this.lineItems, ordersInvoiceAttributes.lineItems)&&
        Objects.equals(this.additionalProperties, ordersInvoiceAttributes.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publicId, orderId, status, dueAmount, totalAmount, subtotalAmount, taxAmount, shippingAmount, discountAmount, currency, issuedAt, dueAt, paidAt, invoiceType, createdAt, updatedAt, lineItems, additionalProperties);
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
    sb.append("class OrdersInvoiceAttributes {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    publicId: ").append(toIndentedString(publicId)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    dueAmount: ").append(toIndentedString(dueAmount)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    subtotalAmount: ").append(toIndentedString(subtotalAmount)).append("\n");
    sb.append("    taxAmount: ").append(toIndentedString(taxAmount)).append("\n");
    sb.append("    shippingAmount: ").append(toIndentedString(shippingAmount)).append("\n");
    sb.append("    discountAmount: ").append(toIndentedString(discountAmount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    issuedAt: ").append(toIndentedString(issuedAt)).append("\n");
    sb.append("    dueAt: ").append(toIndentedString(dueAt)).append("\n");
    sb.append("    paidAt: ").append(toIndentedString(paidAt)).append("\n");
    sb.append("    invoiceType: ").append(toIndentedString(invoiceType)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    lineItems: ").append(toIndentedString(lineItems)).append("\n");
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
    openapiFields.add("order_id");
    openapiFields.add("status");
    openapiFields.add("due_amount");
    openapiFields.add("total_amount");
    openapiFields.add("subtotal_amount");
    openapiFields.add("tax_amount");
    openapiFields.add("shipping_amount");
    openapiFields.add("discount_amount");
    openapiFields.add("currency");
    openapiFields.add("issued_at");
    openapiFields.add("due_at");
    openapiFields.add("paid_at");
    openapiFields.add("invoice_type");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");
    openapiFields.add("line_items");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("order_id");
    openapiRequiredFields.add("status");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to OrdersInvoiceAttributes
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!OrdersInvoiceAttributes.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in OrdersInvoiceAttributes is not found in the empty JSON string", OrdersInvoiceAttributes.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : OrdersInvoiceAttributes.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if ((jsonObj.get("invoice_type") != null && !jsonObj.get("invoice_type").isJsonNull()) && !jsonObj.get("invoice_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `invoice_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("invoice_type").toString()));
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
            OrdersInvoicesLineItemAttributes.validateJsonObject(jsonArraylineItems.get(i).getAsJsonObject());
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!OrdersInvoiceAttributes.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'OrdersInvoiceAttributes' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<OrdersInvoiceAttributes> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(OrdersInvoiceAttributes.class));

       return (TypeAdapter<T>) new TypeAdapter<OrdersInvoiceAttributes>() {
           @Override
           public void write(JsonWriter out, OrdersInvoiceAttributes value) throws IOException {
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
           public OrdersInvoiceAttributes read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             OrdersInvoiceAttributes instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of OrdersInvoiceAttributes given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of OrdersInvoiceAttributes
  * @throws IOException if the JSON string is invalid with respect to OrdersInvoiceAttributes
  */
  public static OrdersInvoiceAttributes fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, OrdersInvoiceAttributes.class);
  }

 /**
  * Convert an instance of OrdersInvoiceAttributes to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

