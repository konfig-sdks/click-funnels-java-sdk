<div align="left">

[![Visit Clickfunnels](./header.png)](https://www.clickfunnels.com&#x2F;)

# [Clickfunnels](https://www.clickfunnels.com&#x2F;)

# Introduction

The ClickFunnels v2 API lets you:

- import data from other apps and sources into ClickFunnels and export data that you need somewhere else
- extend the ClickFunnels platform to your own needs and embed it in your own applications
- act on behalf of other ClickFunnels users via OAuth to offer extended services to other fellow ClickFunnels entrepreneurs

We are starting with exposing a given set of resources but the goal is to converge in terms of
functionality with what the actual app is offering and also offering functionality on top.

For any feedback, please drop us a line at:

- https://feedback.myclickfunnels.com/feature-requests?category=api

For issues and support you can currently go here:

- https://help.clickfunnels.com/hc/en-us

# Authentication

Making your first request is easiest with a Bearer token:

```shell
$ curl 'https://myteam.myclickfunnels.com/api/v2/teams' \
--header 'Authorization: Bearer AVJrj0ZMJ-xoraUk1xxVM6UuL9KXmsWmnJvvSosUO6X'
[{"id":3,"name":"My Team", # ... more output...}]
```

How to get your API key step by step:

https://developers.myclickfunnels.com/docs/getting-started

# Rate limiting

The rate limit is currently set per IP address.

The actual rate limit and the approach on how this is handled is subject to change in future
releases. Please let us know if you have special request limit needs.

# Pagination and Ordering

In order to paginate through a large list response, you can use our cursor-based pagination using
the `id` field of a given object in the list.

There is a limit of 20 objects per list response ordered ascending by ID. So, you can get to items
after the last one in the list, by taking the last item's ID and submitting it in a list request
as the value of an `after` URL parameter. For example:

```shell
# The first 20 contacts are returned without any pagination nor ordering params:
$ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts' --header 'Authorization: Bearer ...'
[{"id": 1, "email_address": "first@contact.com" ...}, {"id": 4, ...} ... {"id": 55, "email_address": "last@contact.com", ...}]

$ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?after=55' --header 'Authorization: Bearer ...'
[{"id": 56, ...}] # There is one more record after ID 55.
```

The `after` param always acts as if you are "turning the next page". So if you order in a descending
order, you will also use `after` to get to the next records:

```shell
$ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?sort_order=desc' --header 'Authorization: Bearer ...'
[{"id": 56, ...},  {"id": 55, ...}, {"id": 4, ...}] # All contacts in descending order.

$ curl 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?sort_order=desc&after=4' --header 'Authorization: Bearer ...'
[{"id": 1, ...}] # There is one more contact on the next page after ID 55.
```

You can also use the `Pagination-Next` header to get the last ID value directly:

```http request
# Example header.
Pagination-Next: 55
```

And you can use the `Link` header to get the next page directly without needing to calculate it
yourself:

```http request
# Example header.
Link: <https://localteam.myclickfunnels.com/api/v2/workspaces/3/contacts?after=55>; rel="next"
```

# Filtering

**Current filters**

If filtering is available for a specific endpoint, 'filter' will be listed as one of the options in the query parameters section of the Request area. Attributes by which you can filter will be listed as well.

**How it works**

There is a filter mechanism that adheres to some simple conventions. The filters provided on
list endpoints, like `filter[email_address]` and `filter[id]` on the `Contacts` list endpoint, need
to be "simple" and "fast". These filters are supposed to be easy to use and allow you to filter by
one or more concrete values.

Here's an example of how you could use the filter to find a contact with a certain email address:

```shell
$ curl -g 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?filter[email_address]=you@cf.com' --header 'Authorization: Bearer ...'
[{"email_address": "you@cf.com",...}]
```

You can also filter by multiple values:

```shell
$ curl -g 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?filter[email_address]=you@cf.com,u2@cf.com' --header 'Authorization: Bearer ...'
[{"email_address": "you@cf.com",...}, {"email_address": "u2@cf.com",...}]
```

You can also filter by multiple attributes. Similar to filters that you might be familiar with when
using GitHub (e.g.: filtering PRs by closed and assignee), those filters are `AND` filters, which
give you the intersection of multiple records:

```shell
# If you@cf.com comes with an ID of 1, you will only see this record for this API call:
$ curl -g 'https://myteam.myclickfunnels.com/api/v2/workspaces/3/contacts?filter[email_address]=you@cf.com,u2@cf.com&filter[id]=1' --header 'Authorization: Bearer ...'
[{"email_address": "you@cf.com",...}] 
# u2@cf.com is not included because it has a different ID that is not included in the filter.
```

> Please let us know your use case if you need more filter or complex search capabilities, we are 
> actively improving these areas: https://feedback.myclickfunnels.com/feature-requests?category=api

# Webhooks

ClickFunnels webhooks allow you to react to many events in the ClickFunnels app on your own server, 
Zapier and other similar tools.

You need to configure one or more endpoints within the ClickFunnels API by using the [Webhooks::Outgoing::Endpoints](https://apidocs.myclickfunnels.com/tag/Webhooks::Outgoing::Endpoint) 
endpoint with the `event_type_ids` that you want to listen to (see below for all types).

Once configured, you will receive POST requrests from us to the configured endpoint URL with the
[Webhooks::Outgoing::Event](https://apidocs.myclickfunnels.com/tag/Webhooks::Outgoing::Event#operation/getWebhooksOutgoingEvents) 
payload, that will contain the subject payload in the `data` property. Like here for the
`contact.identified` webhook in V2 version:

```json
{
  "id": null,
  "public_id": "YVIOwX",
  "workspace_id": 32,
  "uuid": "94856650751bb2c141fc38436fd699cb",
  "event_type_id": "contact.identified",
  "subject_id": 100,
  "subject_type": "Contact",
  "data": {
    "id": 12,
    "public_id": "fdPJAZ",
    "workspace_id": 32,
    "anonymous": null,
    "email_address": "joe.doe@example.com",
    "first_name": "Joe",
    "last_name": "Doe",
    "phone_number": "1-241-822-5555",
    "time_zone": "Pacific Time (US & Canada)",
    "uuid": "26281ba2-7d3b-524d-8ea3-b01ff8414120",
    "unsubscribed_at": null,
    "last_notification_email_sent_at": null,
    "fb_url": "https://www.facebook.com/example",
    "twitter_url": "https://twitter.com/example",
    "instagram_url": "https://instagram.com/example",
    "linkedin_url": "https://www.linkedin.com/in/example",
    "website_url": "https://example.com",
    "created_at": "2023-12-31T18:57:40.871Z",
    "updated_at": "2023-12-31T18:57:40.872Z",
    "tags": [
      {
        "id": 20,
        "public_id": "bRkQrc",
        "name": "Example Tag",
        "color": "#59b0a8"
      }
    ]
  },
  "created_at": "2023-12-31T18:57:41.872Z"
}
```

The content of the `data` property will vary depending on the event type that you are receiving.

Event types are structured like this: `subject.action`. So, for a `contact.identified` webhook, your
`data` payload will contain data that you can source from [the contact response schema/example in the
documentation](https://apidocs.myclickfunnels.com/tag/Contact#operation/getContacts). Similarly, for
webhooks like `order.created` and `one-time-order.identified`, you will find the documentation in
[the Order resource description](https://apidocs.myclickfunnels.com/tag/Order#operation/getOrders).

**Contact webhooks**

Are delivered with [the Contact data payload](https://apidocs.myclickfunnels.com/tag/Contact#operation/getContacts).

| <div style="width:375px;">Event type</div>| Versions available | Description                                                            | 
|--------------------------------------------------|--------------------|------------------------------------------------------------------------|
| ***Contact***                                    |                    |                                                                        |
| `contact.created`                                | V1, V2             | Sent when a Contact is created                                         |
| `contact.updated`                                | V1, V2             | Sent when a Contact is updated                                         |
| `contact.deleted`                                | V1, V2             | Sent when a Contact is deleted                                         |
| `contact.identified`                             | V1, V2             | Sent when a Contact is identified by email address and/or phone number |
| `contact.unsubscribed`                           | V1, V2             | Sent when a Contact unsubscribes from getting communications from the ClickFunnels workspace                         |

**Contact::AppliedTag webhooks**

Are delivered with [the Contact::AppliedTag data payload](https://apidocs.myclickfunnels.com/tag/Contacts::AppliedTag#operation/getContactsAppliedTags)

| <div style="width:375px;">Event type</div>| Versions available | Description                                                            |
|--------------------------------------------------|--------------------|------------------------------------------------------------------------|
| ***Contacts::AppliedTag***                       |                    |                                                                        |
| `contact/applied_tag.created`                    | V2                 | Sent when a Contacts::AppliedTag is created                            |
| `contact/applied_tag.deleted`                    | V2                 | Sent when a Contacts::AppliedTag is deleted

**Courses webhooks**

Payloads correspond to the respective API resources:

- [Course](https://apidocs.myclickfunnels.com/tag/Course#operation/getCourses)
- [Courses::Enrollment](https://apidocs.myclickfunnels.com/tag/Courses::Enrollment#operation/getCoursesEnrollments)
- [Courses::Section](https://apidocs.myclickfunnels.com/tag/Courses::Section#operation/getCoursesSections)
- [Courses::Lesson](https://apidocs.myclickfunnels.com/tag/Courses::Lesson#operation/getCoursesLessons)

| <div style="width:375px;">Event type</div>| Versions available | Description                                                            | 
|--------------------------------------------------|--------------------|------------------------------------------------------------------------|
| ***Course***                                     |                    |                                                                        |
| `course.created`                                 | V2             | Sent when a Course is created                                          |
| `course.updated`                                 | V2             | Sent when a Course is updated                                          |
| `course.deleted`                                 | V2             | Sent when a Course is deleted                                          |
| `course.published`                               | V2                 | Sent when a Course has been published                                  |
| ***Courses::Enrollment***                        |                    |                                                                        |
| `courses/enrollment.created`                     | V2             | Sent when a Course::Enrollment is created                              |
| `courses/enrollment.updated`                     | V2             | Sent when a Course::Enrollment is updated                              |
| `courses/enrollment.deleted`                     | V2             | Sent when a Course::Enrollment is deleted                              |
| `courses/enrollment.suspended`                   | V2                 | Sent when a Course::Enrollment has been suspended                      |
| `courses/enrollment.course_completed`                   | V2                 | Sent when a Course::Enrollment completes a course                      |
| 
***Courses::Section***                           |                    |                                                                        |
| `courses/section.created`                        | V2             | Sent when a Courses::Section is created                                |
| `courses/section.updated`                        | V2             | Sent when a Courses::Section is updated                                |
| `courses/section.deleted`                        | V2             | Sent when a Courses::Section is deleted                                |
| `courses/section.published`                       | V2                 | Sent when a Courses::Lesson has been published                         |
|                      |
***Courses::Lesson***                            |                    |                                                                        |
| `courses/lesson.created`                         | V2             | Sent when a Courses::Lesson is created                                 |
| `courses/lesson.updated`                         | V2             | Sent when a Courses::Lesson is updated                                 |
| `courses/lesson.deleted`                         | V2             | Sent when a Courses::Lesson is deleted                                 |
| `courses/lesson.published`                       | V2                 | Sent when a Courses::Lesson has been published                         |
|                      |

**Form submission webhooks**

Currently only available in V1 with the following JSON payload sample:

```json
{
  "data": {
    "id": "4892034",
    "type": "form_submission",
    "attributes": {
      "id": 9874322,
      "data": {
        "action": "submit",
        "contact": {
          "email": "joe.doe@example.com",
          "aff_sub": "43242122e8c15480e9117143ce806d111"
        },
        "controller": "user_pages/pages",
        "redirect_to": "https://www.example.com/thank-you-newsletter"
      },
      "page_id": 2342324,
      "contact_id": 234424,
      "created_at": "2023-11-14T23:25:54.070Z",
      "updated_at": "2023-11-14T23:25:54.134Z",
      "workspace_id": 11
    }
  },
  "event_id": "bb50ab45-3da8-4532-9d7e-1c85d159ee71",
  "event_type": "form_submission.created",
  "subject_id": 9894793,
  "subject_type": "FormSubmission"
}
```

| <div style="width:375px;">Event type</div>| Versions available | Description                             | 
|--------------------------------------------------|--------------------|-----------------------------------------|
| ***Form::Submission***                           |                    |                                         |
| `form/submission.created`                        | V1                 | Sent when a Form::Submission is created |

**Order webhooks**

Subscriptions and orders are all of type "Order" and their payload will be as in the [Order resources
response payload](https://apidocs.myclickfunnels.com/tag/Order#operation/getOrders).

| <div style="width:375px;">Event type</div> | Versions available | Description                                                                                               | 
|--------------------------------------------|--------------------|-----------------------------------------------------------------------------------------------------------|
| ***Order***                                |                    |                                                                                                           |
| `order.created`                            | V1, V2             | Sent when an Order has been created                                                                       |
| `order.updated`                            | V1, V2             | Sent when an Order has been updated                                                                       |
| `order.deleted`                            | V1, V2             | Sent when an Order has been deleted                                                                       |
| `order.completed`                          | V1, V2             | Sent when a one-time order was paid or a subscription order's service period has concluded                |
| ***One-Time Order***                       |                    |                                                                                                           |
| `one-time-order.completed`                 | V1, V2             | Sent when an Order of `order_type: "one-time-order"` has been completed                                   |
| `one_time_order.refunded`                  | V1, V2             | Sent when an Order of `order_type: "one-time-order"` refund has been issued                               |
| ***Subscription***                         |                    |                                                                                                           |
| `subscription.canceled`                    | V1, V2             | Sent when an Order of `order_type: "subscription"` has been canceled                                      |
| `subscription.reactivated`                 | V1, V2             | Sent when an Order of `order_type: "subscription"` that was canceled was reactivated                      |
| `subscription.downgraded`                  | V1, V2             | Sent when an Order of `order_type: "subscription"` is changed to a product of smaller value               |
| `subscription.upgraded`                    | V1, V2             | Sent when an Order of `order_type: "subscription"` is changed to a product of higher value                |
| `subscription.churned`                     | V1, V2             | Sent when an Order of `order_type: "subscription"` has been churned                                       |
| `subscription.modified`                    | V1, V2             | Sent when an Order of `order_type: "subscription"` has been modified                                      |
| `subscription.activated`                   | V1, V2             | Sent when an Order of `order_type: "subscription"` has been activated                                     |
| `subscription.completed`                   | V1, V2             | Sent when an Order of `order_type: "subscription"` has been completed                                     |
| `subscription.first_payment_received`      | V1, V2             | Sent when an Order of `order_type: "subscription"` received first non-setup payment for subscription item |

**Orders::Transaction Webhooks**

Orders transactions are currently not part of our V2 API, but you can refer to this sample V2 webhook data payload: 

```json
{
  "id": 1821233,
  "arn": null,
  "amount": "200.00",
  "reason": null,
  "result": "approved",
  "status": "completed",
  "currency": "USD",
  "order_id": 110030,
  "is_rebill": false,
  "public_id": "asLOAY",
  "created_at": "2024-01-30T06:25:06.754Z",
  "updated_at": "2024-01-30T06:25:06.754Z",
  "external_id": "txn_01HNCGNQE2C234PCFNERD2AVFZ",
  "external_type": "sale",
  "rebill_number": 0,
  "adjusted_transaction_id": null,
  "billing_payment_instruction_id": 1333223,
  "billing_payment_instruction_type": "Billing::PaymentMethod"
}
```

| <div style="width:375px;">Event type</div>                            | Versions available | Description                                       | 
|---------------------------------------|--------------------|---------------------------------------------------|
| ***Orders::Transaction***                      |                    |                                                   |
| `orders/transaction.created`                   | V1, V2             | Sent when an Orders::Transaction has been created |
| `orders/transaction.updated`                   | V1, V2             | Sent when an Orders::Transaction has been updated |

**Invoice webhooks**

With the [Invoice payload](https://apidocs.myclickfunnels.com/tag/Orders::Invoice).

| <div style="width:375px;">Event type</div>   | Versions available   | Description                                                                 | 
|----------------------------------------------|----------------------|-----------------------------------------------------------------------------|
| ***Orders::Invoice***                        |                      |                                                                             |
| `orders/invoice.created`                     | V1, V2               | Sent when an Orders::Invoice has been created                               |
| `orders/invoice.updated`                     | V1, V2               | Sent when an Orders::Invoice has been updated                               |
| `orders/invoice.refunded`                    | V1, V2               | Sent when an Orders::Invoice has been refunded                              |
| `renewal-invoice-payment-declined`           | V1, V2               | Issued when a renewal Orders::Invoice payment has been declined             |
| ***OneTimeOrder::Invoice***                  |                      |                                                                             |
| `one-time-order.invoice.paid`                | V1, V2               | Sent when an Order::Invoice of `order_type: "one-time-order"` has been paid |
| ***Subscription::Invoice***                  |                      |                                                                             |
| `subscription/invoice.paid`                  | V1, V2               | Sent when an Order of `order_type: "subscription"` has been paid            |

**Workflow-based webhooks**

These are mostly used for [the UI-based ClickFunnels Workflows functionality](https://support.myclickfunnels.com/support/solutions/articles/150000156983-using-the-webhook-step-in-a-workflow).

| <div style="width:375px;">Event type</div>| Versions available | Description                                                        | 
|--------------------------------------------------|--------------------|--------------------------------------------------------------------| 
| ***Runs::Step***                                 |                    |                                                                    |
| `runs/step.dontrunme`                            | V1                 | Issued when the `dontrunme` step has been ran on a Workflow        |
| ***Workflows::Integration::Step***               |                    |                                                                    |
| `workflows_integration_step.executed`            | V1                 | Sent when a Workflows::Integration::Step has been executed         |
| ***Workflows::Steps::IntegrationStep***          |                    |                                                                    |
| `workflows/steps/integration_step.executed`      | V1                 | Sent when a Workflows::Steps::IntegrationStep has been executed    |
| ***Workflows::Steps::DeliverWebhookStep***       |                    |                                                                    |
| `workflows/steps/deliver_webhook_step.executed`  | V1                 | Sent when a Workflows::Steps::DeliverWebhookStep has been executed |


</div>

## Requirements

Building the API client library requires:

1. Java 1.8+
2. Maven (3.8.3+)/Gradle (7.2+)

If you are adding this library to an Android Application or Library:

3. Android 8.0+ (API Level 26+)

## Installation<a id="installation"></a>
<div align="center">
  <a href="https://konfigthis.com/sdk-sign-up?company=ClickFunnels&language=Java">
    <img src="https://raw.githubusercontent.com/konfig-dev/brand-assets/HEAD/cta-images/java-cta.png" width="70%">
  </a>
</div>

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.konfigthis</groupId>
  <artifactId>click-funnels-java-sdk</artifactId>
  <version>V2</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your `build.gradle`:

```groovy
// build.gradle
repositories {
  mavenCentral()
}

dependencies {
   implementation "com.konfigthis:click-funnels-java-sdk:V2"
}
```

### Android users

Make sure your `build.gradle` file as a `minSdk` version of at least 26:
```groovy
// build.gradle
android {
    defaultConfig {
        minSdk 26
    }
}
```

Also make sure your library or application has internet permissions in your `AndroidManifest.xml`:

```xml
<!--AndroidManifest.xml-->
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.INTERNET"/>
</manifest>
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/click-funnels-java-sdk-V2.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContactApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://myworkspace.myclickfunnels.com/api/v2";
    
    // Configure HTTP bearer authorization: BearerAuth
    configuration.token = "BEARER TOKEN";
    ClickFunnels client = new ClickFunnels(configuration);
    Integer workspaceId = 56;
    ContactParameters contact = new HashMap();
    try {
      ContactAttributes result = client
              .contact
              .createNewContact(workspaceId)
              .contact(contact)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getAnonymous());
      System.out.println(result.getEmailAddress());
      System.out.println(result.getFirstName());
      System.out.println(result.getLastName());
      System.out.println(result.getPhoneNumber());
      System.out.println(result.getTimeZone());
      System.out.println(result.getUuid());
      System.out.println(result.getUnsubscribedAt());
      System.out.println(result.getLastNotificationEmailSentAt());
      System.out.println(result.getFbUrl());
      System.out.println(result.getTwitterUrl());
      System.out.println(result.getInstagramUrl());
      System.out.println(result.getLinkedinUrl());
      System.out.println(result.getWebsiteUrl());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#createNewContact");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContactAttributes> response = client
              .contact
              .createNewContact(workspaceId)
              .contact(contact)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#createNewContact");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*ContactApi* | [**createNewContact**](docs/ContactApi.md#createNewContact) | **POST** /workspaces/{workspace_id}/contacts | Create Contact
*ContactApi* | [**getContactById**](docs/ContactApi.md#getContactById) | **GET** /contacts/{id} | Fetch Contact
*ContactApi* | [**listForWorkspace**](docs/ContactApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/contacts | List Contacts
*ContactApi* | [**redactPersonallyIdentifiable**](docs/ContactApi.md#redactPersonallyIdentifiable) | **DELETE** /contacts/{id}/gdpr_destroy | Redact personally identifiable information of a Contact
*ContactApi* | [**removeById**](docs/ContactApi.md#removeById) | **DELETE** /contacts/{id} | Remove Contact
*ContactApi* | [**updateContactById**](docs/ContactApi.md#updateContactById) | **PUT** /contacts/{id} | Update Contact
*ContactApi* | [**upsert**](docs/ContactApi.md#upsert) | **POST** /workspaces/{workspace_id}/contacts/upsert | Upsert a Contact
*ContactsAppliedTagApi* | [**createAppliedTag**](docs/ContactsAppliedTagApi.md#createAppliedTag) | **POST** /contacts/{contact_id}/applied_tags | Create Applied Tag
*ContactsAppliedTagApi* | [**getForContact**](docs/ContactsAppliedTagApi.md#getForContact) | **GET** /contacts/applied_tags/{id} | Fetch Applied Tag
*ContactsAppliedTagApi* | [**list**](docs/ContactsAppliedTagApi.md#list) | **GET** /contacts/{contact_id}/applied_tags | List Applied Tags
*ContactsAppliedTagApi* | [**removeById**](docs/ContactsAppliedTagApi.md#removeById) | **DELETE** /contacts/applied_tags/{id} | Remove Applied Tag
*ContactsTagApi* | [**createNewTag**](docs/ContactsTagApi.md#createNewTag) | **POST** /workspaces/{workspace_id}/contacts/tags | Create Tag
*ContactsTagApi* | [**getSingle**](docs/ContactsTagApi.md#getSingle) | **GET** /contacts/tags/{id} | Fetch Tag
*ContactsTagApi* | [**list**](docs/ContactsTagApi.md#list) | **GET** /workspaces/{workspace_id}/contacts/tags | List Tags
*ContactsTagApi* | [**remove**](docs/ContactsTagApi.md#remove) | **DELETE** /contacts/tags/{id} | Remove Tag
*ContactsTagApi* | [**updateSpecificTag**](docs/ContactsTagApi.md#updateSpecificTag) | **PUT** /contacts/tags/{id} | Update Tag
*CourseApi* | [**getById**](docs/CourseApi.md#getById) | **GET** /courses/{id} | Fetch Course
*CourseApi* | [**listForWorkspace**](docs/CourseApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/courses | List Courses
*CoursesEnrollmentApi* | [**createNewEnrollment**](docs/CoursesEnrollmentApi.md#createNewEnrollment) | **POST** /courses/{course_id}/enrollments | Create Enrollment
*CoursesEnrollmentApi* | [**getById**](docs/CoursesEnrollmentApi.md#getById) | **GET** /courses/enrollments/{id} | Fetch Enrollment
*CoursesEnrollmentApi* | [**list**](docs/CoursesEnrollmentApi.md#list) | **GET** /courses/{course_id}/enrollments | List Enrollments
*CoursesEnrollmentApi* | [**updateSpecificEnrollment**](docs/CoursesEnrollmentApi.md#updateSpecificEnrollment) | **PUT** /courses/enrollments/{id} | Update Enrollment
*CoursesLessonApi* | [**getById**](docs/CoursesLessonApi.md#getById) | **GET** /courses/lessons/{id} | Fetch Lesson
*CoursesLessonApi* | [**listLessons**](docs/CoursesLessonApi.md#listLessons) | **GET** /courses/sections/{section_id}/lessons | List Lessons
*CoursesLessonApi* | [**updateLessonById**](docs/CoursesLessonApi.md#updateLessonById) | **PUT** /courses/lessons/{id} | Update Lesson
*CoursesSectionApi* | [**getSection**](docs/CoursesSectionApi.md#getSection) | **GET** /courses/sections/{id} | Fetch Section
*CoursesSectionApi* | [**listSections**](docs/CoursesSectionApi.md#listSections) | **GET** /courses/{course_id}/sections | List Sections
*CoursesSectionApi* | [**updateSectionById**](docs/CoursesSectionApi.md#updateSectionById) | **PUT** /courses/sections/{id} | Update Section
*FormApi* | [**createNewForm**](docs/FormApi.md#createNewForm) | **POST** /workspaces/{workspace_id}/forms | Create Form
*FormApi* | [**getForm**](docs/FormApi.md#getForm) | **GET** /forms/{id} | Fetch Form
*FormApi* | [**listWorkspaceForms**](docs/FormApi.md#listWorkspaceForms) | **GET** /workspaces/{workspace_id}/forms | List Forms
*FormApi* | [**remove**](docs/FormApi.md#remove) | **DELETE** /forms/{id} | Remove Form
*FormApi* | [**updateFormById**](docs/FormApi.md#updateFormById) | **PUT** /forms/{id} | Update Form
*FormsFieldApi* | [**addNewField**](docs/FormsFieldApi.md#addNewField) | **POST** /forms/field_sets/{field_set_id}/fields | Create Field
*FormsFieldApi* | [**getField**](docs/FormsFieldApi.md#getField) | **GET** /forms/fields/{id} | Fetch Field
*FormsFieldApi* | [**listFieldsForFieldSet**](docs/FormsFieldApi.md#listFieldsForFieldSet) | **GET** /forms/field_sets/{field_set_id}/fields | List Fields
*FormsFieldApi* | [**removeField**](docs/FormsFieldApi.md#removeField) | **DELETE** /forms/fields/{id} | Remove Field
*FormsFieldApi* | [**updateFieldById**](docs/FormsFieldApi.md#updateFieldById) | **PUT** /forms/fields/{id} | Update Field
*FormsFieldSetApi* | [**createNewFieldSet**](docs/FormsFieldSetApi.md#createNewFieldSet) | **POST** /forms/{form_id}/field_sets | Create Field Set
*FormsFieldSetApi* | [**getFieldSet**](docs/FormsFieldSetApi.md#getFieldSet) | **GET** /forms/field_sets/{id} | Fetch Field Set
*FormsFieldSetApi* | [**list**](docs/FormsFieldSetApi.md#list) | **GET** /forms/{form_id}/field_sets | List Field Sets
*FormsFieldSetApi* | [**remove**](docs/FormsFieldSetApi.md#remove) | **DELETE** /forms/field_sets/{id} | Remove Field Set
*FormsFieldSetApi* | [**updateFieldSetById**](docs/FormsFieldSetApi.md#updateFieldSetById) | **PUT** /forms/field_sets/{id} | Update Field Set
*FormsFieldsOptionApi* | [**createNewFieldOption**](docs/FormsFieldsOptionApi.md#createNewFieldOption) | **POST** /forms/fields/{field_id}/options | Create Option
*FormsFieldsOptionApi* | [**deleteOptionForField**](docs/FormsFieldsOptionApi.md#deleteOptionForField) | **DELETE** /forms/fields/options/{id} | Remove Option
*FormsFieldsOptionApi* | [**getFieldOption**](docs/FormsFieldsOptionApi.md#getFieldOption) | **GET** /forms/fields/options/{id} | Fetch Option
*FormsFieldsOptionApi* | [**list**](docs/FormsFieldsOptionApi.md#list) | **GET** /forms/fields/{field_id}/options | List Options
*FormsFieldsOptionApi* | [**updateFieldOption**](docs/FormsFieldsOptionApi.md#updateFieldOption) | **PUT** /forms/fields/options/{id} | Update Option
*FormsSubmissionApi* | [**createNewSubmission**](docs/FormsSubmissionApi.md#createNewSubmission) | **POST** /forms/{form_id}/submissions | Create Submission
*FormsSubmissionApi* | [**getById**](docs/FormsSubmissionApi.md#getById) | **GET** /forms/submissions/{id} | Fetch Submission
*FormsSubmissionApi* | [**list**](docs/FormsSubmissionApi.md#list) | **GET** /forms/{form_id}/submissions | List Submissions
*FormsSubmissionApi* | [**remove**](docs/FormsSubmissionApi.md#remove) | **DELETE** /forms/submissions/{id} | Remove Submission
*FormsSubmissionApi* | [**updateSubmission**](docs/FormsSubmissionApi.md#updateSubmission) | **PUT** /forms/submissions/{id} | Update Submission
*FormsSubmissionsAnswerApi* | [**addNewAnswer**](docs/FormsSubmissionsAnswerApi.md#addNewAnswer) | **POST** /forms/submissions/{submission_id}/answers | Create Answer
*FormsSubmissionsAnswerApi* | [**get**](docs/FormsSubmissionsAnswerApi.md#get) | **GET** /forms/submissions/answers/{id} | Fetch Answer
*FormsSubmissionsAnswerApi* | [**list**](docs/FormsSubmissionsAnswerApi.md#list) | **GET** /forms/submissions/{submission_id}/answers | List Answers
*FormsSubmissionsAnswerApi* | [**removeById**](docs/FormsSubmissionsAnswerApi.md#removeById) | **DELETE** /forms/submissions/answers/{id} | Remove Answer
*FormsSubmissionsAnswerApi* | [**updateAnswer**](docs/FormsSubmissionsAnswerApi.md#updateAnswer) | **PUT** /forms/submissions/answers/{id} | Update Answer
*FulfillmentApi* | [**cancelFulfillment**](docs/FulfillmentApi.md#cancelFulfillment) | **POST** /fulfillments/{id}/cancel | Cancel a Fulfillment
*FulfillmentApi* | [**create**](docs/FulfillmentApi.md#create) | **POST** /workspaces/{workspace_id}/fulfillments | Create Fulfillment
*FulfillmentApi* | [**getById**](docs/FulfillmentApi.md#getById) | **GET** /fulfillments/{id} | Fetch Fulfillment
*FulfillmentApi* | [**list**](docs/FulfillmentApi.md#list) | **GET** /workspaces/{workspace_id}/fulfillments | List Fulfillments
*FulfillmentApi* | [**updateById**](docs/FulfillmentApi.md#updateById) | **PUT** /fulfillments/{id} | Update Fulfillment
*FulfillmentsLocationApi* | [**createNewLocation**](docs/FulfillmentsLocationApi.md#createNewLocation) | **POST** /workspaces/{workspace_id}/fulfillments/locations | Create Location
*FulfillmentsLocationApi* | [**getById**](docs/FulfillmentsLocationApi.md#getById) | **GET** /fulfillments/locations/{id} | Fetch Location
*FulfillmentsLocationApi* | [**list**](docs/FulfillmentsLocationApi.md#list) | **GET** /workspaces/{workspace_id}/fulfillments/locations | List Locations
*FulfillmentsLocationApi* | [**removeById**](docs/FulfillmentsLocationApi.md#removeById) | **DELETE** /fulfillments/locations/{id} | Remove Location
*FulfillmentsLocationApi* | [**updateById**](docs/FulfillmentsLocationApi.md#updateById) | **PUT** /fulfillments/locations/{id} | Update Location
*ImageApi* | [**create**](docs/ImageApi.md#create) | **POST** /workspaces/{workspace_id}/images | Create Image
*ImageApi* | [**getById**](docs/ImageApi.md#getById) | **GET** /images/{id} | Fetch Image
*ImageApi* | [**list**](docs/ImageApi.md#list) | **GET** /workspaces/{workspace_id}/images | List Images
*ImageApi* | [**removeById**](docs/ImageApi.md#removeById) | **DELETE** /images/{id} | Remove Image
*ImageApi* | [**updateById**](docs/ImageApi.md#updateById) | **PUT** /images/{id} | Update Image
*OrderApi* | [**getSingle**](docs/OrderApi.md#getSingle) | **GET** /orders/{id} | Fetch Order
*OrderApi* | [**listOrders**](docs/OrderApi.md#listOrders) | **GET** /workspaces/{workspace_id}/orders | List Orders
*OrderApi* | [**updateSpecific**](docs/OrderApi.md#updateSpecific) | **PUT** /orders/{id} | Update Order
*OrdersAppliedTagApi* | [**createAppliedTag**](docs/OrdersAppliedTagApi.md#createAppliedTag) | **POST** /orders/{order_id}/applied_tags | Create Applied Tag
*OrdersAppliedTagApi* | [**get**](docs/OrdersAppliedTagApi.md#get) | **GET** /orders/applied_tags/{id} | Fetch Applied Tag
*OrdersAppliedTagApi* | [**list**](docs/OrdersAppliedTagApi.md#list) | **GET** /orders/{order_id}/applied_tags | List Applied Tags
*OrdersAppliedTagApi* | [**removeById**](docs/OrdersAppliedTagApi.md#removeById) | **DELETE** /orders/applied_tags/{id} | Remove Applied Tag
*OrdersInvoiceApi* | [**getForOrder**](docs/OrdersInvoiceApi.md#getForOrder) | **GET** /orders/invoices/{id} | Fetch Invoice
*OrdersInvoiceApi* | [**listForOrder**](docs/OrdersInvoiceApi.md#listForOrder) | **GET** /orders/{order_id}/invoices | List Invoices
*OrdersInvoicesRestockApi* | [**getRestock**](docs/OrdersInvoicesRestockApi.md#getRestock) | **GET** /orders/invoices/restocks/{id} | Fetch Restock
*OrdersInvoicesRestockApi* | [**listRestocks**](docs/OrdersInvoicesRestockApi.md#listRestocks) | **GET** /workspaces/{workspace_id}/orders/invoices/restocks | List Restocks
*OrdersTagApi* | [**createNewTag**](docs/OrdersTagApi.md#createNewTag) | **POST** /workspaces/{workspace_id}/orders/tags | Create Tag
*OrdersTagApi* | [**getSingle**](docs/OrdersTagApi.md#getSingle) | **GET** /orders/tags/{id} | Fetch Tag
*OrdersTagApi* | [**list**](docs/OrdersTagApi.md#list) | **GET** /workspaces/{workspace_id}/orders/tags | List Tags
*OrdersTagApi* | [**remove**](docs/OrdersTagApi.md#remove) | **DELETE** /orders/tags/{id} | Remove Tag
*OrdersTagApi* | [**updateSpecificOrderTag**](docs/OrdersTagApi.md#updateSpecificOrderTag) | **PUT** /orders/tags/{id} | Update Tag
*OrdersTransactionApi* | [**getById**](docs/OrdersTransactionApi.md#getById) | **GET** /orders/transactions/{id} | Fetch Transaction
*OrdersTransactionApi* | [**getList**](docs/OrdersTransactionApi.md#getList) | **GET** /orders/{order_id}/transactions | List Transactions
*ProductApi* | [**addNewToWorkspace**](docs/ProductApi.md#addNewToWorkspace) | **POST** /workspaces/{workspace_id}/products | Create Product
*ProductApi* | [**archiveProduct**](docs/ProductApi.md#archiveProduct) | **POST** /products/{id}/archive | Archive a Product
*ProductApi* | [**getForWorkspace**](docs/ProductApi.md#getForWorkspace) | **GET** /products/{id} | Fetch Product
*ProductApi* | [**listForWorkspace**](docs/ProductApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/products | List Products
*ProductApi* | [**unarchiveById**](docs/ProductApi.md#unarchiveById) | **POST** /products/{id}/unarchive | Unarchive a Product
*ProductApi* | [**updateForWorkspace**](docs/ProductApi.md#updateForWorkspace) | **PUT** /products/{id} | Update Product
*ProductsPriceApi* | [**createVariantPrice**](docs/ProductsPriceApi.md#createVariantPrice) | **POST** /products/{product_id}/prices | Create Price
*ProductsPriceApi* | [**getSinglePrice**](docs/ProductsPriceApi.md#getSinglePrice) | **GET** /products/prices/{id} | Fetch Price
*ProductsPriceApi* | [**listForVariant**](docs/ProductsPriceApi.md#listForVariant) | **GET** /products/{product_id}/prices | List Prices
*ProductsPriceApi* | [**updateSinglePrice**](docs/ProductsPriceApi.md#updateSinglePrice) | **PUT** /products/prices/{id} | Update Price
*ProductsTagApi* | [**createNewTag**](docs/ProductsTagApi.md#createNewTag) | **POST** /workspaces/{workspace_id}/products/tags | Create Tag
*ProductsTagApi* | [**deleteTagById**](docs/ProductsTagApi.md#deleteTagById) | **DELETE** /products/tags/{id} | Remove Tag
*ProductsTagApi* | [**getTagById**](docs/ProductsTagApi.md#getTagById) | **GET** /products/tags/{id} | Fetch Tag
*ProductsTagApi* | [**list**](docs/ProductsTagApi.md#list) | **GET** /workspaces/{workspace_id}/products/tags | List Tags
*ProductsTagApi* | [**updateTagById**](docs/ProductsTagApi.md#updateTagById) | **PUT** /products/tags/{id} | Update Tag
*ProductsVariantApi* | [**createNewVariant**](docs/ProductsVariantApi.md#createNewVariant) | **POST** /products/{product_id}/variants | Create Variant
*ProductsVariantApi* | [**getSingle**](docs/ProductsVariantApi.md#getSingle) | **GET** /products/variants/{id} | Fetch Variant
*ProductsVariantApi* | [**list**](docs/ProductsVariantApi.md#list) | **GET** /products/{product_id}/variants | List Variants
*ProductsVariantApi* | [**updateSingle**](docs/ProductsVariantApi.md#updateSingle) | **PUT** /products/variants/{id} | Update Variant
*ShippingLocationGroupApi* | [**getProfileLocationGroup**](docs/ShippingLocationGroupApi.md#getProfileLocationGroup) | **GET** /shipping/location_groups/{id} | Fetch Location Group
*ShippingLocationGroupApi* | [**list**](docs/ShippingLocationGroupApi.md#list) | **GET** /shipping/profiles/{profile_id}/location_groups | List Location Groups
*ShippingPackageApi* | [**addToWorkspace**](docs/ShippingPackageApi.md#addToWorkspace) | **POST** /workspaces/{workspace_id}/shipping/packages | Create Package
*ShippingPackageApi* | [**getForWorkspace**](docs/ShippingPackageApi.md#getForWorkspace) | **GET** /shipping/packages/{id} | Fetch Package
*ShippingPackageApi* | [**listForWorkspace**](docs/ShippingPackageApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/shipping/packages | List Packages
*ShippingPackageApi* | [**removeById**](docs/ShippingPackageApi.md#removeById) | **DELETE** /shipping/packages/{id} | Remove Package
*ShippingPackageApi* | [**updateForWorkspace**](docs/ShippingPackageApi.md#updateForWorkspace) | **PUT** /shipping/packages/{id} | Update Package
*ShippingProfileApi* | [**createNew**](docs/ShippingProfileApi.md#createNew) | **POST** /workspaces/{workspace_id}/shipping/profiles | Create Profile
*ShippingProfileApi* | [**getWorkspaceProfile**](docs/ShippingProfileApi.md#getWorkspaceProfile) | **GET** /shipping/profiles/{id} | Fetch Profile
*ShippingProfileApi* | [**list**](docs/ShippingProfileApi.md#list) | **GET** /workspaces/{workspace_id}/shipping/profiles | List Profiles
*ShippingProfileApi* | [**remove**](docs/ShippingProfileApi.md#remove) | **DELETE** /shipping/profiles/{id} | Remove Profile
*ShippingProfileApi* | [**updateForWorkspace**](docs/ShippingProfileApi.md#updateForWorkspace) | **PUT** /shipping/profiles/{id} | Update Profile
*ShippingRateApi* | [**createRateForZone**](docs/ShippingRateApi.md#createRateForZone) | **POST** /shipping/zones/{zone_id}/rates | Create Rate
*ShippingRateApi* | [**getRateById**](docs/ShippingRateApi.md#getRateById) | **GET** /shipping/rates/{id} | Fetch Rate
*ShippingRateApi* | [**listForZone**](docs/ShippingRateApi.md#listForZone) | **GET** /shipping/zones/{zone_id}/rates | List Rates
*ShippingRateApi* | [**removeById**](docs/ShippingRateApi.md#removeById) | **DELETE** /shipping/rates/{id} | Remove Rate
*ShippingRateApi* | [**updateRateForZone**](docs/ShippingRateApi.md#updateRateForZone) | **PUT** /shipping/rates/{id} | Update Rate
*ShippingRatesNameApi* | [**createNewRateName**](docs/ShippingRatesNameApi.md#createNewRateName) | **POST** /workspaces/{workspace_id}/shipping/rates/names | Create Name
*ShippingRatesNameApi* | [**getRateName**](docs/ShippingRatesNameApi.md#getRateName) | **GET** /shipping/rates/names/{id} | Fetch Name
*ShippingRatesNameApi* | [**list**](docs/ShippingRatesNameApi.md#list) | **GET** /workspaces/{workspace_id}/shipping/rates/names | List Names
*ShippingRatesNameApi* | [**remove**](docs/ShippingRatesNameApi.md#remove) | **DELETE** /shipping/rates/names/{id} | Remove Name
*ShippingRatesNameApi* | [**updateName**](docs/ShippingRatesNameApi.md#updateName) | **PUT** /shipping/rates/names/{id} | Update Name
*ShippingZoneApi* | [**addNewZone**](docs/ShippingZoneApi.md#addNewZone) | **POST** /shipping/location_groups/{location_group_id}/zones | Create Zone
*ShippingZoneApi* | [**getZoneById**](docs/ShippingZoneApi.md#getZoneById) | **GET** /shipping/zones/{id} | Fetch Zone
*ShippingZoneApi* | [**listZones**](docs/ShippingZoneApi.md#listZones) | **GET** /shipping/location_groups/{location_group_id}/zones | List Zones
*ShippingZoneApi* | [**removeById**](docs/ShippingZoneApi.md#removeById) | **DELETE** /shipping/zones/{id} | Remove Zone
*ShippingZoneApi* | [**updateZoneById**](docs/ShippingZoneApi.md#updateZoneById) | **PUT** /shipping/zones/{id} | Update Zone
*TeamApi* | [**getAll**](docs/TeamApi.md#getAll) | **GET** /teams | List Teams
*TeamApi* | [**getSingle**](docs/TeamApi.md#getSingle) | **GET** /teams/{id} | Fetch Team
*TeamApi* | [**updateTeamById**](docs/TeamApi.md#updateTeamById) | **PUT** /teams/{id} | Update Team
*UserApi* | [**getSingle**](docs/UserApi.md#getSingle) | **GET** /users/{id} | Fetch User
*UserApi* | [**listCurrentAccountUsers**](docs/UserApi.md#listCurrentAccountUsers) | **GET** /users | List Users
*UserApi* | [**updateSingleUser**](docs/UserApi.md#updateSingleUser) | **PUT** /users/{id} | Update User
*WebhooksOutgoingEndpointApi* | [**createNew**](docs/WebhooksOutgoingEndpointApi.md#createNew) | **POST** /workspaces/{workspace_id}/webhooks/outgoing/endpoints | Create Endpoint
*WebhooksOutgoingEndpointApi* | [**get**](docs/WebhooksOutgoingEndpointApi.md#get) | **GET** /webhooks/outgoing/endpoints/{id} | Fetch Endpoint
*WebhooksOutgoingEndpointApi* | [**listEndpoints**](docs/WebhooksOutgoingEndpointApi.md#listEndpoints) | **GET** /workspaces/{workspace_id}/webhooks/outgoing/endpoints | List Endpoints
*WebhooksOutgoingEndpointApi* | [**updateEndpoint**](docs/WebhooksOutgoingEndpointApi.md#updateEndpoint) | **PUT** /webhooks/outgoing/endpoints/{id} | Update Endpoint
*WebhooksOutgoingEventApi* | [**getForWorkspace**](docs/WebhooksOutgoingEventApi.md#getForWorkspace) | **GET** /webhooks/outgoing/events/{id} | Fetch Event
*WebhooksOutgoingEventApi* | [**listForWorkspace**](docs/WebhooksOutgoingEventApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/webhooks/outgoing/events | List Events
*WorkspaceApi* | [**addNew**](docs/WorkspaceApi.md#addNew) | **POST** /teams/{team_id}/workspaces | Create Workspace
*WorkspaceApi* | [**getById**](docs/WorkspaceApi.md#getById) | **GET** /workspaces/{id} | Fetch Workspace
*WorkspaceApi* | [**listWorkspaces**](docs/WorkspaceApi.md#listWorkspaces) | **GET** /teams/{team_id}/workspaces | List Workspaces
*WorkspaceApi* | [**update**](docs/WorkspaceApi.md#update) | **PUT** /workspaces/{id} | Update Workspace


## Documentation for Models

 - [ContactAttributes](docs/ContactAttributes.md)
 - [ContactCreateNewContactRequest](docs/ContactCreateNewContactRequest.md)
 - [ContactListForWorkspaceFilterParameter](docs/ContactListForWorkspaceFilterParameter.md)
 - [ContactParameters](docs/ContactParameters.md)
 - [ContactTagAttributes](docs/ContactTagAttributes.md)
 - [ContactUpdateContactByIdRequest](docs/ContactUpdateContactByIdRequest.md)
 - [ContactUpsertRequest](docs/ContactUpsertRequest.md)
 - [ContactsAppliedTagAttributes](docs/ContactsAppliedTagAttributes.md)
 - [ContactsAppliedTagCreateAppliedTagRequest](docs/ContactsAppliedTagCreateAppliedTagRequest.md)
 - [ContactsAppliedTagParameters](docs/ContactsAppliedTagParameters.md)
 - [ContactsProperty](docs/ContactsProperty.md)
 - [ContactsPropertyTagIdsInner](docs/ContactsPropertyTagIdsInner.md)
 - [ContactsTagAttributes](docs/ContactsTagAttributes.md)
 - [ContactsTagCreateNewTagRequest](docs/ContactsTagCreateNewTagRequest.md)
 - [ContactsTagParameters](docs/ContactsTagParameters.md)
 - [ContactsTagUpdateSpecificTagRequest](docs/ContactsTagUpdateSpecificTagRequest.md)
 - [CourseAttributes](docs/CourseAttributes.md)
 - [CoursesEnrollmentAttributes](docs/CoursesEnrollmentAttributes.md)
 - [CoursesEnrollmentCreateNewEnrollmentRequest](docs/CoursesEnrollmentCreateNewEnrollmentRequest.md)
 - [CoursesEnrollmentParameters](docs/CoursesEnrollmentParameters.md)
 - [CoursesEnrollmentUpdateSpecificEnrollmentRequest](docs/CoursesEnrollmentUpdateSpecificEnrollmentRequest.md)
 - [CoursesLessonAttributes](docs/CoursesLessonAttributes.md)
 - [CoursesLessonParameters](docs/CoursesLessonParameters.md)
 - [CoursesLessonUpdateLessonByIdRequest](docs/CoursesLessonUpdateLessonByIdRequest.md)
 - [CoursesSectionAttributes](docs/CoursesSectionAttributes.md)
 - [CoursesSectionParameters](docs/CoursesSectionParameters.md)
 - [CoursesSectionUpdateSectionByIdRequest](docs/CoursesSectionUpdateSectionByIdRequest.md)
 - [FormAttributes](docs/FormAttributes.md)
 - [FormCreateNewFormRequest](docs/FormCreateNewFormRequest.md)
 - [FormParameters](docs/FormParameters.md)
 - [FormUpdateFormByIdRequest](docs/FormUpdateFormByIdRequest.md)
 - [FormsFieldAddNewFieldRequest](docs/FormsFieldAddNewFieldRequest.md)
 - [FormsFieldAttributes](docs/FormsFieldAttributes.md)
 - [FormsFieldParameters](docs/FormsFieldParameters.md)
 - [FormsFieldSetAttributes](docs/FormsFieldSetAttributes.md)
 - [FormsFieldSetCreateNewFieldSetRequest](docs/FormsFieldSetCreateNewFieldSetRequest.md)
 - [FormsFieldSetParameters](docs/FormsFieldSetParameters.md)
 - [FormsFieldSetUpdateFieldSetByIdRequest](docs/FormsFieldSetUpdateFieldSetByIdRequest.md)
 - [FormsFieldUpdateFieldByIdRequest](docs/FormsFieldUpdateFieldByIdRequest.md)
 - [FormsFieldsOptionAttributes](docs/FormsFieldsOptionAttributes.md)
 - [FormsFieldsOptionCreateNewFieldOptionRequest](docs/FormsFieldsOptionCreateNewFieldOptionRequest.md)
 - [FormsFieldsOptionParameters](docs/FormsFieldsOptionParameters.md)
 - [FormsFieldsOptionUpdateFieldOptionRequest](docs/FormsFieldsOptionUpdateFieldOptionRequest.md)
 - [FormsSubmissionAttributes](docs/FormsSubmissionAttributes.md)
 - [FormsSubmissionCreateNewSubmissionRequest](docs/FormsSubmissionCreateNewSubmissionRequest.md)
 - [FormsSubmissionParameters](docs/FormsSubmissionParameters.md)
 - [FormsSubmissionUpdateSubmissionRequest](docs/FormsSubmissionUpdateSubmissionRequest.md)
 - [FormsSubmissionsAnswerAddNewAnswerRequest](docs/FormsSubmissionsAnswerAddNewAnswerRequest.md)
 - [FormsSubmissionsAnswerAttributes](docs/FormsSubmissionsAnswerAttributes.md)
 - [FormsSubmissionsAnswerParameters](docs/FormsSubmissionsAnswerParameters.md)
 - [FormsSubmissionsAnswerUpdateAnswerRequest](docs/FormsSubmissionsAnswerUpdateAnswerRequest.md)
 - [FulfillmentAttributes](docs/FulfillmentAttributes.md)
 - [FulfillmentCreateRequest](docs/FulfillmentCreateRequest.md)
 - [FulfillmentParameters](docs/FulfillmentParameters.md)
 - [FulfillmentParametersIncludedOrdersInvoicesLineItemsAttributesInner](docs/FulfillmentParametersIncludedOrdersInvoicesLineItemsAttributesInner.md)
 - [FulfillmentUpdateByIdRequest](docs/FulfillmentUpdateByIdRequest.md)
 - [FulfillmentsLocationAttributes](docs/FulfillmentsLocationAttributes.md)
 - [FulfillmentsLocationCreateNewLocationRequest](docs/FulfillmentsLocationCreateNewLocationRequest.md)
 - [FulfillmentsLocationParameters](docs/FulfillmentsLocationParameters.md)
 - [FulfillmentsLocationParametersAddress](docs/FulfillmentsLocationParametersAddress.md)
 - [FulfillmentsLocationUpdateByIdRequest](docs/FulfillmentsLocationUpdateByIdRequest.md)
 - [ImageAttributes](docs/ImageAttributes.md)
 - [ImageCreateRequest](docs/ImageCreateRequest.md)
 - [ImageParameters](docs/ImageParameters.md)
 - [ImageUpdateByIdRequest](docs/ImageUpdateByIdRequest.md)
 - [IncludedOrdersInvoicesLineItems](docs/IncludedOrdersInvoicesLineItems.md)
 - [IncludedOrdersInvoicesLineItems1](docs/IncludedOrdersInvoicesLineItems1.md)
 - [IncludedOrdersLineItems](docs/IncludedOrdersLineItems.md)
 - [LineItemsProperty](docs/LineItemsProperty.md)
 - [LineItemsProperty1](docs/LineItemsProperty1.md)
 - [LocationsProperty](docs/LocationsProperty.md)
 - [OrderAttributes](docs/OrderAttributes.md)
 - [OrderContactGroupAttributes](docs/OrderContactGroupAttributes.md)
 - [OrderListOrdersFilterParameter](docs/OrderListOrdersFilterParameter.md)
 - [OrderPageAttributes](docs/OrderPageAttributes.md)
 - [OrderParameters](docs/OrderParameters.md)
 - [OrderSegmentAttributes](docs/OrderSegmentAttributes.md)
 - [OrderUpdateSpecificRequest](docs/OrderUpdateSpecificRequest.md)
 - [OrdersAppliedTagAttributes](docs/OrdersAppliedTagAttributes.md)
 - [OrdersAppliedTagCreateAppliedTagRequest](docs/OrdersAppliedTagCreateAppliedTagRequest.md)
 - [OrdersAppliedTagParameters](docs/OrdersAppliedTagParameters.md)
 - [OrdersInvoiceAttributes](docs/OrdersInvoiceAttributes.md)
 - [OrdersInvoicesLineItemAttributes](docs/OrdersInvoicesLineItemAttributes.md)
 - [OrdersInvoicesRestockGetRestockResponse](docs/OrdersInvoicesRestockGetRestockResponse.md)
 - [OrdersLineItemAttributes](docs/OrdersLineItemAttributes.md)
 - [OrdersTagAttributes](docs/OrdersTagAttributes.md)
 - [OrdersTagCreateNewTagRequest](docs/OrdersTagCreateNewTagRequest.md)
 - [OrdersTagParameters](docs/OrdersTagParameters.md)
 - [OrdersTagUpdateSpecificOrderTagRequest](docs/OrdersTagUpdateSpecificOrderTagRequest.md)
 - [OrdersTransactionAttributes](docs/OrdersTransactionAttributes.md)
 - [PagesProperty](docs/PagesProperty.md)
 - [ProductAddNewToWorkspaceRequest](docs/ProductAddNewToWorkspaceRequest.md)
 - [ProductAttributes](docs/ProductAttributes.md)
 - [ProductParameters](docs/ProductParameters.md)
 - [ProductParametersVariantPropertiesInner](docs/ProductParametersVariantPropertiesInner.md)
 - [ProductPriceProperty](docs/ProductPriceProperty.md)
 - [ProductProperty](docs/ProductProperty.md)
 - [ProductUpdateForWorkspaceRequest](docs/ProductUpdateForWorkspaceRequest.md)
 - [ProductVariantProperty](docs/ProductVariantProperty.md)
 - [ProductsPriceAttributes](docs/ProductsPriceAttributes.md)
 - [ProductsPriceCreateVariantPriceRequest](docs/ProductsPriceCreateVariantPriceRequest.md)
 - [ProductsPriceParameters](docs/ProductsPriceParameters.md)
 - [ProductsPriceUpdateSinglePriceRequest](docs/ProductsPriceUpdateSinglePriceRequest.md)
 - [ProductsTagAttributes](docs/ProductsTagAttributes.md)
 - [ProductsTagCreateNewTagRequest](docs/ProductsTagCreateNewTagRequest.md)
 - [ProductsTagParameters](docs/ProductsTagParameters.md)
 - [ProductsTagUpdateTagByIdRequest](docs/ProductsTagUpdateTagByIdRequest.md)
 - [ProductsVariantAttributes](docs/ProductsVariantAttributes.md)
 - [ProductsVariantCreateNewVariantRequest](docs/ProductsVariantCreateNewVariantRequest.md)
 - [ProductsVariantParameters](docs/ProductsVariantParameters.md)
 - [ProductsVariantParametersPropertiesValuesInner](docs/ProductsVariantParametersPropertiesValuesInner.md)
 - [ProductsVariantUpdateSingleRequest](docs/ProductsVariantUpdateSingleRequest.md)
 - [Restocks](docs/Restocks.md)
 - [ShippingLocationGroupAttributes](docs/ShippingLocationGroupAttributes.md)
 - [ShippingPackageAddToWorkspaceRequest](docs/ShippingPackageAddToWorkspaceRequest.md)
 - [ShippingPackageAttributes](docs/ShippingPackageAttributes.md)
 - [ShippingPackageParameters](docs/ShippingPackageParameters.md)
 - [ShippingPackageUpdateForWorkspaceRequest](docs/ShippingPackageUpdateForWorkspaceRequest.md)
 - [ShippingProfileAttributes](docs/ShippingProfileAttributes.md)
 - [ShippingProfileCreateNewRequest](docs/ShippingProfileCreateNewRequest.md)
 - [ShippingProfileParameters](docs/ShippingProfileParameters.md)
 - [ShippingProfileUpdateForWorkspaceRequest](docs/ShippingProfileUpdateForWorkspaceRequest.md)
 - [ShippingRateAttributes](docs/ShippingRateAttributes.md)
 - [ShippingRateCreateRateForZoneRequest](docs/ShippingRateCreateRateForZoneRequest.md)
 - [ShippingRateParameters](docs/ShippingRateParameters.md)
 - [ShippingRateUpdateRateForZoneRequest](docs/ShippingRateUpdateRateForZoneRequest.md)
 - [ShippingRatesNameAttributes](docs/ShippingRatesNameAttributes.md)
 - [ShippingRatesNameCreateNewRateNameRequest](docs/ShippingRatesNameCreateNewRateNameRequest.md)
 - [ShippingRatesNameParameters](docs/ShippingRatesNameParameters.md)
 - [ShippingRatesNameUpdateNameRequest](docs/ShippingRatesNameUpdateNameRequest.md)
 - [ShippingZoneAddNewZoneRequest](docs/ShippingZoneAddNewZoneRequest.md)
 - [ShippingZoneAttributes](docs/ShippingZoneAttributes.md)
 - [ShippingZoneParameters](docs/ShippingZoneParameters.md)
 - [ShippingZoneUpdateZoneByIdRequest](docs/ShippingZoneUpdateZoneByIdRequest.md)
 - [TeamAttributes](docs/TeamAttributes.md)
 - [TeamParameters](docs/TeamParameters.md)
 - [TeamUpdateTeamByIdRequest](docs/TeamUpdateTeamByIdRequest.md)
 - [TranslationMissingEnProductsPropertiesTitle](docs/TranslationMissingEnProductsPropertiesTitle.md)
 - [TranslationMissingEnProductsPropertiesValuesTitle](docs/TranslationMissingEnProductsPropertiesValuesTitle.md)
 - [UserAttributes](docs/UserAttributes.md)
 - [UserParameters](docs/UserParameters.md)
 - [UserUpdateSingleUserRequest](docs/UserUpdateSingleUserRequest.md)
 - [WebhooksOutgoingEndpointAttributes](docs/WebhooksOutgoingEndpointAttributes.md)
 - [WebhooksOutgoingEndpointCreateNewRequest](docs/WebhooksOutgoingEndpointCreateNewRequest.md)
 - [WebhooksOutgoingEndpointParameters](docs/WebhooksOutgoingEndpointParameters.md)
 - [WebhooksOutgoingEndpointUpdateEndpointRequest](docs/WebhooksOutgoingEndpointUpdateEndpointRequest.md)
 - [WebhooksOutgoingEventAttributes](docs/WebhooksOutgoingEventAttributes.md)
 - [WebhooksOutgoingEventAttributesData](docs/WebhooksOutgoingEventAttributesData.md)
 - [WorkspaceAddNewRequest](docs/WorkspaceAddNewRequest.md)
 - [WorkspaceAttributes](docs/WorkspaceAttributes.md)
 - [WorkspaceParameters](docs/WorkspaceParameters.md)
 - [WorkspaceUpdateRequest](docs/WorkspaceUpdateRequest.md)


## Author
This Java package is automatically generated by [Konfig](https://konfigthis.com)
