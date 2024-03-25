

# Restocks

Restocks

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | Restock ID |  |
|**publicId** | **Object** | Public Restock ID |  [optional] |
|**workspaceId** | **Integer** | Workspace ID |  |
|**contactId** | **Integer** | Contact ID |  |
|**status** | [**StatusEnum**](#StatusEnum) | Status |  |
|**createdAt** | **Object** | Created At Date |  |
|**updatedAt** | **Object** | Updated At Date |  |
|**includedInvoicesLineItems** | [**List&lt;IncludedOrdersInvoicesLineItems&gt;**](IncludedOrdersInvoicesLineItems.md) | Included order invoices line items |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CANCELLED | &quot;cancelled&quot; |
| RESTOCKED | &quot;restocked&quot; |



