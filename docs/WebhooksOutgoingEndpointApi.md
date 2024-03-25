# WebhooksOutgoingEndpointApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNew**](WebhooksOutgoingEndpointApi.md#createNew) | **POST** /workspaces/{workspace_id}/webhooks/outgoing/endpoints | Create Endpoint |
| [**get**](WebhooksOutgoingEndpointApi.md#get) | **GET** /webhooks/outgoing/endpoints/{id} | Fetch Endpoint |
| [**listEndpoints**](WebhooksOutgoingEndpointApi.md#listEndpoints) | **GET** /workspaces/{workspace_id}/webhooks/outgoing/endpoints | List Endpoints |
| [**updateEndpoint**](WebhooksOutgoingEndpointApi.md#updateEndpoint) | **PUT** /webhooks/outgoing/endpoints/{id} | Update Endpoint |


<a name="createNew"></a>
# **createNew**
> WebhooksOutgoingEndpointAttributes createNew(workspaceId, webhooksOutgoingEndpointCreateNewRequest).execute();

Create Endpoint

Add a new webhook endpoint to a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksOutgoingEndpointApi;
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
    WebhooksOutgoingEndpointParameters webhooksOutgoingEndpoint = new HashMap();
    try {
      WebhooksOutgoingEndpointAttributes result = client
              .webhooksOutgoingEndpoint
              .createNew(workspaceId)
              .webhooksOutgoingEndpoint(webhooksOutgoingEndpoint)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getUrl());
      System.out.println(result.getName());
      System.out.println(result.getEventTypeIds());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#createNew");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<WebhooksOutgoingEndpointAttributes> response = client
              .webhooksOutgoingEndpoint
              .createNew(workspaceId)
              .webhooksOutgoingEndpoint(webhooksOutgoingEndpoint)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#createNew");
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
| **webhooksOutgoingEndpointCreateNewRequest** | [**WebhooksOutgoingEndpointCreateNewRequest**](WebhooksOutgoingEndpointCreateNewRequest.md)| Information about a new Endpoint | |

### Return type

[**WebhooksOutgoingEndpointAttributes**](WebhooksOutgoingEndpointAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="get"></a>
# **get**
> WebhooksOutgoingEndpointAttributes get(id).execute();

Fetch Endpoint

Retrieve a webhook endpoint for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksOutgoingEndpointApi;
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
      WebhooksOutgoingEndpointAttributes result = client
              .webhooksOutgoingEndpoint
              .get(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getUrl());
      System.out.println(result.getName());
      System.out.println(result.getEventTypeIds());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#get");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<WebhooksOutgoingEndpointAttributes> response = client
              .webhooksOutgoingEndpoint
              .get(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#get");
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

[**WebhooksOutgoingEndpointAttributes**](WebhooksOutgoingEndpointAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="listEndpoints"></a>
# **listEndpoints**
> List&lt;WebhooksOutgoingEndpointAttributes&gt; listEndpoints(workspaceId).after(after).sortOrder(sortOrder).execute();

List Endpoints

List webhook endpoints for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksOutgoingEndpointApi;
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
      List<WebhooksOutgoingEndpointAttributes> result = client
              .webhooksOutgoingEndpoint
              .listEndpoints(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#listEndpoints");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<WebhooksOutgoingEndpointAttributes>> response = client
              .webhooksOutgoingEndpoint
              .listEndpoints(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#listEndpoints");
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

[**List&lt;WebhooksOutgoingEndpointAttributes&gt;**](WebhooksOutgoingEndpointAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="updateEndpoint"></a>
# **updateEndpoint**
> WebhooksOutgoingEndpointAttributes updateEndpoint(id, webhooksOutgoingEndpointUpdateEndpointRequest).execute();

Update Endpoint

Update a webhook endpoint for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksOutgoingEndpointApi;
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
    WebhooksOutgoingEndpointParameters webhooksOutgoingEndpoint = new HashMap();
    try {
      WebhooksOutgoingEndpointAttributes result = client
              .webhooksOutgoingEndpoint
              .updateEndpoint(id)
              .webhooksOutgoingEndpoint(webhooksOutgoingEndpoint)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getUrl());
      System.out.println(result.getName());
      System.out.println(result.getEventTypeIds());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#updateEndpoint");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<WebhooksOutgoingEndpointAttributes> response = client
              .webhooksOutgoingEndpoint
              .updateEndpoint(id)
              .webhooksOutgoingEndpoint(webhooksOutgoingEndpoint)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksOutgoingEndpointApi#updateEndpoint");
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
| **webhooksOutgoingEndpointUpdateEndpointRequest** | [**WebhooksOutgoingEndpointUpdateEndpointRequest**](WebhooksOutgoingEndpointUpdateEndpointRequest.md)| Information about updated fields in Endpoint | |

### Return type

[**WebhooksOutgoingEndpointAttributes**](WebhooksOutgoingEndpointAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

