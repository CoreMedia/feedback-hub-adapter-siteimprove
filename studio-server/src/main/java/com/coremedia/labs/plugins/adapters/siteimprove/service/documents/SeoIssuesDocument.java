package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class SeoIssuesDocument {
  @JsonProperty("total_items")
  private int totalItems;

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public List<String> getUrls() {
    return Collections.emptyList();
  }
}
