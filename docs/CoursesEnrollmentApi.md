# CoursesEnrollmentApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewEnrollment**](CoursesEnrollmentApi.md#createNewEnrollment) | **POST** /courses/{course_id}/enrollments | Create Enrollment |
| [**getById**](CoursesEnrollmentApi.md#getById) | **GET** /courses/enrollments/{id} | Fetch Enrollment |
| [**list**](CoursesEnrollmentApi.md#list) | **GET** /courses/{course_id}/enrollments | List Enrollments |
| [**updateSpecificEnrollment**](CoursesEnrollmentApi.md#updateSpecificEnrollment) | **PUT** /courses/enrollments/{id} | Update Enrollment |


<a name="createNewEnrollment"></a>
# **createNewEnrollment**
> CoursesEnrollmentAttributes createNewEnrollment(courseId, coursesEnrollmentCreateNewEnrollmentRequest).execute();

Create Enrollment

Add a new enrollment

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CoursesEnrollmentApi;
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
    Integer courseId = 56;
    CoursesEnrollmentParameters coursesEnrollment = new HashMap();
    try {
      CoursesEnrollmentAttributes result = client
              .coursesEnrollment
              .createNewEnrollment(courseId)
              .coursesEnrollment(coursesEnrollment)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getContactId());
      System.out.println(result.getCourseId());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getSuspended());
      System.out.println(result.getSuspensionReason());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getOriginationSourceType());
      System.out.println(result.getOriginationSourceId());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#createNewEnrollment");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CoursesEnrollmentAttributes> response = client
              .coursesEnrollment
              .createNewEnrollment(courseId)
              .coursesEnrollment(coursesEnrollment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#createNewEnrollment");
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
| **courseId** | **Integer**|  | |
| **coursesEnrollmentCreateNewEnrollmentRequest** | [**CoursesEnrollmentCreateNewEnrollmentRequest**](CoursesEnrollmentCreateNewEnrollmentRequest.md)| Information about a new Enrollment | |

### Return type

[**CoursesEnrollmentAttributes**](CoursesEnrollmentAttributes.md)

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
> CoursesEnrollmentAttributes getById(id).execute();

Fetch Enrollment

Retrieve an enrollment

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CoursesEnrollmentApi;
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
      CoursesEnrollmentAttributes result = client
              .coursesEnrollment
              .getById(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getContactId());
      System.out.println(result.getCourseId());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getSuspended());
      System.out.println(result.getSuspensionReason());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getOriginationSourceType());
      System.out.println(result.getOriginationSourceId());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CoursesEnrollmentAttributes> response = client
              .coursesEnrollment
              .getById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#getById");
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

[**CoursesEnrollmentAttributes**](CoursesEnrollmentAttributes.md)

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
> List&lt;CoursesEnrollmentAttributes&gt; list(courseId).after(after).sortOrder(sortOrder).execute();

List Enrollments

List enrollments for a course

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CoursesEnrollmentApi;
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
    Integer courseId = 56;
    String after = "after_example"; // ID of item after which the collection should be returned
    String sortOrder = "asc"; // Sort order of a list response. Use 'desc' to reverse the default 'asc' (ascending) sort order.
    try {
      List<CoursesEnrollmentAttributes> result = client
              .coursesEnrollment
              .list(courseId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<CoursesEnrollmentAttributes>> response = client
              .coursesEnrollment
              .list(courseId)
              .after(after)
              .sortOrder(sortOrder)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#list");
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
| **courseId** | **Integer**|  | |
| **after** | **String**| ID of item after which the collection should be returned | [optional] |
| **sortOrder** | **String**| Sort order of a list response. Use &#39;desc&#39; to reverse the default &#39;asc&#39; (ascending) sort order. | [optional] [enum: asc, desc] |

### Return type

[**List&lt;CoursesEnrollmentAttributes&gt;**](CoursesEnrollmentAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="updateSpecificEnrollment"></a>
# **updateSpecificEnrollment**
> CoursesEnrollmentAttributes updateSpecificEnrollment(id, coursesEnrollmentUpdateSpecificEnrollmentRequest).execute();

Update Enrollment

Update an enrollment

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CoursesEnrollmentApi;
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
    CoursesEnrollmentParameters coursesEnrollment = new HashMap();
    try {
      CoursesEnrollmentAttributes result = client
              .coursesEnrollment
              .updateSpecificEnrollment(id)
              .coursesEnrollment(coursesEnrollment)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getContactId());
      System.out.println(result.getCourseId());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getSuspended());
      System.out.println(result.getSuspensionReason());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getOriginationSourceType());
      System.out.println(result.getOriginationSourceId());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#updateSpecificEnrollment");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CoursesEnrollmentAttributes> response = client
              .coursesEnrollment
              .updateSpecificEnrollment(id)
              .coursesEnrollment(coursesEnrollment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CoursesEnrollmentApi#updateSpecificEnrollment");
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
| **coursesEnrollmentUpdateSpecificEnrollmentRequest** | [**CoursesEnrollmentUpdateSpecificEnrollmentRequest**](CoursesEnrollmentUpdateSpecificEnrollmentRequest.md)| Information about updated fields in Enrollment | |

### Return type

[**CoursesEnrollmentAttributes**](CoursesEnrollmentAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

