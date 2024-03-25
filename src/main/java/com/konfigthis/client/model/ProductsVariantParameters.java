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
import com.konfigthis.client.model.ProductsVariantParametersPropertiesValuesInner;
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
 * Variants
 */
@ApiModel(description = "Variants")@javax.annotation.Generated(value = "Generated by https://konfigthis.com")
public class ProductsVariantParameters {
  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private Object description = null;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_SKU = "sku";
  @SerializedName(SERIALIZED_NAME_SKU)
  private Object sku = null;

  public static final String SERIALIZED_NAME_PRODUCT_TYPE = "product_type";
  @SerializedName(SERIALIZED_NAME_PRODUCT_TYPE)
  private String productType;

  public static final String SERIALIZED_NAME_OUT_OF_STOCK_SALES = "out_of_stock_sales";
  @SerializedName(SERIALIZED_NAME_OUT_OF_STOCK_SALES)
  private Object outOfStockSales = null;

  public static final String SERIALIZED_NAME_WEIGHT = "weight";
  @SerializedName(SERIALIZED_NAME_WEIGHT)
  private Object weight = null;

  public static final String SERIALIZED_NAME_WEIGHT_UNIT = "weight_unit";
  @SerializedName(SERIALIZED_NAME_WEIGHT_UNIT)
  private String weightUnit;

  public static final String SERIALIZED_NAME_HEIGHT = "height";
  @SerializedName(SERIALIZED_NAME_HEIGHT)
  private Object height = null;

  public static final String SERIALIZED_NAME_WIDTH = "width";
  @SerializedName(SERIALIZED_NAME_WIDTH)
  private Object width = null;

  public static final String SERIALIZED_NAME_LENGTH = "length";
  @SerializedName(SERIALIZED_NAME_LENGTH)
  private Object length = null;

  public static final String SERIALIZED_NAME_DIMENSIONS_UNIT = "dimensions_unit";
  @SerializedName(SERIALIZED_NAME_DIMENSIONS_UNIT)
  private Object dimensionsUnit = null;

  public static final String SERIALIZED_NAME_TAX_CATEGORY_ID = "tax_category_id";
  @SerializedName(SERIALIZED_NAME_TAX_CATEGORY_ID)
  private Object taxCategoryId = null;

  public static final String SERIALIZED_NAME_TAXABLE = "taxable";
  @SerializedName(SERIALIZED_NAME_TAXABLE)
  private Object taxable = null;

  public static final String SERIALIZED_NAME_TRACK_QUANTITY = "track_quantity";
  @SerializedName(SERIALIZED_NAME_TRACK_QUANTITY)
  private Object trackQuantity = null;

  public static final String SERIALIZED_NAME_ARCHIVED = "archived";
  @SerializedName(SERIALIZED_NAME_ARCHIVED)
  private Object archived = null;

  public static final String SERIALIZED_NAME_VISIBLE = "visible";
  @SerializedName(SERIALIZED_NAME_VISIBLE)
  private Object visible = null;

  public static final String SERIALIZED_NAME_FULFILLMENT_REQUIRED = "fulfillment_required";
  @SerializedName(SERIALIZED_NAME_FULFILLMENT_REQUIRED)
  private Object fulfillmentRequired = null;

  public static final String SERIALIZED_NAME_COUNTRY_OF_MANUFACTURE_ID = "country_of_manufacture_id";
  @SerializedName(SERIALIZED_NAME_COUNTRY_OF_MANUFACTURE_ID)
  private Object countryOfManufactureId = null;

  public static final String SERIALIZED_NAME_IMAGE_IDS = "image_ids";
  @SerializedName(SERIALIZED_NAME_IMAGE_IDS)
  private Object imageIds = null;

  public static final String SERIALIZED_NAME_FULFILLMENTS_LOCATION_IDS = "fulfillments_location_ids";
  @SerializedName(SERIALIZED_NAME_FULFILLMENTS_LOCATION_IDS)
  private Object fulfillmentsLocationIds = null;

  public static final String SERIALIZED_NAME_PROPERTIES_VALUES = "properties_values";
  @SerializedName(SERIALIZED_NAME_PROPERTIES_VALUES)
  private List<ProductsVariantParametersPropertiesValuesInner> propertiesValues = null;

  public ProductsVariantParameters() {
  }

