# FormsSubmissionApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewSubmission**](FormsSubmissionApi.md#createNewSubmission) | **POST** /forms/{form_id}/submissions | Create Submission |
| [**getById**](FormsSubmissionApi.md#getById) | **GET** /forms/submissions/{id} | Fetch Submission |
| [**list**](FormsSubmissionApi.md#list) | **GET** /forms/{form_id}/submissions | List Submissions |
| [**remove**](FormsSubmissionApi.md#remove) | **DELETE** /forms/submissions/{id} | Remove Submission |
| [**updateSubmission**](FormsSubmissionApi.md#updateSubmission) | **PUT** /forms/submissions/{id} | Update Submission |


<a name="createNewSubmission"></a>
# **createNewSubmission**
> FormsSubmissionAttributes createNewSubmission(formId, formsSubmissionCreateNewSubmissionRequest).execute();

Create Submission

Add a new submission to a form

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FormsSubmissionApi;
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
    Integer formId = 56;
    FormsSubmissionParameters formsSubmission = new HashMap();
    try {
      FormsSubmissionAttributes result = client
              .formsSubmission
              .createNewSubmission(formId)
              .formsSubmission(formsSubmission)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getFormId());
      System.out.println(result.getContactId());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#createNewSubmission");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FormsSubmissionAttributes> response = client
              .formsSubmission
              .createNewSubmission(formId)
              .formsSubmission(formsSubmission)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#createNewSubmission");
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
| **formId** | **Integer**|  | |
| **formsSubmissionCreateNewSubmissionRequest** | [**FormsSubmissionCreateNewSubmissionRequest**](FormsSubmissionCreateNewSubmissionRequest.md)| Information about a new Submission | |

### Return type

[**FormsSubmissionAttributes**](FormsSubmissionAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="getById"></a>
# **getById**
> FormsSubmissionAttributes getById(id).execute();

Fetch Submission

Retrieve a submission for a form

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FormsSubmissionApi;
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
      FormsSubmissionAttributes result = client
              .formsSubmission
              .getById(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getFormId());
      System.out.println(result.getContactId());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FormsSubmissionAttributes> response = client
              .formsSubmission
              .getById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#getById");
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

[**FormsSubmissionAttributes**](FormsSubmissionAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="list"></a>
# **list**
> List&lt;FormsSubmissionAttributes&gt; list(formId).after(after).sortOrder(sortOrder).execute();

List Submissions

List submissions for a form

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FormsSubmissionApi;
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
    Integer formId = 56;
    String after = "after_example"; // ID of item after which the collection should be returned
    String sortOrder = "asc"; // Sort order of a list response. Use 'desc' to reverse the default 'asc' (ascending) sort order.
    try {
      List<FormsSubmissionAttributes> result = client
              .formsSubmission
              .list(formId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<FormsSubmissionAttributes>> response = client
              .formsSubmission
              .list(formId)
              .after(after)
              .sortOrder(sortOrder)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#list");
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
| **formId** | **Integer**|  | |
| **after** | **String**| ID of item after which the collection should be returned | [optional] |
| **sortOrder** | **String**| Sort order of a list response. Use &#39;desc&#39; to reverse the default &#39;asc&#39; (ascending) sort order. | [optional] [enum: asc, desc] |

### Return type

[**List&lt;FormsSubmissionAttributes&gt;**](FormsSubmissionAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="remove"></a>
# **remove**
> remove(id).execute();

Remove Submission

Delete a submission for a form

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FormsSubmissionApi;
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
      client
              .formsSubmission
              .remove(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#remove");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .formsSubmission
              .remove(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#remove");
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

null (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | No Content |  -  |

<a name="updateSubmission"></a>
# **updateSubmission**
> FormsSubmissionAttributes updateSubmission(id, formsSubmissionUpdateSubmissionRequest).execute();

Update Submission

Update a submission for a form

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FormsSubmissionApi;
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
    FormsSubmissionParameters formsSubmission = new HashMap();
    try {
      FormsSubmissionAttributes result = client
              .formsSubmission
              .updateSubmission(id)
              .formsSubmission(formsSubmission)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getFormId());
      System.out.println(result.getContactId());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#updateSubmission");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FormsSubmissionAttributes> response = client
              .formsSubmission
              .updateSubmission(id)
              .formsSubmission(formsSubmission)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FormsSubmissionApi#updateSubmission");
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
| **formsSubmissionUpdateSubmissionRequest** | [**FormsSubmissionUpdateSubmissionRequest**](FormsSubmissionUpdateSubmissionRequest.md)| Information about updated fields in Submission | |

### Return type

[**FormsSubmissionAttributes**](FormsSubmissionAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

