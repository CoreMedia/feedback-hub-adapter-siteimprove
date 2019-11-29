package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Seov2IssuesDocument {
  @JsonProperty("total_items")
  private int totalItems;

  @JsonProperty("items")
  private List<Seov2IssueDocument> items;

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public List<Seov2IssueDocument> getItems() {
    return items;
  }

  public void setItems(List<Seov2IssueDocument> items) {
    this.items = items;
  }
}
