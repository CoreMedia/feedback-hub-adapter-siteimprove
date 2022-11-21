# Configuration

This documentation describes how to enable the CoreMedia Feedback Hub integration for Siteimprove. 

## Prerequisites

In order to use Siteimprove with CoreMedia, you should already have set up your sites in Siteimprove.
Ensure that not only the live CAE is crawled by Siteimprove, but also the preview CAE.

The following screenshot shows the setup of two test sites: _Labs Preview_ and _Labs Live_.

![Siteimprove Sites](sites.png "Siteimprove Sites")

# Installation of the CoreMedia Siteimprove Plugin
[Installation](installation.md)

# Configuration for the CoreMedia Feedbackhub 

The CoreMedia Feedback Hub integration for Siteimprove can be enabled by creating a new global _CMSettings_ document 
in either a global or site-specific folder.

- Global: _/Settings/Options/Settings/Feedback Hub/_
- Site specific: _&lt;SITE&gt;/Options/Settings/Feedback Hub/_

Edit the settings document using the struct editor. Create the following settings:

```xml
<Struct xmlns="http://www.coremedia.com/2008/struct">
  <StringProperty Name="factoryId">siteimprove</StringProperty>
  <StringProperty Name="groupId">siteimprove</StringProperty>
  <StringProperty Name="contentType">CMLinkable</StringProperty>
  <BooleanProperty Name="enabled">true</BooleanProperty>
  <StructProperty Name="settings">
    <Struct>
      <StringProperty Name="contentLinkBuilderStrategy">cae</StringProperty>  
      <StringProperty Name="email"></StringProperty>
      <StringProperty Name="apiKey"></StringProperty>
      <StringProperty Name="siteimprovePreviewSiteId"></StringProperty>
      <StringProperty Name="siteimproveLiveSiteId"></StringProperty>
      <StringProperty Name="previewCaeBaseUrl">http://cae-preview:8081</StringProperty>
      <StringProperty Name="liveCaeBaseUrl">http://cae-live:8081/</StringProperty>
    </Struct>
  </StructProperty>
</Struct>
```

####  Adapter Configuration Details

| Property | Mandatory | Description |
| -------- |:---------:| ----------- |
| factoryId | yes | Set it to _siteimprove_  |
| groupId | yes | Set it to _siteimprove_  |
| contentType | yes | Configures the CoreMedia content type the feedback is enabled for.  |
| enabled | yes | Enables/disables the adapter  |

####  Adapter Settings Struct Details

| Property | Mandatory | Description |
| -------- |:---------:| ----------- |
| email | yes | The email of the Siteimprove account to be used, ensure that the user has enough privileges.  |
| apiKey | yes | The API key of the email account. You can create a new API key using the Siteimprove manager (_Main Menu -> Integrations -> API -> API Keys_). |
| siteimprovePreviewSiteId | yes | The Siteimprove site id of the crawled Coremedia _preview_ site, see section "How to find the Siteimprove site id?". |
| siteimproveLiveSiteId | yes | The Siteimprove site id of the crawled Coremedia _live_ site, see section "How to find the Siteimprove site id?". |
| previewCaeBaseUrl | yes | The base url of the preview cae |
| liveCaeBaseUrl | yes | The base url of the live cae |
| contentLinkBuilderStrategy | yes | Defines which link builder strategy should be used to generate links which are then crawled by Siteimprove. Can be "cae" for cae-based frontends or "direct" for other frontends. |

#### Example

![Siteimprove Hub Integration](hub.png "Siteimprove Hub Integration")

#### How to find the Siteimprove site id?

In the Siteimprove manager, go to _Main Menu -> Settings -> Sites_.
The page shows a list of all sites configured for Siteimprove. 

The details link for each site contains the Siteimprove site id, e.g.: 

```
https://my2.siteimprove.com/Settings/<SITEMRPOVE_SITE_ID>/Sites/EditSite
```

## Configuring Content Issues

The list of issues shown in the feedback panel is filtered.
Since the editor can't handle all types of issues reported by Siteimprove (e.g. errors on the markup), 
the extension filters the issues and returns only those the editor can actually do something about.

The whitelist of issues is configured in the _enum_ class _SeoIssueTypesEditorWhitelist_ and can be customized there.
