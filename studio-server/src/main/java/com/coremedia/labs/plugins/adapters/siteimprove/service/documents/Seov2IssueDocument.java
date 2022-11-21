package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seov2IssueDocument {

  @JsonProperty("complexity")
  private String complexity;

  @JsonProperty("issue_name")
  private String issueName;

  @JsonProperty("issue_type")
  private String issueType;

  @JsonProperty("occurrences")
  private int occurrences;

  @JsonProperty("seo_points_gained")
  private double seoPointsGained;

  @JsonProperty("seo_points_to_gain")
  private double seoPointsToGain;

  @JsonProperty("_links")
  private LinkDocument detailsLink;

  @JsonProperty("_siteimprove")
  private LinkDocument pageReportLink;

  public String getComplexity() {
    return complexity;
  }

  public void setComplexity(String complexity) {
    this.complexity = complexity;
  }

  public String getIssueName() {
    return issueName;
  }

  public void setIssueName(String issueName) {
    this.issueName = issueName;
  }

  public String getIssueType() {
    return issueType;
  }

  public void setIssueType(String issueType) {
    this.issueType = issueType;
  }

  public int getOccurrences() {
    return occurrences;
  }

  public void setOccurrences(int occurrences) {
    this.occurrences = occurrences;
  }

  public double getSeoPointsGained() {
    return seoPointsGained;
  }

  public void setSeoPointsGained(double seoPointsGained) {
    this.seoPointsGained = seoPointsGained;
  }

  public double getSeoPointsToGain() {
    return seoPointsToGain;
  }

  public void setSeoPointsToGain(double seoPointsToGain) {
    this.seoPointsToGain = seoPointsToGain;
  }

  public LinkDocument getDetailsLink() {
    return detailsLink;
  }

  public void setDetailsLink(LinkDocument detailsLink) {
    this.detailsLink = detailsLink;
  }

  public LinkDocument getPageReportLink() {
    return pageReportLink;
  }

  public void setPageReportLink(LinkDocument pageReportLink) {
    this.pageReportLink = pageReportLink;
  }
}
