

# ProductsPriceAttributes

Prices

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID |  |
|**publicId** | **Object** | Price public ID |  [optional] |
|**variantId** | **Object** | Variant |  [optional] |
|**name** | **Object** | Name |  [optional] |
|**amount** | **String** | Amount |  |
|**cost** | **Object** | Cost |  [optional] |
|**currency** | **String** | Currency |  |
|**duration** | **Integer** | Duration |  |
|**interval** | **String** | Interval |  |
|**trialInterval** | **String** | Trial interval |  |
|**trialDuration** | **String** | Trial duration |  |
|**trialAmount** | **String** | Trial Amount |  |
|**setupAmount** | **Object** | Setup amount |  [optional] |
|**selfCancel** | **Object** | Customer can cancel |  [optional] |
|**selfUpgrade** | **Object** | Customer can upgrade |  [optional] |
|**selfDowngrade** | **Object** | Customer can downgrade |  [optional] |
|**selfReactivate** | **Object** | Customer can reactivate |  [optional] |
|**intervalCount** | **Integer** | Interval count |  |
|**paymentType** | [**PaymentTypeEnum**](#PaymentTypeEnum) | Payment Type |  [optional] |
|**visible** | **Object** | Visible |  [optional] |
|**compareAtAmount** | **Object** | Compare at amount |  [optional] |
|**key** | **Object** | Key |  [optional] |
|**archived** | **Object** | Archived |  [optional] |
|**createdAt** | **Object** | Added |  [optional] |
|**updatedAt** | **Object** | Updated |  [optional] |



## Enum: PaymentTypeEnum

| Name | Value |
|---- | -----|
| ONE_TIME | &quot;one_time&quot; |
| SUBSCRIPTION | &quot;subscription&quot; |
| PAYMENT_PLAN | &quot;payment_plan&quot; |



