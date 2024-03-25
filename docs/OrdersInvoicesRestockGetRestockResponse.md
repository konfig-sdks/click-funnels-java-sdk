

# OrdersInvoicesRestockGetRestockResponse

Restock

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | Restock |  |
|**publicId** | **Object** | Public Restock ID |  [optional] |
|**workspaceId** | **Integer** | Workspace ID |  |
|**contactId** | **Integer** | Contact ID |  |
|**status** | [**StatusEnum**](#StatusEnum) | Status |  |
|**createdAt** | **Object** | Created At Date |  |
|**updatedAt** | **Object** | Updated At Date |  |
|**includedInvoicesLineItems** | [**List&lt;IncludedOrdersInvoicesLineItems1&gt;**](IncludedOrdersInvoicesLineItems1.md) | Included Order Invoices Line Items |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CANCELLED | &quot;cancelled&quot; |
| RESTOCKED | &quot;restocked&quot; |



