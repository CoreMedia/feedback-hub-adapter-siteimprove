package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessibilitySummaryDocument {

  @JsonProperty("conformance_level")
  private String conformanceLevel;
  @JsonProperty("issues")
  private String issues;
  @JsonProperty("severity")
  private String severity;

  public String getConformanceLevel() {
    return conformanceLevel;
  }

  public void setConformanceLevel(String conformanceLevel) {
    this.conformanceLevel = conformanceLevel;
  }

  public String getIssues() {
    return issues;
  }

  public void setIssues(String issues) {
    this.issues = issues;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }
}
