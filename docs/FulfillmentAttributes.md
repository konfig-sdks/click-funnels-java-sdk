

# FulfillmentAttributes

Fulfillments

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | Fulfillment |  |
|**publicId** | **Object** | Public Fulfillment ID |  [optional] |
|**workspaceId** | **Integer** | Workspace ID |  |
|**contactId** | **Integer** | Contact ID |  |
|**status** | [**StatusEnum**](#StatusEnum) | Status |  |
|**trackingUrl** | **Object** | Tracking URL |  [optional] |
|**shippingProvider** | **Object** | Shipping Provider |  [optional] |
|**trackingCode** | **Object** | Tracking Code |  [optional] |
|**locationId** | **Integer** | Location |  |
|**fulfillmentNumber** | **Object** | Fulfillment Number |  [optional] |
|**createdAt** | **Object** | Created |  [optional] |
|**updatedAt** | **Object** | Updated |  [optional] |
|**includedInvoicesLineItems** | [**List&lt;IncludedOrdersLineItems&gt;**](IncludedOrdersLineItems.md) | Included order invoices line items |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CANCELLED | &quot;cancelled&quot; |
| FULFILLED | &quot;fulfilled&quot; |
| RESTOCKED | &quot;restocked&quot; |



