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


package com.konfigthis.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

import okio.ByteString;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/*
 * A JSON utility class
 *
 * NOTE: in the future, this class may be converted to static, which may break
 *       backward-compatibility
 */
public class JSON {
    private static Gson gson;
    private static boolean isLenientOnJson = false;
    private static DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private static SqlDateTypeAdapter sqlDateTypeAdapter = new SqlDateTypeAdapter();
    private static OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new OffsetDateTimeTypeAdapter();
    private static LocalDateTypeAdapter localDateTypeAdapter = new LocalDateTypeAdapter();
    private static ByteArrayAdapter byteArrayAdapter = new ByteArrayAdapter();

    @SuppressWarnings("unchecked")
    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder()
        ;
        GsonBuilder builder = fireBuilder.createGsonBuilder();
        return builder;
    }

    private static String getDiscriminatorValue(JsonElement readElement, String discriminatorField) {
        JsonElement element = readElement.getAsJsonObject().get(discriminatorField);
        if (null == element) {
            throw new IllegalArgumentException("missing discriminator field: <" + discriminatorField + ">");
        }
        return element.getAsString();
    }

    /**
     * Returns the Java class that implements the OpenAPI schema for the specified discriminator value.
     *
     * @param classByDiscriminatorValue The map of discriminator values to Java classes.
     * @param discriminatorValue The value of the OpenAPI discriminator in the input data.
     * @return The Java class that implements the OpenAPI schema
     */
    private static Class getClassByDiscriminator(Map classByDiscriminatorValue, String discriminatorValue) {
        Class clazz = (Class) classByDiscriminatorValue.get(discriminatorValue);
        if (null == clazz) {
            throw new IllegalArgumentException("cannot determine model class of name: <" + discriminatorValue + ">");
        }
        return clazz;
    }

    {
        GsonBuilder gsonBuilder = createGson();
        gsonBuilder.registerTypeAdapter(Date.class, dateTypeAdapter);
        gsonBuilder.registerTypeAdapter(java.sql.Date.class, sqlDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter);
        gsonBuilder.registerTypeAdapter(LocalDate.class, localDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(byte[].class, byteArrayAdapter);

        /**
         * For the "type: number" schema we accept both Double and Integer
         * In the case that we pass "1.0" or "1" we serialize the JsonPrimitive
         * as the "1" literal. This is useful when the server expects an integer.
         */
        gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

            @Override
            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == src.longValue())
                    return new JsonPrimitive(src.longValue());
                return new JsonPrimitive(src);
            }
        });
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactCreateNewContactRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactListForWorkspaceFilterParameter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactTagAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactUpdateContactByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactUpsertRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsAppliedTagAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsAppliedTagCreateAppliedTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsAppliedTagParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsPropertyTagIdsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsTagAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsTagCreateNewTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsTagParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContactsTagUpdateSpecificTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CourseAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesEnrollmentAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesEnrollmentCreateNewEnrollmentRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesEnrollmentParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesEnrollmentUpdateSpecificEnrollmentRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesLessonAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesLessonParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesLessonUpdateLessonByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesSectionAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesSectionParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CoursesSectionUpdateSectionByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormCreateNewFormRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormUpdateFormByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldAddNewFieldRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldSetAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldSetCreateNewFieldSetRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldSetParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldSetUpdateFieldSetByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldUpdateFieldByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldsOptionAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldsOptionCreateNewFieldOptionRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldsOptionParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsFieldsOptionUpdateFieldOptionRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionCreateNewSubmissionRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionUpdateSubmissionRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionsAnswerAddNewAnswerRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionsAnswerAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionsAnswerParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FormsSubmissionsAnswerUpdateAnswerRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentCreateRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentParametersIncludedOrdersInvoicesLineItemsAttributesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentUpdateByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentsLocationAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentsLocationCreateNewLocationRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentsLocationParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentsLocationParametersAddress.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FulfillmentsLocationUpdateByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ImageAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ImageCreateRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ImageParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ImageUpdateByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IncludedOrdersInvoicesLineItems.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IncludedOrdersInvoicesLineItems1.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IncludedOrdersLineItems.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.LineItemsProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.LineItemsProperty1.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.LocationsProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderContactGroupAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderListOrdersFilterParameter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderPageAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderSegmentAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrderUpdateSpecificRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersAppliedTagAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersAppliedTagCreateAppliedTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersAppliedTagParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersInvoiceAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersInvoicesLineItemAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersInvoicesRestockGetRestockResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersLineItemAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersTagAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersTagCreateNewTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersTagParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersTagUpdateSpecificOrderTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OrdersTransactionAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PagesProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductAddNewToWorkspaceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductParametersVariantPropertiesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductPriceProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductUpdateForWorkspaceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductVariantProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsPriceAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsPriceCreateVariantPriceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsPriceParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsPriceUpdateSinglePriceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsTagAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsTagCreateNewTagRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsTagParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsTagUpdateTagByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsVariantAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsVariantCreateNewVariantRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsVariantParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsVariantParametersPropertiesValuesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProductsVariantUpdateSingleRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Restocks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingLocationGroupAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingPackageAddToWorkspaceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingPackageAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingPackageParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingPackageUpdateForWorkspaceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingProfileAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingProfileCreateNewRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingProfileParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingProfileUpdateForWorkspaceRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRateAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRateCreateRateForZoneRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRateParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRateUpdateRateForZoneRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRatesNameAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRatesNameCreateNewRateNameRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRatesNameParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingRatesNameUpdateNameRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingZoneAddNewZoneRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingZoneAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingZoneParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ShippingZoneUpdateZoneByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamUpdateTeamByIdRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TranslationMissingEnProductsPropertiesTitle.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TranslationMissingEnProductsPropertiesValuesTitle.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserUpdateSingleUserRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhooksOutgoingEndpointAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhooksOutgoingEndpointCreateNewRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhooksOutgoingEndpointParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhooksOutgoingEndpointUpdateEndpointRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhooksOutgoingEventAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhooksOutgoingEventAttributesData.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkspaceAddNewRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkspaceAttributes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkspaceParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkspaceUpdateRequest.CustomTypeAdapterFactory());
        gsonBuilder.disableHtmlEscaping();
        gson = gsonBuilder.create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     */
    public static void setGson(Gson gson) {
        JSON.gson = gson;
    }

    public static void setLenientOnJson(boolean lenientOnJson) {
        isLenientOnJson = lenientOnJson;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public static String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            // Fallback processing when failed to parse JSON form response body:
            // return the response body string directly for the String return type;
            if (returnType.equals(String.class)) {
                return (T) body;
            } else {
                throw (e);
            }
        }
    }

    /**
     * Gson TypeAdapter for Byte Array type
     */
    public static class ByteArrayAdapter extends TypeAdapter<byte[]> {

        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(ByteString.of(value).base64());
            }
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String bytesAsBase64 = in.nextString();
                    ByteString byteString = ByteString.decodeBase64(bytesAsBase64);
                    return byteString.toByteArray();
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    if (date.endsWith("+0000")) {
                        date = date.substring(0, date.length()-5) + "Z";
                    }
                    return OffsetDateTime.parse(date, formatter);
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateTypeAdapter() {
            this(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public LocalDateTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDate.parse(date, formatter);
            }
        }
    }

    public static void setOffsetDateTimeFormat(DateTimeFormatter dateFormat) {
        offsetDateTimeTypeAdapter.setFormat(dateFormat);
    }

    public static void setLocalDateFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setFormat(dateFormat);
    }

    /**
     * Gson TypeAdapter for java.sql.Date type
     * If the dateFormat is null, a simple "yyyy-MM-dd" format will be used
     * (more efficient than SimpleDateFormat).
     */
    public static class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {

        private DateFormat dateFormat;

        public SqlDateTypeAdapter() {}

        public SqlDateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, java.sql.Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = date.toString();
                }
                out.value(value);
            }
        }

        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    try {
                        if (dateFormat != null) {
                            return new java.sql.Date(dateFormat.parse(date).getTime());
                        }
                        return new java.sql.Date(ISO8601Utils.parse(date, new ParsePosition(0)).getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
            }
        }
    }

    /**
     * Gson TypeAdapter for java.util.Date type
     * If the dateFormat is null, ISO8601Utils will be used.
     */
    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private DateFormat dateFormat;

        public DateTypeAdapter() {}

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = ISO8601Utils.format(date, true);
                }
                out.value(value);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                switch (in.peek()) {
                    case NULL:
                        in.nextNull();
                        return null;
                    default:
                        String date = in.nextString();
                        try {
                            if (dateFormat != null) {
                                return dateFormat.parse(date);
                            }
                            return ISO8601Utils.parse(date, new ParsePosition(0));
                        } catch (ParseException e) {
                            throw new JsonParseException(e);
                        }
                }
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static void setDateFormat(DateFormat dateFormat) {
        dateTypeAdapter.setFormat(dateFormat);
    }

    public static void setSqlDateFormat(DateFormat dateFormat) {
        sqlDateTypeAdapter.setFormat(dateFormat);
    }
}
