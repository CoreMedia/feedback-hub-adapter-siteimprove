package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Accessibility summary issues
 */
public class AccessibilitySummaryListDocument {
  @JsonProperty("items")
  private List<AccessibilitySummaryDocument> accessibilitySummaryDocuments;

  public List<AccessibilitySummaryDocument> getAccessibilitySummaryDocuments() {
    return accessibilitySummaryDocuments;
  }

  public void setAccessibilitySummaryDocuments(List<AccessibilitySummaryDocument> accessibilitySummaryDocuments) {
    this.accessibilitySummaryDocuments = accessibilitySummaryDocuments;
  }
}
