# ContactApi

All URIs are relative to *https://myworkspace.myclickfunnels.com/api/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewContact**](ContactApi.md#createNewContact) | **POST** /workspaces/{workspace_id}/contacts | Create Contact |
| [**getContactById**](ContactApi.md#getContactById) | **GET** /contacts/{id} | Fetch Contact |
| [**listForWorkspace**](ContactApi.md#listForWorkspace) | **GET** /workspaces/{workspace_id}/contacts | List Contacts |
| [**redactPersonallyIdentifiable**](ContactApi.md#redactPersonallyIdentifiable) | **DELETE** /contacts/{id}/gdpr_destroy | Redact personally identifiable information of a Contact |
| [**removeById**](ContactApi.md#removeById) | **DELETE** /contacts/{id} | Remove Contact |
| [**updateContactById**](ContactApi.md#updateContactById) | **PUT** /contacts/{id} | Update Contact |
| [**upsert**](ContactApi.md#upsert) | **POST** /workspaces/{workspace_id}/contacts/upsert | Upsert a Contact |


<a name="createNewContact"></a>
# **createNewContact**
> ContactAttributes createNewContact(workspaceId, contactCreateNewContactRequest).execute();

Create Contact

Add a new contact to the workspace

### Example
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

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **workspaceId** | **Integer**|  | |
| **contactCreateNewContactRequest** | [**ContactCreateNewContactRequest**](ContactCreateNewContactRequest.md)| Information about a new Contact | |

### Return type

[**ContactAttributes**](ContactAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="getContactById"></a>
# **getContactById**
> ContactAttributes getContactById(id).execute();

Fetch Contact

Retrieve a contact

### Example
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
    String id = "id_example";
    try {
      ContactAttributes result = client
              .contact
              .getContactById(id)
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
      System.err.println("Exception when calling ContactApi#getContactById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContactAttributes> response = client
              .contact
              .getContactById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#getContactById");
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

[**ContactAttributes**](ContactAttributes.md)

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
> List&lt;ContactAttributes&gt; listForWorkspace(workspaceId).after(after).sortOrder(sortOrder).filter(filter).execute();

List Contacts

List contacts for the given workspace. By default, only identified contacts are shown so you won&amp;#39;t see anonymous or GDPR-redacted contacts.

### Example
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
    String after = "after_example"; // ID of item after which the collection should be returned
    String sortOrder = "asc"; // Sort order of a list response. Use 'desc' to reverse the default 'asc' (ascending) sort order.
    ContactListForWorkspaceFilterParameter filter = new HashMap(); // Filtering  - Keep in mind that depending on the tools that you use, you might run into different situations where additional encoding is needed. For example:     - You might need to encode `filter[id]=1` as `filter%5Bid%5D=1` or use special options in your tools of choice to do it for you (like `g` in CURL).     -  Special URL characters like `%`, `+`, or unicode characters in emails (like Chinese characters) will need additional encoding.  
    try {
      List<ContactAttributes> result = client
              .contact
              .listForWorkspace(workspaceId)
              .after(after)
              .sortOrder(sortOrder)
              .filter(filter)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#listForWorkspace");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<ContactAttributes>> response = client
              .contact
              .listForWorkspace(workspaceId)
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
      System.err.println("Exception when calling ContactApi#listForWorkspace");
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
| **filter** | [**ContactListForWorkspaceFilterParameter**](.md)| Filtering  - Keep in mind that depending on the tools that you use, you might run into different situations where additional encoding is needed. For example:     - You might need to encode &#x60;filter[id]&#x3D;1&#x60; as &#x60;filter%5Bid%5D&#x3D;1&#x60; or use special options in your tools of choice to do it for you (like &#x60;g&#x60; in CURL).     -  Special URL characters like &#x60;%&#x60;, &#x60;+&#x60;, or unicode characters in emails (like Chinese characters) will need additional encoding.   | [optional] |

### Return type

[**List&lt;ContactAttributes&gt;**](ContactAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  * Pagination-Next -  <br>  * Link -  <br>  |

<a name="redactPersonallyIdentifiable"></a>
# **redactPersonallyIdentifiable**
> redactPersonallyIdentifiable(id).execute();

Redact personally identifiable information of a Contact

This will destroy all personally identifiable information for a contact, including their name and phone number. This cannot be undone.

### Example
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
    String id = "id_example";
    try {
      client
              .contact
              .redactPersonallyIdentifiable(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#redactPersonallyIdentifiable");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .contact
              .redactPersonallyIdentifiable(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#redactPersonallyIdentifiable");
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
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | Not Found |  -  |

<a name="removeById"></a>
# **removeById**
> removeById(id).execute();

Remove Contact

Delete a contact

### Example
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
    String id = "id_example";
    try {
      client
              .contact
              .removeById(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#removeById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .contact
              .removeById(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#removeById");
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

<a name="updateContactById"></a>
# **updateContactById**
> ContactAttributes updateContactById(id, contactUpdateContactByIdRequest).execute();

Update Contact

Update a contact

### Example
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
    String id = "id_example";
    ContactParameters contact = new HashMap();
    try {
      ContactAttributes result = client
              .contact
              .updateContactById(id)
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
      System.err.println("Exception when calling ContactApi#updateContactById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContactAttributes> response = client
              .contact
              .updateContactById(id)
              .contact(contact)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#updateContactById");
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
| **contactUpdateContactByIdRequest** | [**ContactUpdateContactByIdRequest**](ContactUpdateContactByIdRequest.md)| Information about updated fields in Contact | |

### Return type

[**ContactAttributes**](ContactAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="upsert"></a>
# **upsert**
> List&lt;ContactAttributes&gt; upsert(workspaceId, contactUpsertRequest).execute();

Upsert a Contact

Creates or updates a Contact, matching on the email address. If the Contact does not exist, it will be created. If the Contact does exist, it will be updated. It is not possible to delete a Contact via this endpoint. It is not possible to reset properties of a Contact by passing empty values. E.g. passing &#x60;null&#x60; for &#x60;first_name&#x60; or an empty array for &#x60;tag_ids&#x60; won&#39;t update previous values. To do that you would instead need to use the &#x60;Update Contact&#x60; endpoint.

### Example
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
    String workspaceId = "workspaceId_example";
    ContactsProperty contact = new ContactsProperty();
    try {
      List<ContactAttributes> result = client
              .contact
              .upsert(workspaceId)
              .contact(contact)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#upsert");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<ContactAttributes>> response = client
              .contact
              .upsert(workspaceId)
              .contact(contact)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContactApi#upsert");
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
| **workspaceId** | **String**|  | |
| **contactUpsertRequest** | [**ContactUpsertRequest**](ContactUpsertRequest.md)| Contact to create or update, matching on the email address. Note that properties of a Contact are not reset when passed empty values, e.g. passing &#x60;null&#x60; for &#x60;first_name&#x60; or an empty array for &#x60;tag_ids&#x60; won&#39;t update previous values. To do that you would instead use the &#x60;Update Contact&#x60; endpoint. | |

### Return type

[**List&lt;ContactAttributes&gt;**](ContactAttributes.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **422** | Unprocessable Entity |  -  |

