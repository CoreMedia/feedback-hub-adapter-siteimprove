package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Document for broken links in sites
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class BrokenLinkPageDocument {

  @JsonProperty("id")
  private String id;
  @JsonProperty("url")
  private String url;
  @JsonProperty("broken_links")
  private String brokenLinkClicks;
  @JsonProperty("page_score")
  private String pageScore;

  public String getPageScore() {
    return pageScore;
  }

  public void setPageScore(String pageScore) {
    this.pageScore = pageScore;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getBrokenLinkClicks() {
    return brokenLinkClicks;
  }

  public void setBrokenLinkClicks(String brokenLinkClicks) {
    this.brokenLinkClicks = brokenLinkClicks;
  }
}
