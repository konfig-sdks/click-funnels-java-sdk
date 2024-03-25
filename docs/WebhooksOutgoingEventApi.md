# WebhooksOutgoingEventApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getForWorkspace**](WebhooksOutgoingEventApi.md#getForWorkspace) | **GET** /webhooks/outgoing/events/{id} | Fetch Event |
| [**listForWorkspace**](WebhooksOutgoingEventApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/webhooks/outgoing/events | List Events |


<a name="getForWorkspace"></a>
# **getForWorkspace**
> WebhooksOutgoingEventAttributes getForWorkspace(id).execute();

Fetch Event

Retrieve an webhook event for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksOutgoingEventApi;
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
    String id = "id_example";
    try {
      WebhooksOutgoingEventAttributes result = client
              .webhooksOutgoingEvent
              .getForWorkspace(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getUuid());
      System.out.println(result.getEventTypeId());
      System.out.println(result.getSubjectId());
      System.out.println(result.getSubjectType());
      System.out.println(result.getData());
      System.out.println(result.getCreatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEventApi#getForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<WebhooksOutgoingEventAttributes> response = client
              .webhooksOutgoingEvent
              .getForWorkspace(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEventApi#getForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

[**WebhooksOutgoingEventAttributes**](WebhooksOutgoingEventAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="listForWorkspace"></a>
# **listForWorkspace**
> List&lt;WebhooksOutgoingEventAttributes&gt; listForWorkspace(workspaceId).after(after).sortOrder(sortOrder).execute();

List Events

List webhook events for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksOutgoingEventApi;
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
    String after = "after_example"; // ID of item after which the collection should be returned
    String sortOrder = "asc"; // Sort order of a list response. Use 'desc' to reverse the default 'asc' (ascending) sort order.
    try {
      List<WebhooksOutgoingEventAttributes> result = client
              .webhooksOutgoingEvent
              .listForWorkspace(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEventApi#listForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<WebhooksOutgoingEventAttributes>> response = client
              .webhooksOutgoingEvent
              .listForWorkspace(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEventApi#listForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **workspaceId** | **Integer**|  | |
| **after** | **String**| ID of item after which the collection should be returned | [optional] |
| **sortOrder** | **String**| Sort order of a list response. Use &#39;desc&#39; to reverse the default &#39;asc&#39; (ascending) sort order. | [optional] [enum: asc, desc] |

### Return type

[**List&lt;WebhooksOutgoingEventAttributes&gt;**](WebhooksOutgoingEventAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

