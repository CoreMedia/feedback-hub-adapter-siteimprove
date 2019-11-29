package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Analytics Summary from SI
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class AnalyticsSummaryDocument {
  @JsonProperty("bounce_rate")
  private String bounceRate;
  @JsonProperty("new_visitors")
  private String newVisitors;
  @JsonProperty("page_views")
  private String pageViews;
  @JsonProperty("returning_visitors")
  private String returningVisitors;
  @JsonProperty("unique_visitors")
  private String uniqueVisitors;
  @JsonProperty("visits")
  private String visits;


  public String getBounceRate() {
    return bounceRate;
  }

  public void setBounceRate(String bounceRate) {
    this.bounceRate = bounceRate;
  }

  public String getNewVisitors() {
    return newVisitors;
  }

  public void setNewVisitors(String newVisitors) {
    this.newVisitors = newVisitors;
  }

  public String getPageViews() {
    return pageViews;
  }

  public void setPageViews(String pageViews) {
    this.pageViews = pageViews;
  }

  public String getReturningVisitors() {
    return returningVisitors;
  }

  public void setReturningVisitors(String returningVisitors) {
    this.returningVisitors = returningVisitors;
  }

  public String getUniqueVisitors() {
    return uniqueVisitors;
  }

  public void setUniqueVisitors(String uniqueVisitors) {
    this.uniqueVisitors = uniqueVisitors;
  }

  public String getVisits() {
    return visits;
  }

  public void setVisits(String visits) {
    this.visits = visits;
  }
}
