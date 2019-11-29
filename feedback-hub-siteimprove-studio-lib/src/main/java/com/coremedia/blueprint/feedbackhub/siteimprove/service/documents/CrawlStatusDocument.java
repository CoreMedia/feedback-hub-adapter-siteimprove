package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrawlStatusDocument {
  @JsonProperty("last_crawl")
  private String lastCrawled;
  @JsonProperty("permission")
  private String crawlPermission;
  @JsonProperty("is_crawl_enabled")
  private Boolean isCrawlEnabled;
  @JsonProperty("is_crawl_running")
  private Boolean isCrawlRunning;

  public String getLastCrawled() {
    return lastCrawled;
  }

  public void setLastCrawled(String lastCrawled) {
    this.lastCrawled = lastCrawled;
  }

  public String getCrawlPermission() {
    return crawlPermission;
  }

  public void setCrawlPermission(String crawlPermission) {
    this.crawlPermission = crawlPermission;
  }

  public Boolean getCrawlEnabled() {
    return isCrawlEnabled;
  }

  public void setCrawlEnabled(Boolean crawlEnabled) {
    isCrawlEnabled = crawlEnabled;
  }

  public Boolean getCrawlRunning() {
    return isCrawlRunning;
  }

  public void setCrawlRunning(Boolean crawlRunning) {
    isCrawlRunning = crawlRunning;
  }
}
