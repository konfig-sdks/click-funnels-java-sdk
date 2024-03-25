

# ContactsProperty

Contacts

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**emailAddress** | **Object** | Email address |  |
|**firstName** | **Object** | First name |  [optional] |
|**lastName** | **Object** | Last name |  [optional] |
|**phoneNumber** | **Object** | Phone number |  [optional] |
|**timeZone** | **Object** | Time zone |  [optional] |
|**fbUrl** | **Object** | Facebook URL |  [optional] |
|**twitterUrl** | **Object** | Twitter URL |  [optional] |
|**instagramUrl** | **Object** | Instagram URL |  [optional] |
|**linkedinUrl** | **Object** | Linkedin URL |  [optional] |
|**websiteUrl** | **Object** | Website URL |  [optional] |
|**tagIds** | [**List&lt;ContactsPropertyTagIdsInner&gt;**](ContactsPropertyTagIdsInner.md) | Tag IDs  - An empty array is ignored here. If you wish to remove all tags for a Contact, use the &#x60;Update Contact&#x60; endpoint.  - A non-empty array overwrites the existing array.  - To avoid losing existing tags, first use the &#x60;Fetch Contact&#x60; endpoint then be sure to include the existing tags in your payload along with any new addition(s) |  [optional] |



