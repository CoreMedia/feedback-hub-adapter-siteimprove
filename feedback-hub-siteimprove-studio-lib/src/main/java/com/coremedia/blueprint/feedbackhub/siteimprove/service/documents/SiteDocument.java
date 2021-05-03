package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json document for site
 */

@JsonIgnoreProperties(ignoreUnknown = false)
public class SiteDocument {

  @JsonProperty("id")
  private String siteID;
  @JsonProperty("site_name")
  private String siteName;
  @JsonProperty("url")
  private String url;


  public String getSiteID() {
    return siteID;
  }

  public void setSiteID(String siteID) {
    this.siteID = siteID;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSiteRootUrl() {
    return url.substring(0, url.lastIndexOf("/"));
  }
}
