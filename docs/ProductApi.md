# ProductApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNewToWorkspace**](ProductApi.md#addNewToWorkspace) | **POST** /workspaces/{workspace_id}/products | Create Product |
| [**archiveProduct**](ProductApi.md#archiveProduct) | **POST** /products/{id}/archive | Archive a Product |
| [**getForWorkspace**](ProductApi.md#getForWorkspace) | **GET** /products/{id} | Fetch Product |
| [**listForWorkspace**](ProductApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/products | List Products |
| [**unarchiveById**](ProductApi.md#unarchiveById) | **POST** /products/{id}/unarchive | Unarchive a Product |
| [**updateForWorkspace**](ProductApi.md#updateForWorkspace) | **PUT** /products/{id} | Update Product |


<a name="addNewToWorkspace"></a>
# **addNewToWorkspace**
> ProductAttributes addNewToWorkspace(workspaceId, productAddNewToWorkspaceRequest).execute();

Create Product

Add a new product to a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductApi;
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
    ProductParameters product = new HashMap();
    try {
      ProductAttributes result = client
              .product
              .addNewToWorkspace(workspaceId)
              .product(product)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getName());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getArchived());
      System.out.println(result.getVisibleInStore());
      System.out.println(result.getVisibleInCustomerCenter());
      System.out.println(result.getImageId());
      System.out.println(result.getSeoTitle());
      System.out.println(result.getSeoDescription());
      System.out.println(result.getSeoImageId());
      System.out.println(result.getCommissionable());
      System.out.println(result.getImageIds());
      System.out.println(result.getDefaultVariantId());
      System.out.println(result.getVariantIds());
      System.out.println(result.getPriceIds());
      System.out.println(result.getTagIds());
      System.out.println(result.getRedirectFunnelId());
      System.out.println(result.getCancellationFunnelUrl());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getVariantProperties());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#addNewToWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductAttributes> response = client
              .product
              .addNewToWorkspace(workspaceId)
              .product(product)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#addNewToWorkspace");
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
| **productAddNewToWorkspaceRequest** | [**ProductAddNewToWorkspaceRequest**](ProductAddNewToWorkspaceRequest.md)| Information about a new Product | |

### Return type

[**ProductAttributes**](ProductAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="archiveProduct"></a>
# **archiveProduct**
> ProductAttributes archiveProduct(id).execute();

Archive a Product

This will archive a Product. A product can only be archived if it&#39;s not in the \&quot;live\&quot; state.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductApi;
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
      ProductAttributes result = client
              .product
              .archiveProduct(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getName());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getArchived());
      System.out.println(result.getVisibleInStore());
      System.out.println(result.getVisibleInCustomerCenter());
      System.out.println(result.getImageId());
      System.out.println(result.getSeoTitle());
      System.out.println(result.getSeoDescription());
      System.out.println(result.getSeoImageId());
      System.out.println(result.getCommissionable());
      System.out.println(result.getImageIds());
      System.out.println(result.getDefaultVariantId());
      System.out.println(result.getVariantIds());
      System.out.println(result.getPriceIds());
      System.out.println(result.getTagIds());
      System.out.println(result.getRedirectFunnelId());
      System.out.println(result.getCancellationFunnelUrl());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getVariantProperties());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#archiveProduct");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductAttributes> response = client
              .product
              .archiveProduct(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#archiveProduct");
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

[**ProductAttributes**](ProductAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | Not Found |  -  |
| **409** | Conflict - occurs if product is already archived. |  -  |
| **422** | Unprocessable Entity |  -  |

<a name="getForWorkspace"></a>
# **getForWorkspace**
> ProductAttributes getForWorkspace(id).execute();

Fetch Product

Retrieve a product for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductApi;
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
      ProductAttributes result = client
              .product
              .getForWorkspace(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getName());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getArchived());
      System.out.println(result.getVisibleInStore());
      System.out.println(result.getVisibleInCustomerCenter());
      System.out.println(result.getImageId());
      System.out.println(result.getSeoTitle());
      System.out.println(result.getSeoDescription());
      System.out.println(result.getSeoImageId());
      System.out.println(result.getCommissionable());
      System.out.println(result.getImageIds());
      System.out.println(result.getDefaultVariantId());
      System.out.println(result.getVariantIds());
      System.out.println(result.getPriceIds());
      System.out.println(result.getTagIds());
      System.out.println(result.getRedirectFunnelId());
      System.out.println(result.getCancellationFunnelUrl());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getVariantProperties());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#getForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductAttributes> response = client
              .product
              .getForWorkspace(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#getForWorkspace");
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

[**ProductAttributes**](ProductAttributes.md)

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
> List&lt;ProductAttributes&gt; listForWorkspace(workspaceId).after(after).sortOrder(sortOrder).execute();

List Products

List products for a workspace. All products are listed regardless of &#x60;archived&#x60; state.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductApi;
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
      List<ProductAttributes> result = client
              .product
              .listForWorkspace(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#listForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<ProductAttributes>> response = client
              .product
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
      System.err.println("Exception when calling ProductApi#listForWorkspace");
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

[**List&lt;ProductAttributes&gt;**](ProductAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="unarchiveById"></a>
# **unarchiveById**
> ProductAttributes unarchiveById(id).execute();

Unarchive a Product

This will unarchive a Product.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductApi;
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
      ProductAttributes result = client
              .product
              .unarchiveById(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getName());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getArchived());
      System.out.println(result.getVisibleInStore());
      System.out.println(result.getVisibleInCustomerCenter());
      System.out.println(result.getImageId());
      System.out.println(result.getSeoTitle());
      System.out.println(result.getSeoDescription());
      System.out.println(result.getSeoImageId());
      System.out.println(result.getCommissionable());
      System.out.println(result.getImageIds());
      System.out.println(result.getDefaultVariantId());
      System.out.println(result.getVariantIds());
      System.out.println(result.getPriceIds());
      System.out.println(result.getTagIds());
      System.out.println(result.getRedirectFunnelId());
      System.out.println(result.getCancellationFunnelUrl());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getVariantProperties());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#unarchiveById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductAttributes> response = client
              .product
              .unarchiveById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#unarchiveById");
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

[**ProductAttributes**](ProductAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | Not Found |  -  |
| **409** | Conflict - occurs if product is not archived. |  -  |
| **422** | Unprocessable Entity |  -  |

<a name="updateForWorkspace"></a>
# **updateForWorkspace**
> ProductAttributes updateForWorkspace(id, productUpdateForWorkspaceRequest).execute();

Update Product

Update a product for a workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductApi;
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
    ProductParameters product = new HashMap();
    try {
      ProductAttributes result = client
              .product
              .updateForWorkspace(id)
              .product(product)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getName());
      System.out.println(result.getCurrentPath());
      System.out.println(result.getArchived());
      System.out.println(result.getVisibleInStore());
      System.out.println(result.getVisibleInCustomerCenter());
      System.out.println(result.getImageId());
      System.out.println(result.getSeoTitle());
      System.out.println(result.getSeoDescription());
      System.out.println(result.getSeoImageId());
      System.out.println(result.getCommissionable());
      System.out.println(result.getImageIds());
      System.out.println(result.getDefaultVariantId());
      System.out.println(result.getVariantIds());
      System.out.println(result.getPriceIds());
      System.out.println(result.getTagIds());
      System.out.println(result.getRedirectFunnelId());
      System.out.println(result.getCancellationFunnelUrl());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getVariantProperties());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#updateForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductAttributes> response = client
              .product
              .updateForWorkspace(id)
              .product(product)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#updateForWorkspace");
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
| **productUpdateForWorkspaceRequest** | [**ProductUpdateForWorkspaceRequest**](ProductUpdateForWorkspaceRequest.md)| Information about updated fields in Product | |

### Return type

[**ProductAttributes**](ProductAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

