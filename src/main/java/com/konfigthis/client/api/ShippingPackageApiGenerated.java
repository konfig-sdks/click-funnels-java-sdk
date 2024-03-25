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


package com.konfigthis.client.api;

import com.konfigthis.client.ApiCallback;
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.Pair;
import com.konfigthis.client.ProgressRequestBody;
import com.konfigthis.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.konfigthis.client.model.ShippingPackageAddToWorkspaceRequest;
import com.konfigthis.client.model.ShippingPackageAttributes;
import com.konfigthis.client.model.ShippingPackageUpdateForWorkspaceRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class ShippingPackageApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public ShippingPackageApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public ShippingPackageApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    private okhttp3.Call addToWorkspaceCall(Integer workspaceId, ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = shippingPackageAddToWorkspaceRequest;

        // create path and map variables
        String localVarPath = "/workspaces/{workspace_id}/shipping/packages"
            .replace("{" + "workspace_id" + "}", localVarApiClient.escapeString(workspaceId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "BearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call addToWorkspaceValidateBeforeCall(Integer workspaceId, ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'workspaceId' is set
        if (workspaceId == null) {
            throw new ApiException("Missing the required parameter 'workspaceId' when calling addToWorkspace(Async)");
        }

        // verify the required parameter 'shippingPackageAddToWorkspaceRequest' is set
        if (shippingPackageAddToWorkspaceRequest == null) {
            throw new ApiException("Missing the required parameter 'shippingPackageAddToWorkspaceRequest' when calling addToWorkspace(Async)");
        }

        return addToWorkspaceCall(workspaceId, shippingPackageAddToWorkspaceRequest, _callback);

    }


    private ApiResponse<ShippingPackageAttributes> addToWorkspaceWithHttpInfo(Integer workspaceId, ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest) throws ApiException {
        okhttp3.Call localVarCall = addToWorkspaceValidateBeforeCall(workspaceId, shippingPackageAddToWorkspaceRequest, null);
        Type localVarReturnType = new TypeToken<ShippingPackageAttributes>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call addToWorkspaceAsync(Integer workspaceId, ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest, final ApiCallback<ShippingPackageAttributes> _callback) throws ApiException {

        okhttp3.Call localVarCall = addToWorkspaceValidateBeforeCall(workspaceId, shippingPackageAddToWorkspaceRequest, _callback);
        Type localVarReturnType = new TypeToken<ShippingPackageAttributes>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class AddToWorkspaceRequestBuilder {
        private final Integer workspaceId;
        private ShippingPackageParameters shippingPackage;

        private AddToWorkspaceRequestBuilder(Integer workspaceId) {
            this.workspaceId = workspaceId;
        }

        /**
         * Set shippingPackage
         * @param shippingPackage  (optional)
         * @return AddToWorkspaceRequestBuilder
         */
        public AddToWorkspaceRequestBuilder shippingPackage(ShippingPackageParameters shippingPackage) {
            this.shippingPackage = shippingPackage;
            return this;
        }
        
        /**
         * Build call for addToWorkspace
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest = buildBodyParams();
            return addToWorkspaceCall(workspaceId, shippingPackageAddToWorkspaceRequest, _callback);
        }

        private ShippingPackageAddToWorkspaceRequest buildBodyParams() {
            ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest = new ShippingPackageAddToWorkspaceRequest();
            shippingPackageAddToWorkspaceRequest.shippingPackage(this.shippingPackage);
            return shippingPackageAddToWorkspaceRequest;
        }

        /**
         * Execute addToWorkspace request
         * @return ShippingPackageAttributes
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public ShippingPackageAttributes execute() throws ApiException {
            ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest = buildBodyParams();
            ApiResponse<ShippingPackageAttributes> localVarResp = addToWorkspaceWithHttpInfo(workspaceId, shippingPackageAddToWorkspaceRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute addToWorkspace request with HTTP info returned
         * @return ApiResponse&lt;ShippingPackageAttributes&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ShippingPackageAttributes> executeWithHttpInfo() throws ApiException {
            ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest = buildBodyParams();
            return addToWorkspaceWithHttpInfo(workspaceId, shippingPackageAddToWorkspaceRequest);
        }

        /**
         * Execute addToWorkspace request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ShippingPackageAttributes> _callback) throws ApiException {
            ShippingPackageAddToWorkspaceRequest shippingPackageAddToWorkspaceRequest = buildBodyParams();
            return addToWorkspaceAsync(workspaceId, shippingPackageAddToWorkspaceRequest, _callback);
        }
    }

    /**
     * Create Package
     * Add a new package to a workspace
     * @param workspaceId  (required)
     * @param shippingPackageAddToWorkspaceRequest Information about a new Package (required)
     * @return AddToWorkspaceRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
     </table>
     */
    public AddToWorkspaceRequestBuilder addToWorkspace(Integer workspaceId) throws IllegalArgumentException {
        if (workspaceId == null) throw new IllegalArgumentException("\"workspaceId\" is required but got null");
        return new AddToWorkspaceRequestBuilder(workspaceId);
    }
    private okhttp3.Call getForWorkspaceCall(String id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/shipping/packages/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "BearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getForWorkspaceValidateBeforeCall(String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getForWorkspace(Async)");
        }

        return getForWorkspaceCall(id, _callback);

    }


    private ApiResponse<ShippingPackageAttributes> getForWorkspaceWithHttpInfo(String id) throws ApiException {
        okhttp3.Call localVarCall = getForWorkspaceValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<ShippingPackageAttributes>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getForWorkspaceAsync(String id, final ApiCallback<ShippingPackageAttributes> _callback) throws ApiException {

        okhttp3.Call localVarCall = getForWorkspaceValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<ShippingPackageAttributes>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetForWorkspaceRequestBuilder {
        private final String id;

        private GetForWorkspaceRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Build call for getForWorkspace
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getForWorkspaceCall(id, _callback);
        }


        /**
         * Execute getForWorkspace request
         * @return ShippingPackageAttributes
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public ShippingPackageAttributes execute() throws ApiException {
            ApiResponse<ShippingPackageAttributes> localVarResp = getForWorkspaceWithHttpInfo(id);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getForWorkspace request with HTTP info returned
         * @return ApiResponse&lt;ShippingPackageAttributes&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ShippingPackageAttributes> executeWithHttpInfo() throws ApiException {
            return getForWorkspaceWithHttpInfo(id);
        }

        /**
         * Execute getForWorkspace request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ShippingPackageAttributes> _callback) throws ApiException {
            return getForWorkspaceAsync(id, _callback);
        }
    }

    /**
     * Fetch Package
     * Retrieve a package for a workspace
     * @param id  (required)
     * @return GetForWorkspaceRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public GetForWorkspaceRequestBuilder getForWorkspace(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new GetForWorkspaceRequestBuilder(id);
    }
    private okhttp3.Call listForWorkspaceCall(Integer workspaceId, String after, String sortOrder, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workspaces/{workspace_id}/shipping/packages"
            .replace("{" + "workspace_id" + "}", localVarApiClient.escapeString(workspaceId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (after != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("after", after));
        }

        if (sortOrder != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort_order", sortOrder));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "BearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listForWorkspaceValidateBeforeCall(Integer workspaceId, String after, String sortOrder, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'workspaceId' is set
        if (workspaceId == null) {
            throw new ApiException("Missing the required parameter 'workspaceId' when calling listForWorkspace(Async)");
        }

        return listForWorkspaceCall(workspaceId, after, sortOrder, _callback);

    }


    private ApiResponse<List<ShippingPackageAttributes>> listForWorkspaceWithHttpInfo(Integer workspaceId, String after, String sortOrder) throws ApiException {
        okhttp3.Call localVarCall = listForWorkspaceValidateBeforeCall(workspaceId, after, sortOrder, null);
        Type localVarReturnType = new TypeToken<List<ShippingPackageAttributes>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listForWorkspaceAsync(Integer workspaceId, String after, String sortOrder, final ApiCallback<List<ShippingPackageAttributes>> _callback) throws ApiException {

        okhttp3.Call localVarCall = listForWorkspaceValidateBeforeCall(workspaceId, after, sortOrder, _callback);
        Type localVarReturnType = new TypeToken<List<ShippingPackageAttributes>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListForWorkspaceRequestBuilder {
        private final Integer workspaceId;
        private String after;
        private String sortOrder;

        private ListForWorkspaceRequestBuilder(Integer workspaceId) {
            this.workspaceId = workspaceId;
        }

        /**
         * Set after
         * @param after ID of item after which the collection should be returned (optional)
         * @return ListForWorkspaceRequestBuilder
         */
        public ListForWorkspaceRequestBuilder after(String after) {
            this.after = after;
            return this;
        }
        
        /**
         * Set sortOrder
         * @param sortOrder Sort order of a list response. Use &#39;desc&#39; to reverse the default &#39;asc&#39; (ascending) sort order. (optional)
         * @return ListForWorkspaceRequestBuilder
         */
        public ListForWorkspaceRequestBuilder sortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }
        
        /**
         * Build call for listForWorkspace
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  * Pagination-Next -  <br>  * Link -  <br>  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listForWorkspaceCall(workspaceId, after, sortOrder, _callback);
        }


        /**
         * Execute listForWorkspace request
         * @return List&lt;ShippingPackageAttributes&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  * Pagination-Next -  <br>  * Link -  <br>  </td></tr>
         </table>
         */
        public List<ShippingPackageAttributes> execute() throws ApiException {
            ApiResponse<List<ShippingPackageAttributes>> localVarResp = listForWorkspaceWithHttpInfo(workspaceId, after, sortOrder);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listForWorkspace request with HTTP info returned
         * @return ApiResponse&lt;List&lt;ShippingPackageAttributes&gt;&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  * Pagination-Next -  <br>  * Link -  <br>  </td></tr>
         </table>
         */
        public ApiResponse<List<ShippingPackageAttributes>> executeWithHttpInfo() throws ApiException {
            return listForWorkspaceWithHttpInfo(workspaceId, after, sortOrder);
        }

        /**
         * Execute listForWorkspace request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  * Pagination-Next -  <br>  * Link -  <br>  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<List<ShippingPackageAttributes>> _callback) throws ApiException {
            return listForWorkspaceAsync(workspaceId, after, sortOrder, _callback);
        }
    }

    /**
     * List Packages
     * List packages for a workspace
     * @param workspaceId  (required)
     * @return ListForWorkspaceRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  * Pagination-Next -  <br>  * Link -  <br>  </td></tr>
     </table>
     */
    public ListForWorkspaceRequestBuilder listForWorkspace(Integer workspaceId) throws IllegalArgumentException {
        if (workspaceId == null) throw new IllegalArgumentException("\"workspaceId\" is required but got null");
        return new ListForWorkspaceRequestBuilder(workspaceId);
    }
    private okhttp3.Call removeByIdCall(String id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/shipping/packages/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "BearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call removeByIdValidateBeforeCall(String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling removeById(Async)");
        }

        return removeByIdCall(id, _callback);

    }


    private ApiResponse<Void> removeByIdWithHttpInfo(String id) throws ApiException {
        okhttp3.Call localVarCall = removeByIdValidateBeforeCall(id, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call removeByIdAsync(String id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeByIdValidateBeforeCall(id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class RemoveByIdRequestBuilder {
        private final String id;

        private RemoveByIdRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Build call for removeById
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return removeByIdCall(id, _callback);
        }


        /**
         * Execute removeById request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            removeByIdWithHttpInfo(id);
        }

        /**
         * Execute removeById request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return removeByIdWithHttpInfo(id);
        }

        /**
         * Execute removeById request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            return removeByIdAsync(id, _callback);
        }
    }

    /**
     * Remove Package
     * Delete a package for a workspace
     * @param id  (required)
     * @return RemoveByIdRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
     </table>
     */
    public RemoveByIdRequestBuilder removeById(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new RemoveByIdRequestBuilder(id);
    }
    private okhttp3.Call updateForWorkspaceCall(String id, ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = shippingPackageUpdateForWorkspaceRequest;

        // create path and map variables
        String localVarPath = "/shipping/packages/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "BearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateForWorkspaceValidateBeforeCall(String id, ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling updateForWorkspace(Async)");
        }

        // verify the required parameter 'shippingPackageUpdateForWorkspaceRequest' is set
        if (shippingPackageUpdateForWorkspaceRequest == null) {
            throw new ApiException("Missing the required parameter 'shippingPackageUpdateForWorkspaceRequest' when calling updateForWorkspace(Async)");
        }

        return updateForWorkspaceCall(id, shippingPackageUpdateForWorkspaceRequest, _callback);

    }


    private ApiResponse<ShippingPackageAttributes> updateForWorkspaceWithHttpInfo(String id, ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest) throws ApiException {
        okhttp3.Call localVarCall = updateForWorkspaceValidateBeforeCall(id, shippingPackageUpdateForWorkspaceRequest, null);
        Type localVarReturnType = new TypeToken<ShippingPackageAttributes>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateForWorkspaceAsync(String id, ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest, final ApiCallback<ShippingPackageAttributes> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateForWorkspaceValidateBeforeCall(id, shippingPackageUpdateForWorkspaceRequest, _callback);
        Type localVarReturnType = new TypeToken<ShippingPackageAttributes>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateForWorkspaceRequestBuilder {
        private final String id;
        private ShippingPackageParameters shippingPackage;

        private UpdateForWorkspaceRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Set shippingPackage
         * @param shippingPackage  (optional)
         * @return UpdateForWorkspaceRequestBuilder
         */
        public UpdateForWorkspaceRequestBuilder shippingPackage(ShippingPackageParameters shippingPackage) {
            this.shippingPackage = shippingPackage;
            return this;
        }
        
        /**
         * Build call for updateForWorkspace
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest = buildBodyParams();
            return updateForWorkspaceCall(id, shippingPackageUpdateForWorkspaceRequest, _callback);
        }

        private ShippingPackageUpdateForWorkspaceRequest buildBodyParams() {
            ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest = new ShippingPackageUpdateForWorkspaceRequest();
            shippingPackageUpdateForWorkspaceRequest.shippingPackage(this.shippingPackage);
            return shippingPackageUpdateForWorkspaceRequest;
        }

        /**
         * Execute updateForWorkspace request
         * @return ShippingPackageAttributes
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public ShippingPackageAttributes execute() throws ApiException {
            ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest = buildBodyParams();
            ApiResponse<ShippingPackageAttributes> localVarResp = updateForWorkspaceWithHttpInfo(id, shippingPackageUpdateForWorkspaceRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateForWorkspace request with HTTP info returned
         * @return ApiResponse&lt;ShippingPackageAttributes&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ShippingPackageAttributes> executeWithHttpInfo() throws ApiException {
            ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest = buildBodyParams();
            return updateForWorkspaceWithHttpInfo(id, shippingPackageUpdateForWorkspaceRequest);
        }

        /**
         * Execute updateForWorkspace request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ShippingPackageAttributes> _callback) throws ApiException {
            ShippingPackageUpdateForWorkspaceRequest shippingPackageUpdateForWorkspaceRequest = buildBodyParams();
            return updateForWorkspaceAsync(id, shippingPackageUpdateForWorkspaceRequest, _callback);
        }
    }

    /**
     * Update Package
     * Update a package for a workspace
     * @param id  (required)
     * @param shippingPackageUpdateForWorkspaceRequest Information about updated fields in Package (required)
     * @return UpdateForWorkspaceRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public UpdateForWorkspaceRequestBuilder updateForWorkspace(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new UpdateForWorkspaceRequestBuilder(id);
    }
}
