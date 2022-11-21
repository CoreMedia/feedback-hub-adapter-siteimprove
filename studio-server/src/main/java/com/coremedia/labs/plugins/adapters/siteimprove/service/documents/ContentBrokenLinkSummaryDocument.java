package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Quality Summary for a content, a page or article.
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class ContentBrokenLinkSummaryDocument {
  @JsonProperty("broken_links")
  private String brokenLinks;
  @JsonProperty("page_score")
  private String pageScore;

  public String getBrokenLinks() {
    return brokenLinks;
  }

  public void setBrokenLinks(String brokenLinks) {
    this.brokenLinks = brokenLinks;
  }

  public String getPageScore() {
    return pageScore;
  }

  public void setPageScore(String pageScore) {
    this.pageScore = pageScore;
  }
}
