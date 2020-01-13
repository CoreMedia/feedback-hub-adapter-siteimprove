package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CrawlStatusDocument {
  @JsonProperty("last_crawl")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastCrawl;
  @JsonProperty("next_crawl")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date nextCrawl;
  @JsonProperty("permission")
  private String crawlPermission;
  @JsonProperty("is_crawl_enabled")
  private Boolean isCrawlEnabled;
  @JsonProperty("is_crawl_running")
  private Boolean isCrawlRunning;

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

  public Date getLastCrawl() {
    return lastCrawl;
  }

  public void setLastCrawl(Date lastCrawl) {
    this.lastCrawl = lastCrawl;
  }

  public Date getNextCrawl() {
    return nextCrawl;
  }

  public void setNextCrawl(Date nextCrawl) {
    this.nextCrawl = nextCrawl;
  }
}
