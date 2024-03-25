

# OrdersInvoiceAttributes

Invoices

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | Invoice ID |  |
|**publicId** | **Object** | Invoice public ID |  [optional] |
|**orderId** | **Integer** | Creative concept ID |  |
|**status** | **String** | Status |  |
|**dueAmount** | **Object** | Due Amount |  [optional] |
|**totalAmount** | **Object** | Total Amt |  [optional] |
|**subtotalAmount** | **Object** | Subtotal Amount |  [optional] |
|**taxAmount** | **Object** | Tax Amount |  [optional] |
|**shippingAmount** | **Object** | Shipping Amount |  [optional] |
|**discountAmount** | **Object** | Discount Amount |  [optional] |
|**currency** | **Object** | Currency |  [optional] |
|**issuedAt** | **Object** | Issued At |  [optional] |
|**dueAt** | **Object** | Due At |  [optional] |
|**paidAt** | **Object** | Paid At |  [optional] |
|**invoiceType** | [**InvoiceTypeEnum**](#InvoiceTypeEnum) | Invoice Type |  [optional] |
|**createdAt** | **Object** | Added |  [optional] |
|**updatedAt** | **Object** | Updated |  [optional] |
|**lineItems** | [**List&lt;OrdersInvoicesLineItemAttributes&gt;**](OrdersInvoicesLineItemAttributes.md) | Invoice Line Items |  [optional] |



## Enum: InvoiceTypeEnum

| Name | Value |
|---- | -----|
| INITIAL | &quot;initial&quot; |
| RENEWAL | &quot;renewal&quot; |
| INTERIM | &quot;interim&quot; |
| CANCELLATION | &quot;cancellation&quot; |
| ONE_TIME | &quot;one_time&quot; |
| REFUND | &quot;refund&quot; |
| CHARGE | &quot;charge&quot; |
| ONE_TIME_SALE | &quot;one_time_sale&quot; |



