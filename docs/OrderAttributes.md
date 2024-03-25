

# OrderAttributes

Orders

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ClickFunnels Order ID |  |
|**publicId** | **Object** | ClickFunnels Order public ID |  [optional] |
|**orderNumber** | **Object** | Order Number |  [optional] |
|**workspaceId** | **Integer** | ClickFunnels Workspace ID |  |
|**contactId** | **Integer** | Customer |  |
|**totalAmount** | **Object** | Total |  [optional] |
|**currency** | **String** | Currency |  |
|**originationChannelId** | **Integer** | Origination Channel ID |  |
|**originationChannelType** | **Object** | Origination Channel Type |  [optional] |
|**shippingAddressFirstName** | **Object** | First Name |  [optional] |
|**shippingAddressLastName** | **Object** | Last Name |  [optional] |
|**shippingAddressOrganizationName** | **Object** | Organization Name |  [optional] |
|**shippingAddressPhoneNumber** | **Object** | Phone Number |  [optional] |
|**shippingAddressStreetOne** | **Object** | Street One |  [optional] |
|**shippingAddressStreetTwo** | **Object** | Street Two |  [optional] |
|**shippingAddressCity** | **Object** | City |  [optional] |
|**shippingAddressRegion** | **Object** | State |  [optional] |
|**shippingAddressCountry** | **Object** | Country |  [optional] |
|**shippingAddressPostalCode** | **Object** | Postal Code |  [optional] |
|**billingAddressStreetOne** | **Object** | Street One |  [optional] |
|**billingAddressStreetTwo** | **Object** | Street Two |  [optional] |
|**billingAddressCity** | **Object** | City |  [optional] |
|**billingAddressRegion** | **Object** | State |  [optional] |
|**billingAddressCountry** | **Object** | Country |  [optional] |
|**billingAddressPostalCode** | **Object** | Postal Code |  [optional] |
|**pageId** | **Object** | Page ID |  [optional] |
|**notes** | **Object** | Notes |  [optional] |
|**inTrial** | **Object** | In Trial |  [optional] |
|**billingStatus** | **Object** | Billing Status |  [optional] |
|**serviceStatus** | **Object** | Order Status |  [optional] |
|**orderType** | **Object** | Order Type |  [optional] |
|**nextChargeAt** | **Object** | Next Charge At |  [optional] |
|**taxAmount** | **Object** | Tax Amount |  [optional] |
|**trialEndAt** | **Object** | Trial End At |  [optional] |
|**billingPaymentMethodId** | **Object** | Payment Method |  [optional] |
|**funnelName** | **Object** | Funnel Name |  [optional] |
|**tagIds** | **Object** | Tags |  [optional] |
|**discountIds** | **Object** | Discounts |  [optional] |
|**activatedAt** | **Object** | Date Activated |  [optional] |
|**createdAt** | **Object** | Date Ordered |  [optional] |
|**updatedAt** | **Object** | Date Updated |  [optional] |
|**phoneNumber** | **Object** | Phone Number |  [optional] |
|**pageName** | **Object** | Page Name |  [optional] |
|**originationChannelName** | **Object** | Origination Channel Name |  [optional] |
|**orderPage** | [**PagesProperty**](PagesProperty.md) |  |  [optional] |
|**contact** | [**ContactAttributes**](ContactAttributes.md) | Customer Email |  |
|**contactGroups** | [**List&lt;OrderContactGroupAttributes&gt;**](OrderContactGroupAttributes.md) | Contact Groups |  [optional] |
|**workspace** | [**WorkspaceAttributes**](WorkspaceAttributes.md) | Workspace |  |
|**segments** | [**List&lt;OrderSegmentAttributes&gt;**](OrderSegmentAttributes.md) | Segments |  [optional] |
|**lineItems** | [**List&lt;OrdersLineItemAttributes&gt;**](OrdersLineItemAttributes.md) | Order Line Items |  [optional] |
|**previousLineItem** | **Object** | An additional field, only available in outgoing subscription.upgrade/subscription.downgrade webhooks. It enables you to identify the former line item which is otherwise removed from the regular subscription line items when an upgrade or downgrade happens. Thus you can see the data of the previously active subscription. This property will not appear in regular API requests, but only in the event of a subscription upgrade or downgrade. |  [optional] |



