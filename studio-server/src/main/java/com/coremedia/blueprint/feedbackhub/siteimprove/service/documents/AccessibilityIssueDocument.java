package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessibilityIssueDocument {

  @JsonProperty("conformance_level")
  private String conformanceLevel;

  @JsonProperty("issues")
  private int issues;

  @JsonProperty("severity")
  private String severity;

  public String getConformanceLevel() {
    return conformanceLevel;
  }

  public void setConformanceLevel(String conformanceLevel) {
    this.conformanceLevel = conformanceLevel;
  }

  public int getIssues() {
    return issues;
  }

  public void setIssues(int issues) {
    this.issues = issues;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }
}
