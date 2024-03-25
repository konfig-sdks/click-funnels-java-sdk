# CourseApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getById**](CourseApi.md#getById) | **GET** /courses/{id} | Fetch Course |
| [**listForWorkspace**](CourseApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/courses | List Courses |


<a name="getById"></a>
# **getById**
> CourseAttributes getById(id).execute();

Fetch Course

Retrieve a course

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CourseApi;
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
      CourseAttributes result = client
              .course
              .getById(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTitle());
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getPublishedAt());
      System.out.println(result.getRootSectionId());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getSharingFingerprint());
      System.out.println(result.getShowInCommunity());
      System.out.println(result.getShowToNonMembers());
      System.out.println(result.getUpgradeUrl());
      System.out.println(result.getRedirectToFullCourse());
      System.out.println(result.getUnauthorizedRedirectUrl());
      System.out.println(result.getCommentsEnabled());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling CourseApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CourseAttributes> response = client
              .course
              .getById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CourseApi#getById");
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

[**CourseAttributes**](CourseAttributes.md)

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
> List&lt;CourseAttributes&gt; listForWorkspace(workspaceId).after(after).sortOrder(sortOrder).execute();

List Courses

List courses for a team

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CourseApi;
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
      List<CourseAttributes> result = client
              .course
              .listForWorkspace(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CourseApi#listForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<CourseAttributes>> response = client
              .course
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
      System.err.println("Exception when calling CourseApi#listForWorkspace");
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

[**List&lt;CourseAttributes&gt;**](CourseAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

