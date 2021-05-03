package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seov2PageDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("url")
  private String url;

  @JsonProperty("checking_now")
  private boolean checkingNow;

  @JsonProperty("issues")
  private int issues;

  @JsonProperty("page_level")
  private int pageLevel;

  @JsonProperty("seo_page_score")
  private double seoPageScore;

  @JsonProperty("seo_points_to_gain")
  private double seoPointsToGain;

  @JsonProperty("_links")
  private LinkDocument issuesLink;

  @JsonProperty("_siteimprove")
  private LinkDocument pageReportLink;

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

  public boolean isCheckingNow() {
    return checkingNow;
  }

  public void setCheckingNow(boolean checkingNow) {
    this.checkingNow = checkingNow;
  }

  public int getIssues() {
    return issues;
  }

  public void setIssues(int issues) {
    this.issues = issues;
  }

  public int getPageLevel() {
    return pageLevel;
  }

  public void setPageLevel(int pageLevel) {
    this.pageLevel = pageLevel;
  }

  public double getSeoPageScore() {
    return seoPageScore;
  }

  public void setSeoPageScore(double seoPageScore) {
    this.seoPageScore = seoPageScore;
  }

  public double getSeoPointsToGain() {
    return seoPointsToGain;
  }

  public void setSeoPointsToGain(double seoPointsToGain) {
    this.seoPointsToGain = seoPointsToGain;
  }

  public LinkDocument getIssuesLink() {
    return issuesLink;
  }

  public void setIssuesLink(LinkDocument issuesLink) {
    this.issuesLink = issuesLink;
  }

  public LinkDocument getPageReportLink() {
    return pageReportLink;
  }

  public void setPageReportLink(LinkDocument pageReportLink) {
    this.pageReportLink = pageReportLink;
  }
}
