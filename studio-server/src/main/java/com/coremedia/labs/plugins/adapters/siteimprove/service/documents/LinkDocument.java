package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json document for link
 */

@JsonIgnoreProperties(ignoreUnknown = false)
public class LinkDocument {

  @JsonProperty("details")
  private HrefDocument details;

  @JsonProperty("next")
  private HrefDocument nextUrl;

  @JsonProperty("prev")
  private HrefDocument prevUrl;

  @JsonProperty("self")
  private HrefDocument selfUrl;

  @JsonProperty("issues")
  private HrefDocument issues;

  @JsonProperty("page_report")
  private HrefDocument pageReport;

  @JsonProperty("contents")
  private HrefDocument contents;

  @JsonProperty("pages")
  private HrefDocument pages;

  public HrefDocument getDetails() {
    return details;
  }

  public void setDetails(HrefDocument details) {
    this.details = details;
  }

  public HrefDocument getSelfUrl() {
    return selfUrl;
  }

  public void setSelfUrl(HrefDocument selfUrl) {
    this.selfUrl = selfUrl;
  }

  public HrefDocument getPrevUrl() {
    return prevUrl;
  }

  public void setPrevUrl(HrefDocument prevUrl) {
    this.prevUrl = prevUrl;
  }

  public HrefDocument getNextUrl() {
    return nextUrl;
  }

  public void setNextUrl(HrefDocument nextUrl) {
    this.nextUrl = nextUrl;
  }

  public HrefDocument getIssues() {
    return issues;
  }

  public void setIssues(HrefDocument issues) {
    this.issues = issues;
  }

  public HrefDocument getPageReport() {
    return pageReport;
  }

  public void setPageReport(HrefDocument pageReport) {
    this.pageReport = pageReport;
  }

  public HrefDocument getContents() {
    return contents;
  }

  public void setContents(HrefDocument contents) {
    this.contents = contents;
  }

  public HrefDocument getPages() {
    return pages;
  }

  public void setPages(HrefDocument pages) {
    this.pages = pages;
  }
}
