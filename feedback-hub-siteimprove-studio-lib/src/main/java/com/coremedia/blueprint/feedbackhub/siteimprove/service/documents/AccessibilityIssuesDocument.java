package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class AccessibilityIssuesDocument {
  @JsonProperty("total_items")
  private int totalItems;

  @JsonProperty("items")
  private List<AccessibilityIssueDocument> items;

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public List<AccessibilityIssueDocument> getItems() {
    return items;
  }

  public void setItems(List<AccessibilityIssueDocument> items) {
    this.items = items;
  }
}
