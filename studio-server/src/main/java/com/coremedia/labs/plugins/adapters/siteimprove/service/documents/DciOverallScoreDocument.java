package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DCI score
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class DciOverallScoreDocument {

  @JsonProperty("a11y")
  private AccessibilityDocument accessibilityDocument;

  @JsonProperty("qa")
  private QADocument qaDocument;

  @JsonProperty("seo")
  private SEODocument seoDocument;

  @JsonProperty("total")
  private double totalScore;

  public AccessibilityDocument getAccessibilityDocument() {
    return accessibilityDocument;
  }

  public void setAccessibilityDocument(AccessibilityDocument accessibilityDocument) {
    this.accessibilityDocument = accessibilityDocument;
  }

  public QADocument getQaDocument() {
    return qaDocument;
  }

  public void setQaDocument(QADocument qaDocument) {
    this.qaDocument = qaDocument;
  }

  public SEODocument getSeoDocument() {
    return seoDocument;
  }

  public void setSeoDocument(SEODocument seoDocument) {
    this.seoDocument = seoDocument;
  }

  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }
}
