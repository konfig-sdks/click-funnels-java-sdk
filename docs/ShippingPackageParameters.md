

# ShippingPackageParameters

Packages

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**packageType** | [**PackageTypeEnum**](#PackageTypeEnum) | Package Type |  |
|**height** | **Double** | Height |  |
|**width** | **Double** | Width |  |
|**length** | **Double** | Length |  |
|**distanceUnit** | [**DistanceUnitEnum**](#DistanceUnitEnum) | Distance Unit |  |
|**emptyWeight** | **Object** | Empty Weight |  [optional] |
|**weight** | **Object** | Weight |  [optional] |
|**weightUnit** | [**WeightUnitEnum**](#WeightUnitEnum) | Weight Unit |  [optional] |
|**defaultPackage** | **Object** | Default Package |  [optional] |
|**name** | **String** | Name |  |
|**carrier** | [**CarrierEnum**](#CarrierEnum) | Carrier |  [optional] |



## Enum: PackageTypeEnum

| Name | Value |
|---- | -----|
| BOX | &quot;box&quot; |
| ENVELOPE | &quot;envelope&quot; |
| SOFT_PACKAGE | &quot;soft_package&quot; |



## Enum: DistanceUnitEnum

| Name | Value |
|---- | -----|
| IN | &quot;in&quot; |
| CM | &quot;cm&quot; |
| FT | &quot;ft&quot; |
| M | &quot;m&quot; |
| MM | &quot;mm&quot; |



## Enum: WeightUnitEnum

| Name | Value |
|---- | -----|
| LB | &quot;lb&quot; |
| KG | &quot;kg&quot; |
| G | &quot;g&quot; |
| OZ | &quot;oz&quot; |



## Enum: CarrierEnum

| Name | Value |
|---- | -----|
| FEDEX | &quot;fedex&quot; |
| UPS | &quot;ups&quot; |
| USPS | &quot;usps&quot; |



