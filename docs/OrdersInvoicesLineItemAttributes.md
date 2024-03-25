

# OrdersInvoicesLineItemAttributes

Line items

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**description** | **Object** | Description |  [optional] |
|**id** | **Integer** | Line item ID |  |
|**publicId** | **Object** | Public ID |  [optional] |
|**invoiceId** | **Integer** | Invoice ID |  |
|**externalId** | **String** | External |  |
|**paymentType** | **Object** | Payment Type |  [optional] |
|**amount** | **Object** | Amount |  [optional] |
|**quantity** | **Object** | Quantity |  [optional] |
|**fulfillmentStatus** | [**FulfillmentStatusEnum**](#FulfillmentStatusEnum) | Represents the current fulfillment status of the line item |  [optional] |
|**externalProductId** | **Object** | External Product |  [optional] |
|**discountAmount** | **Object** | Discount Amount |  [optional] |
|**stateTaxAmount** | **Object** | State Tax Amount |  [optional] |
|**countyTaxAmount** | **Object** | County Tax Amount |  [optional] |
|**cityTaxAmount** | **Object** | City Tax Amount |  [optional] |
|**districtTaxAmount** | **Object** | District Tax Amount |  [optional] |
|**stateTaxRate** | **Object** | State Tax Rate |  [optional] |
|**countyTaxRate** | **Object** | County Tax Rate |  [optional] |
|**cityTaxRate** | **Object** | City Tax Rate |  [optional] |
|**districtTaxRate** | **Object** | District Tax Rate |  [optional] |
|**countryTaxJurisdiction** | **Object** | Country Tax Jurisdiction |  [optional] |
|**stateTaxJurisdiction** | **Object** | State Tax Jurisdiction |  [optional] |
|**countyTaxJurisdiction** | **Object** | County Tax Jurisdiction |  [optional] |
|**cityTaxJurisdiction** | **Object** | City Tax Jurisdiction |  [optional] |
|**periodStartAt** | **Object** | Period Start At |  [optional] |
|**periodEndAt** | **Object** | Period End At |  [optional] |
|**periodNumber** | **Object** | Period Number |  [optional] |
|**createdAt** | **Object** | Added |  [optional] |
|**updatedAt** | **Object** | Updated |  [optional] |
|**productsPrice** | [**LineItemsProperty**](LineItemsProperty.md) |  |  [optional] |
|**productsVariant** | [**LineItemsProperty1**](LineItemsProperty1.md) |  |  [optional] |



## Enum: FulfillmentStatusEnum

| Name | Value |
|---- | -----|
| UNFULFILLED | &quot;unfulfilled&quot; |
| PARTIALLY_FULFILLED | &quot;partially_fulfilled&quot; |
| COMPLETE | &quot;complete&quot; |
| IGNORED | &quot;ignored&quot; |



