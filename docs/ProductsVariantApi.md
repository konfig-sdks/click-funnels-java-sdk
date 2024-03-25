# ProductsVariantApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewVariant**](ProductsVariantApi.md#createNewVariant) | **POST** /products/{product_id}/variants | Create Variant |
| [**getSingle**](ProductsVariantApi.md#getSingle) | **GET** /products/variants/{id} | Fetch Variant |
| [**list**](ProductsVariantApi.md#list) | **GET** /products/{product_id}/variants | List Variants |
| [**updateSingle**](ProductsVariantApi.md#updateSingle) | **PUT** /products/variants/{id} | Update Variant |


<a name="createNewVariant"></a>
# **createNewVariant**
> ProductsVariantAttributes createNewVariant(productId, productsVariantCreateNewVariantRequest).execute();

Create Variant

Create a new variant for a product

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsVariantApi;
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
    Integer productId = 56;
    ProductsVariantParameters productsVariant = new HashMap();
    try {
      ProductsVariantAttributes result = client
              .productsVariant
              .createNewVariant(productId)
              .productsVariant(productsVariant)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getProductId());
      System.out.println(result.getName());
      System.out.println(result.getSku());
      System.out.println(result.getProductType());
      System.out.println(result.getPropertiesValueIds());
      System.out.println(result.getOutOfStockSales());
      System.out.println(result.getWeight());
      System.out.println(result.getWeightUnit());
      System.out.println(result.getHeight());
      System.out.println(result.getWidth());
      System.out.println(result.getLength());
      System.out.println(result.getDimensionsUnit());
      System.out.println(result.getQuantity());
      System.out.println(result.getTagIds());
      System.out.println(result.getTaxCategoryId());
      System.out.println(result.getAssetIds());
      System.out.println(result.getTaxable());
      System.out.println(result.getTrackQuantity());
      System.out.println(result.getArchived());
      System.out.println(result.getVisible());
      System.out.println(result.getPriceIds());
      System.out.println(result.getFulfillmentRequired());
      System.out.println(result.getCountryOfManufactureId());
      System.out.println(result.getImageIds());
      System.out.println(result.getFulfillmentsLocationIds());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getDefault());
      System.out.println(result.getPropertiesValues());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#createNewVariant");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductsVariantAttributes> response = client
              .productsVariant
              .createNewVariant(productId)
              .productsVariant(productsVariant)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#createNewVariant");
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
| **productId** | **Integer**|  | |
| **productsVariantCreateNewVariantRequest** | [**ProductsVariantCreateNewVariantRequest**](ProductsVariantCreateNewVariantRequest.md)| Information about a new Variant | |

### Return type

[**ProductsVariantAttributes**](ProductsVariantAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="getSingle"></a>
# **getSingle**
> ProductsVariantAttributes getSingle(id).execute();

Fetch Variant

Retrieve a single variant

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsVariantApi;
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
      ProductsVariantAttributes result = client
              .productsVariant
              .getSingle(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getProductId());
      System.out.println(result.getName());
      System.out.println(result.getSku());
      System.out.println(result.getProductType());
      System.out.println(result.getPropertiesValueIds());
      System.out.println(result.getOutOfStockSales());
      System.out.println(result.getWeight());
      System.out.println(result.getWeightUnit());
      System.out.println(result.getHeight());
      System.out.println(result.getWidth());
      System.out.println(result.getLength());
      System.out.println(result.getDimensionsUnit());
      System.out.println(result.getQuantity());
      System.out.println(result.getTagIds());
      System.out.println(result.getTaxCategoryId());
      System.out.println(result.getAssetIds());
      System.out.println(result.getTaxable());
      System.out.println(result.getTrackQuantity());
      System.out.println(result.getArchived());
      System.out.println(result.getVisible());
      System.out.println(result.getPriceIds());
      System.out.println(result.getFulfillmentRequired());
      System.out.println(result.getCountryOfManufactureId());
      System.out.println(result.getImageIds());
      System.out.println(result.getFulfillmentsLocationIds());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getDefault());
      System.out.println(result.getPropertiesValues());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#getSingle");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductsVariantAttributes> response = client
              .productsVariant
              .getSingle(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#getSingle");
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

[**ProductsVariantAttributes**](ProductsVariantAttributes.md)

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
> List&lt;ProductsVariantAttributes&gt; list(productId).after(after).sortOrder(sortOrder).execute();

List Variants

List variants for a product

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsVariantApi;
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
    Integer productId = 56;
    String after = "after_example"; // ID of item after which the collection should be returned
    String sortOrder = "asc"; // Sort order of a list response. Use 'desc' to reverse the default 'asc' (ascending) sort order.
    try {
      List<ProductsVariantAttributes> result = client
              .productsVariant
              .list(productId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<ProductsVariantAttributes>> response = client
              .productsVariant
              .list(productId)
              .after(after)
              .sortOrder(sortOrder)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#list");
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
| **productId** | **Integer**|  | |
| **after** | **String**| ID of item after which the collection should be returned | [optional] |
| **sortOrder** | **String**| Sort order of a list response. Use &#39;desc&#39; to reverse the default &#39;asc&#39; (ascending) sort order. | [optional] [enum: asc, desc] |

### Return type

[**List&lt;ProductsVariantAttributes&gt;**](ProductsVariantAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="updateSingle"></a>
# **updateSingle**
> ProductsVariantAttributes updateSingle(id, productsVariantUpdateSingleRequest).execute();

Update Variant

Update a single variant

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsVariantApi;
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
    ProductsVariantParameters productsVariant = new HashMap();
    try {
      ProductsVariantAttributes result = client
              .productsVariant
              .updateSingle(id)
              .productsVariant(productsVariant)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getProductId());
      System.out.println(result.getName());
      System.out.println(result.getSku());
      System.out.println(result.getProductType());
      System.out.println(result.getPropertiesValueIds());
      System.out.println(result.getOutOfStockSales());
      System.out.println(result.getWeight());
      System.out.println(result.getWeightUnit());
      System.out.println(result.getHeight());
      System.out.println(result.getWidth());
      System.out.println(result.getLength());
      System.out.println(result.getDimensionsUnit());
      System.out.println(result.getQuantity());
      System.out.println(result.getTagIds());
      System.out.println(result.getTaxCategoryId());
      System.out.println(result.getAssetIds());
      System.out.println(result.getTaxable());
      System.out.println(result.getTrackQuantity());
      System.out.println(result.getArchived());
      System.out.println(result.getVisible());
      System.out.println(result.getPriceIds());
      System.out.println(result.getFulfillmentRequired());
      System.out.println(result.getCountryOfManufactureId());
      System.out.println(result.getImageIds());
      System.out.println(result.getFulfillmentsLocationIds());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getDefault());
      System.out.println(result.getPropertiesValues());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#updateSingle");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductsVariantAttributes> response = client
              .productsVariant
              .updateSingle(id)
              .productsVariant(productsVariant)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsVariantApi#updateSingle");
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
| **productsVariantUpdateSingleRequest** | [**ProductsVariantUpdateSingleRequest**](ProductsVariantUpdateSingleRequest.md)| Information about updated fields in Variant | |

### Return type

[**ProductsVariantAttributes**](ProductsVariantAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