  public ProductsVariantParameters description(Object description) {
    
    
    
    
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


  public ProductsVariantParameters name(String name) {
    
    
    
    
    this.name = name;
    return this;
  }

   /**
   * Name
   * @return name
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "Lightweight Bronze Clock variant e702", required = true, value = "Name")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    
    
    
    this.name = name;
  }


  public ProductsVariantParameters sku(Object sku) {
    
    
    
    
    this.sku = sku;
    return this;
  }

   /**
   * SKU
   * @return sku
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "SKU")

  public Object getSku() {
    return sku;
  }


  public void setSku(Object sku) {
    
    
    
    this.sku = sku;
  }


  public ProductsVariantParameters productType(String productType) {
    
    
    
    
    this.productType = productType;
    return this;
  }

   /**
   * Type
   * @return productType
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "physical", required = true, value = "Type")

  public String getProductType() {
    return productType;
  }


  public void setProductType(String productType) {
    
    
    
    this.productType = productType;
  }


  public ProductsVariantParameters outOfStockSales(Object outOfStockSales) {
    
    
    
    
    this.outOfStockSales = outOfStockSales;
    return this;
  }

   /**
   * Out of stock sales
   * @return outOfStockSales
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Out of stock sales")

  public Object getOutOfStockSales() {
    return outOfStockSales;
  }


  public void setOutOfStockSales(Object outOfStockSales) {
    
    
    
    this.outOfStockSales = outOfStockSales;
  }


  public ProductsVariantParameters weight(Object weight) {
    
    
    
    
    this.weight = weight;
    return this;
  }

   /**
   * Weight
   * @return weight
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Weight")

  public Object getWeight() {
    return weight;
  }


  public void setWeight(Object weight) {
    
    
    
    this.weight = weight;
  }


  public ProductsVariantParameters weightUnit(String weightUnit) {
    
    
    
    
    this.weightUnit = weightUnit;
    return this;
  }

   /**
   * Weight unit
   * @return weightUnit
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "lb", required = true, value = "Weight unit")

  public String getWeightUnit() {
    return weightUnit;
  }


  public void setWeightUnit(String weightUnit) {
    
    
    
    this.weightUnit = weightUnit;
  }


  public ProductsVariantParameters height(Object height) {
    
    
    
    
    this.height = height;
    return this;
  }

   /**
   * Height
   * @return height
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Height")

  public Object getHeight() {
    return height;
  }


  public void setHeight(Object height) {
    
    
    
    this.height = height;
  }


  public ProductsVariantParameters width(Object width) {
    
    
    
    
    this.width = width;
    return this;
  }

   /**
   * Width
   * @return width
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Width")

  public Object getWidth() {
    return width;
  }


  public void setWidth(Object width) {
    
    
    
    this.width = width;
  }


  public ProductsVariantParameters length(Object length) {
    
    
    
    
    this.length = length;
    return this;
  }

   /**
   * Length
   * @return length
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Length")

  public Object getLength() {
    return length;
  }


  public void setLength(Object length) {
    
    
    
    this.length = length;
  }


  public ProductsVariantParameters dimensionsUnit(Object dimensionsUnit) {
    
    
    
    
    this.dimensionsUnit = dimensionsUnit;
    return this;
  }

   /**
   * Dimensions unit
   * @return dimensionsUnit
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Dimensions unit")

  public Object getDimensionsUnit() {
    return dimensionsUnit;
  }


  public void setDimensionsUnit(Object dimensionsUnit) {
    
    
    
    this.dimensionsUnit = dimensionsUnit;
  }


  public ProductsVariantParameters taxCategoryId(Object taxCategoryId) {
    
    
    
    
    this.taxCategoryId = taxCategoryId;
    return this;
  }

   /**
   * Tax category ID
   * @return taxCategoryId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Tax category ID")

  public Object getTaxCategoryId() {
    return taxCategoryId;
  }


  public void setTaxCategoryId(Object taxCategoryId) {
    
    
    
    this.taxCategoryId = taxCategoryId;
  }


  public ProductsVariantParameters taxable(Object taxable) {
    
    
    
    
    this.taxable = taxable;
    return this;
  }

   /**
   * Taxable
   * @return taxable
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Taxable")

  public Object getTaxable() {
    return taxable;
  }


  public void setTaxable(Object taxable) {
    
    
    
    this.taxable = taxable;
  }


  public ProductsVariantParameters trackQuantity(Object trackQuantity) {
    
    
    
    
    this.trackQuantity = trackQuantity;
    return this;
  }

   /**
   * Track Quantity
   * @return trackQuantity
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Track Quantity")

  public Object getTrackQuantity() {
    return trackQuantity;
  }


  public void setTrackQuantity(Object trackQuantity) {
    
    
    
    this.trackQuantity = trackQuantity;
  }


  public ProductsVariantParameters archived(Object archived) {
    
    
    
    
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


  public ProductsVariantParameters visible(Object visible) {
    
    
    
    
    this.visible = visible;
    return this;
  }

   /**
   * Is visible
   * @return visible
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Is visible")

  public Object getVisible() {
    return visible;
  }


  public void setVisible(Object visible) {
    
    
    
    this.visible = visible;
  }


  public ProductsVariantParameters fulfillmentRequired(Object fulfillmentRequired) {
    
    
    
    
    this.fulfillmentRequired = fulfillmentRequired;
    return this;
  }

   /**
   * Fulfillment required
   * @return fulfillmentRequired
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Fulfillment required")

  public Object getFulfillmentRequired() {
    return fulfillmentRequired;
  }


  public void setFulfillmentRequired(Object fulfillmentRequired) {
    
    
    
    this.fulfillmentRequired = fulfillmentRequired;
  }


  public ProductsVariantParameters countryOfManufactureId(Object countryOfManufactureId) {
    
    
    
    
    this.countryOfManufactureId = countryOfManufactureId;
    return this;
  }

   /**
   * Country of manufacture
   * @return countryOfManufactureId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Country of manufacture")

  public Object getCountryOfManufactureId() {
    return countryOfManufactureId;
  }


  public void setCountryOfManufactureId(Object countryOfManufactureId) {
    
    
    
    this.countryOfManufactureId = countryOfManufactureId;
  }


  public ProductsVariantParameters imageIds(Object imageIds) {
    
    
    
    
    this.imageIds = imageIds;
    return this;
  }

   /**
   * Image IDs
   * @return imageIds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Image IDs")

  public Object getImageIds() {
    return imageIds;
  }


  public void setImageIds(Object imageIds) {
    
    
    
    this.imageIds = imageIds;
  }


  public ProductsVariantParameters fulfillmentsLocationIds(Object fulfillmentsLocationIds) {
    
    
    
    
    this.fulfillmentsLocationIds = fulfillmentsLocationIds;
    return this;
  }

   /**
   * Fulfillments Location IDs
   * @return fulfillmentsLocationIds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Fulfillments Location IDs")

  public Object getFulfillmentsLocationIds() {
    return fulfillmentsLocationIds;
  }


  public void setFulfillmentsLocationIds(Object fulfillmentsLocationIds) {
    
    
    
    this.fulfillmentsLocationIds = fulfillmentsLocationIds;
  }


  public ProductsVariantParameters propertiesValues(List<ProductsVariantParametersPropertiesValuesInner> propertiesValues) {
    
    
    
    
    this.propertiesValues = propertiesValues;
    return this;
  }

  public ProductsVariantParameters addPropertiesValuesItem(ProductsVariantParametersPropertiesValuesInner propertiesValuesItem) {
    if (this.propertiesValues == null) {
      this.propertiesValues = new ArrayList<>();
    }
    this.propertiesValues.add(propertiesValuesItem);
    return this;
  }

   /**
   * The property values of the Variant. e.g. &#39;Large&#39;, &#39;Red&#39; etc.
   * @return propertiesValues
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The property values of the Variant. e.g. 'Large', 'Red' etc.")

  public List<ProductsVariantParametersPropertiesValuesInner> getPropertiesValues() {
    return propertiesValues;
  }


  public void setPropertiesValues(List<ProductsVariantParametersPropertiesValuesInner> propertiesValues) {
    
    
    
    this.propertiesValues = propertiesValues;
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
   * @return the ProductsVariantParameters instance itself
   */
  public ProductsVariantParameters putAdditionalProperty(String key, Object value) {
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
    ProductsVariantParameters productsVariantParameters = (ProductsVariantParameters) o;
    return Objects.equals(this.description, productsVariantParameters.description) &&
        Objects.equals(this.name, productsVariantParameters.name) &&
        Objects.equals(this.sku, productsVariantParameters.sku) &&
        Objects.equals(this.productType, productsVariantParameters.productType) &&
        Objects.equals(this.outOfStockSales, productsVariantParameters.outOfStockSales) &&
        Objects.equals(this.weight, productsVariantParameters.weight) &&
        Objects.equals(this.weightUnit, productsVariantParameters.weightUnit) &&
        Objects.equals(this.height, productsVariantParameters.height) &&
        Objects.equals(this.width, productsVariantParameters.width) &&
        Objects.equals(this.length, productsVariantParameters.length) &&
        Objects.equals(this.dimensionsUnit, productsVariantParameters.dimensionsUnit) &&
        Objects.equals(this.taxCategoryId, productsVariantParameters.taxCategoryId) &&
        Objects.equals(this.taxable, productsVariantParameters.taxable) &&
        Objects.equals(this.trackQuantity, productsVariantParameters.trackQuantity) &&
        Objects.equals(this.archived, productsVariantParameters.archived) &&
        Objects.equals(this.visible, productsVariantParameters.visible) &&
        Objects.equals(this.fulfillmentRequired, productsVariantParameters.fulfillmentRequired) &&
        Objects.equals(this.countryOfManufactureId, productsVariantParameters.countryOfManufactureId) &&
        Objects.equals(this.imageIds, productsVariantParameters.imageIds) &&
        Objects.equals(this.fulfillmentsLocationIds, productsVariantParameters.fulfillmentsLocationIds) &&
        Objects.equals(this.propertiesValues, productsVariantParameters.propertiesValues)&&
        Objects.equals(this.additionalProperties, productsVariantParameters.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, name, sku, productType, outOfStockSales, weight, weightUnit, height, width, length, dimensionsUnit, taxCategoryId, taxable, trackQuantity, archived, visible, fulfillmentRequired, countryOfManufactureId, imageIds, fulfillmentsLocationIds, propertiesValues, additionalProperties);
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
    sb.append("class ProductsVariantParameters {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    outOfStockSales: ").append(toIndentedString(outOfStockSales)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
    sb.append("    weightUnit: ").append(toIndentedString(weightUnit)).append("\n");
    sb.append("    height: ").append(toIndentedString(height)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    dimensionsUnit: ").append(toIndentedString(dimensionsUnit)).append("\n");
    sb.append("    taxCategoryId: ").append(toIndentedString(taxCategoryId)).append("\n");
    sb.append("    taxable: ").append(toIndentedString(taxable)).append("\n");
    sb.append("    trackQuantity: ").append(toIndentedString(trackQuantity)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    visible: ").append(toIndentedString(visible)).append("\n");
    sb.append("    fulfillmentRequired: ").append(toIndentedString(fulfillmentRequired)).append("\n");
    sb.append("    countryOfManufactureId: ").append(toIndentedString(countryOfManufactureId)).append("\n");
    sb.append("    imageIds: ").append(toIndentedString(imageIds)).append("\n");
    sb.append("    fulfillmentsLocationIds: ").append(toIndentedString(fulfillmentsLocationIds)).append("\n");
    sb.append("    propertiesValues: ").append(toIndentedString(propertiesValues)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("sku");
    openapiFields.add("product_type");
    openapiFields.add("out_of_stock_sales");
    openapiFields.add("weight");
    openapiFields.add("weight_unit");
    openapiFields.add("height");
    openapiFields.add("width");
    openapiFields.add("length");
    openapiFields.add("dimensions_unit");
    openapiFields.add("tax_category_id");
    openapiFields.add("taxable");
    openapiFields.add("track_quantity");
    openapiFields.add("archived");
    openapiFields.add("visible");
    openapiFields.add("fulfillment_required");
    openapiFields.add("country_of_manufacture_id");
    openapiFields.add("image_ids");
    openapiFields.add("fulfillments_location_ids");
    openapiFields.add("properties_values");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("product_type");
    openapiRequiredFields.add("weight_unit");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to ProductsVariantParameters
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!ProductsVariantParameters.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ProductsVariantParameters is not found in the empty JSON string", ProductsVariantParameters.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ProductsVariantParameters.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("product_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `product_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("product_type").toString()));
      }
      if (!jsonObj.get("weight_unit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `weight_unit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("weight_unit").toString()));
      }
      if (jsonObj.get("properties_values") != null && !jsonObj.get("properties_values").isJsonNull()) {
        JsonArray jsonArraypropertiesValues = jsonObj.getAsJsonArray("properties_values");
        if (jsonArraypropertiesValues != null) {
          // ensure the json data is an array
          if (!jsonObj.get("properties_values").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `properties_values` to be an array in the JSON string but got `%s`", jsonObj.get("properties_values").toString()));
          }

          // validate the optional field `properties_values` (array)
          for (int i = 0; i < jsonArraypropertiesValues.size(); i++) {
            ProductsVariantParametersPropertiesValuesInner.validateJsonObject(jsonArraypropertiesValues.get(i).getAsJsonObject());
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ProductsVariantParameters.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ProductsVariantParameters' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ProductsVariantParameters> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ProductsVariantParameters.class));

       return (TypeAdapter<T>) new TypeAdapter<ProductsVariantParameters>() {
           @Override
           public void write(JsonWriter out, ProductsVariantParameters value) throws IOException {
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
           public ProductsVariantParameters read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             // store additional fields in the deserialized instance
             ProductsVariantParameters instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of ProductsVariantParameters given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ProductsVariantParameters
  * @throws IOException if the JSON string is invalid with respect to ProductsVariantParameters
  */
  public static ProductsVariantParameters fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ProductsVariantParameters.class);
  }

 /**
  * Convert an instance of ProductsVariantParameters to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

