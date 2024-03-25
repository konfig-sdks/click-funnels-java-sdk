# ProductsPriceApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createVariantPrice**](ProductsPriceApi.md#createVariantPrice) | **POST** /products/{product_id}/prices | Create Price |
| [**getSinglePrice**](ProductsPriceApi.md#getSinglePrice) | **GET** /products/prices/{id} | Fetch Price |
| [**listForVariant**](ProductsPriceApi.md#listForVariant) | **GET** /products/{product_id}/prices | List Prices |
| [**updateSinglePrice**](ProductsPriceApi.md#updateSinglePrice) | **PUT** /products/prices/{id} | Update Price |


<a name="createVariantPrice"></a>
# **createVariantPrice**
> ProductsPriceAttributes createVariantPrice(productId, productsPriceCreateVariantPriceRequest).execute();

Create Price

Create a new price for a given variant

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsPriceApi;
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
    ProductsPriceParameters productsPrice = new HashMap();
    try {
      ProductsPriceAttributes result = client
              .productsPrice
              .createVariantPrice(productId)
              .productsPrice(productsPrice)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getVariantId());
      System.out.println(result.getName());
      System.out.println(result.getAmount());
      System.out.println(result.getCost());
      System.out.println(result.getCurrency());
      System.out.println(result.getDuration());
      System.out.println(result.getInterval());
      System.out.println(result.getTrialInterval());
      System.out.println(result.getTrialDuration());
      System.out.println(result.getTrialAmount());
      System.out.println(result.getSetupAmount());
      System.out.println(result.getSelfCancel());
      System.out.println(result.getSelfUpgrade());
      System.out.println(result.getSelfDowngrade());
      System.out.println(result.getSelfReactivate());
      System.out.println(result.getIntervalCount());
      System.out.println(result.getPaymentType());
      System.out.println(result.getVisible());
      System.out.println(result.getCompareAtAmount());
      System.out.println(result.getKey());
      System.out.println(result.getArchived());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#createVariantPrice");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductsPriceAttributes> response = client
              .productsPrice
              .createVariantPrice(productId)
              .productsPrice(productsPrice)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#createVariantPrice");
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
| **productsPriceCreateVariantPriceRequest** | [**ProductsPriceCreateVariantPriceRequest**](ProductsPriceCreateVariantPriceRequest.md)| Information about a new Price | |

### Return type

[**ProductsPriceAttributes**](ProductsPriceAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="getSinglePrice"></a>
# **getSinglePrice**
> ProductsPriceAttributes getSinglePrice(id).execute();

Fetch Price

Retrieve a single price

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsPriceApi;
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
      ProductsPriceAttributes result = client
              .productsPrice
              .getSinglePrice(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getVariantId());
      System.out.println(result.getName());
      System.out.println(result.getAmount());
      System.out.println(result.getCost());
      System.out.println(result.getCurrency());
      System.out.println(result.getDuration());
      System.out.println(result.getInterval());
      System.out.println(result.getTrialInterval());
      System.out.println(result.getTrialDuration());
      System.out.println(result.getTrialAmount());
      System.out.println(result.getSetupAmount());
      System.out.println(result.getSelfCancel());
      System.out.println(result.getSelfUpgrade());
      System.out.println(result.getSelfDowngrade());
      System.out.println(result.getSelfReactivate());
      System.out.println(result.getIntervalCount());
      System.out.println(result.getPaymentType());
      System.out.println(result.getVisible());
      System.out.println(result.getCompareAtAmount());
      System.out.println(result.getKey());
      System.out.println(result.getArchived());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#getSinglePrice");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductsPriceAttributes> response = client
              .productsPrice
              .getSinglePrice(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#getSinglePrice");
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

[**ProductsPriceAttributes**](ProductsPriceAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="listForVariant"></a>
# **listForVariant**
> List&lt;ProductsPriceAttributes&gt; listForVariant(productId).after(after).sortOrder(sortOrder).execute();

List Prices

List all prices for a given variant

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsPriceApi;
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
      List<ProductsPriceAttributes> result = client
              .productsPrice
              .listForVariant(productId)
              .after(after)
              .sortOrder(sortOrder)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#listForVariant");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<ProductsPriceAttributes>> response = client
              .productsPrice
              .listForVariant(productId)
              .after(after)
              .sortOrder(sortOrder)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#listForVariant");
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

[**List&lt;ProductsPriceAttributes&gt;**](ProductsPriceAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="updateSinglePrice"></a>
# **updateSinglePrice**
> ProductsPriceAttributes updateSinglePrice(id, productsPriceUpdateSinglePriceRequest).execute();

Update Price

Update a single price

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProductsPriceApi;
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
    ProductsPriceParameters productsPrice = new HashMap();
    try {
      ProductsPriceAttributes result = client
              .productsPrice
              .updateSinglePrice(id)
              .productsPrice(productsPrice)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getVariantId());
      System.out.println(result.getName());
      System.out.println(result.getAmount());
      System.out.println(result.getCost());
      System.out.println(result.getCurrency());
      System.out.println(result.getDuration());
      System.out.println(result.getInterval());
      System.out.println(result.getTrialInterval());
      System.out.println(result.getTrialDuration());
      System.out.println(result.getTrialAmount());
      System.out.println(result.getSetupAmount());
      System.out.println(result.getSelfCancel());
      System.out.println(result.getSelfUpgrade());
      System.out.println(result.getSelfDowngrade());
      System.out.println(result.getSelfReactivate());
      System.out.println(result.getIntervalCount());
      System.out.println(result.getPaymentType());
      System.out.println(result.getVisible());
      System.out.println(result.getCompareAtAmount());
      System.out.println(result.getKey());
      System.out.println(result.getArchived());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#updateSinglePrice");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProductsPriceAttributes> response = client
              .productsPrice
              .updateSinglePrice(id)
              .productsPrice(productsPrice)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsPriceApi#updateSinglePrice");
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
| **productsPriceUpdateSinglePriceRequest** | [**ProductsPriceUpdateSinglePriceRequest**](ProductsPriceUpdateSinglePriceRequest.md)| Information about updated fields in Price | |

### Return type

[**ProductsPriceAttributes**](ProductsPriceAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

