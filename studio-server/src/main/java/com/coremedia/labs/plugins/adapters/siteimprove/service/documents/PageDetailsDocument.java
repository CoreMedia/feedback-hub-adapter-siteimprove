package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageDetailsDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("url")
  private String url;

  @JsonProperty("cms_url")
  private String cmsUrl;

  @JsonProperty("size_bytes")
  private int sizeBites;

  //TODO: '_links'

  @JsonProperty("summary")
  private PageSummaryDocument summary;

  @JsonProperty("_siteimprove")
  private SiteimproveLinkDocument siteimprove;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCmsUrl() {
    return cmsUrl;
  }

  public void setCmsUrl(String cmsUrl) {
    this.cmsUrl = cmsUrl;
  }

  public int getSizeBites() {
    return sizeBites;
  }

  public void setSizeBites(int sizeBites) {
    this.sizeBites = sizeBites;
  }

  public SiteimproveLinkDocument getSiteimprove() {
    return siteimprove;
  }

  public void setSiteimprove(SiteimproveLinkDocument siteimprove) {
    this.siteimprove = siteimprove;
  }

  public PageSummaryDocument getSummary() {
    return summary;
  }

  public void setSummary(PageSummaryDocument summary) {
    this.summary = summary;
  }
}
