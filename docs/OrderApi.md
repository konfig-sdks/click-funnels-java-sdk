# OrderApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getSingle**](OrderApi.md#getSingle) | **GET** /orders/{id} | Fetch Order |
| [**listOrders**](OrderApi.md#listOrders) | **GET** /workspaces/{workspace_id}/orders | List Orders |
| [**updateSpecific**](OrderApi.md#updateSpecific) | **PUT** /orders/{id} | Update Order |


<a name="getSingle"></a>
# **getSingle**
> OrderAttributes getSingle(id).execute();

Fetch Order

Retrieve a specific order in the current workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OrderApi;
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
      OrderAttributes result = client
              .order
              .getSingle(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getOrderNumber());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getContactId());
      System.out.println(result.getTotalAmount());
      System.out.println(result.getCurrency());
      System.out.println(result.getOriginationChannelId());
      System.out.println(result.getOriginationChannelType());
      System.out.println(result.getShippingAddressFirstName());
      System.out.println(result.getShippingAddressLastName());
      System.out.println(result.getShippingAddressOrganizationName());
      System.out.println(result.getShippingAddressPhoneNumber());
      System.out.println(result.getShippingAddressStreetOne());
      System.out.println(result.getShippingAddressStreetTwo());
      System.out.println(result.getShippingAddressCity());
      System.out.println(result.getShippingAddressRegion());
      System.out.println(result.getShippingAddressCountry());
      System.out.println(result.getShippingAddressPostalCode());
      System.out.println(result.getBillingAddressStreetOne());
      System.out.println(result.getBillingAddressStreetTwo());
      System.out.println(result.getBillingAddressCity());
      System.out.println(result.getBillingAddressRegion());
      System.out.println(result.getBillingAddressCountry());
      System.out.println(result.getBillingAddressPostalCode());
      System.out.println(result.getPageId());
      System.out.println(result.getNotes());
      System.out.println(result.getInTrial());
      System.out.println(result.getBillingStatus());
      System.out.println(result.getServiceStatus());
      System.out.println(result.getOrderType());
      System.out.println(result.getNextChargeAt());
      System.out.println(result.getTaxAmount());
      System.out.println(result.getTrialEndAt());
      System.out.println(result.getBillingPaymentMethodId());
      System.out.println(result.getFunnelName());
      System.out.println(result.getTagIds());
      System.out.println(result.getDiscountIds());
      System.out.println(result.getActivatedAt());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getPhoneNumber());
      System.out.println(result.getPageName());
      System.out.println(result.getOriginationChannelName());
      System.out.println(result.getOrderPage());
      System.out.println(result.getContact());
      System.out.println(result.getContactGroups());
      System.out.println(result.getWorkspace());
      System.out.println(result.getSegments());
      System.out.println(result.getLineItems());
      System.out.println(result.getPreviousLineItem());
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#getSingle");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<OrderAttributes> response = client
              .order
              .getSingle(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#getSingle");
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

[**OrderAttributes**](OrderAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="listOrders"></a>
# **listOrders**
> List&lt;OrderAttributes&gt; listOrders(workspaceId).after(after).sortOrder(sortOrder).filter(filter).execute();

List Orders

List all orders for the current workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OrderApi;
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
    OrderListOrdersFilterParameter filter = new HashMap(); // Filtering  - Keep in mind that depending on the tools that you use, you might run into different situations where additional encoding is needed. For example:     - You might need to encode `filter[id]=1` as `filter%5Bid%5D=1` or use special options in your tools of choice to do it for you (like `g` in CURL).     -  Special URL characters like `%`, `+`, or unicode characters in emails (like Chinese characters) will need additional encoding.  
    try {
      List<OrderAttributes> result = client
              .order
              .listOrders(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .filter(filter)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#listOrders");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<OrderAttributes>> response = client
              .order
              .listOrders(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .filter(filter)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#listOrders");
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
| **filter** | [**OrderListOrdersFilterParameter**](.md)| Filtering  - Keep in mind that depending on the tools that you use, you might run into different situations where additional encoding is needed. For example:     - You might need to encode &#x60;filter[id]&#x3D;1&#x60; as &#x60;filter%5Bid%5D&#x3D;1&#x60; or use special options in your tools of choice to do it for you (like &#x60;g&#x60; in CURL).     -  Special URL characters like &#x60;%&#x60;, &#x60;+&#x60;, or unicode characters in emails (like Chinese characters) will need additional encoding.   | [optional] |

### Return type

[**List&lt;OrderAttributes&gt;**](OrderAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="updateSpecific"></a>
# **updateSpecific**
> OrderAttributes updateSpecific(id, orderUpdateSpecificRequest).execute();

Update Order

Update a specific order in the current workspace

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.ClickFunnels;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OrderApi;
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
    OrderParameters order = new HashMap();
    try {
      OrderAttributes result = client
              .order
              .updateSpecific(id)
              .order(order)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getPublicId());
      System.out.println(result.getOrderNumber());
      System.out.println(result.getWorkspaceId());
      System.out.println(result.getContactId());
      System.out.println(result.getTotalAmount());
      System.out.println(result.getCurrency());
      System.out.println(result.getOriginationChannelId());
      System.out.println(result.getOriginationChannelType());
      System.out.println(result.getShippingAddressFirstName());
      System.out.println(result.getShippingAddressLastName());
      System.out.println(result.getShippingAddressOrganizationName());
      System.out.println(result.getShippingAddressPhoneNumber());
      System.out.println(result.getShippingAddressStreetOne());
      System.out.println(result.getShippingAddressStreetTwo());
      System.out.println(result.getShippingAddressCity());
      System.out.println(result.getShippingAddressRegion());
      System.out.println(result.getShippingAddressCountry());
      System.out.println(result.getShippingAddressPostalCode());
      System.out.println(result.getBillingAddressStreetOne());
      System.out.println(result.getBillingAddressStreetTwo());
      System.out.println(result.getBillingAddressCity());
      System.out.println(result.getBillingAddressRegion());
      System.out.println(result.getBillingAddressCountry());
      System.out.println(result.getBillingAddressPostalCode());
      System.out.println(result.getPageId());
      System.out.println(result.getNotes());
      System.out.println(result.getInTrial());
      System.out.println(result.getBillingStatus());
      System.out.println(result.getServiceStatus());
      System.out.println(result.getOrderType());
      System.out.println(result.getNextChargeAt());
      System.out.println(result.getTaxAmount());
      System.out.println(result.getTrialEndAt());
      System.out.println(result.getBillingPaymentMethodId());
      System.out.println(result.getFunnelName());
      System.out.println(result.getTagIds());
      System.out.println(result.getDiscountIds());
      System.out.println(result.getActivatedAt());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getUpdatedAt());
      System.out.println(result.getPhoneNumber());
      System.out.println(result.getPageName());
      System.out.println(result.getOriginationChannelName());
      System.out.println(result.getOrderPage());
      System.out.println(result.getContact());
      System.out.println(result.getContactGroups());
      System.out.println(result.getWorkspace());
      System.out.println(result.getSegments());
      System.out.println(result.getLineItems());
      System.out.println(result.getPreviousLineItem());
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#updateSpecific");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<OrderAttributes> response = client
              .order
              .updateSpecific(id)
              .order(order)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#updateSpecific");
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
| **orderUpdateSpecificRequest** | [**OrderUpdateSpecificRequest**](OrderUpdateSpecificRequest.md)| Information about updated fields in Order | |

### Return type

[**OrderAttributes**](OrderAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

