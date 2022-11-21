package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class MetatagNameListDocument {

  @JsonProperty("items")
  private List<MetatagNameDocument> items;

  @JsonProperty("total_items")
  private int totalItems;

  @JsonProperty("total_pages")
  private int totalPages;

  public List<MetatagNameDocument> getItems() {
    return items;
  }

  public void setItems(List<MetatagNameDocument> items) {
    this.items = items;
  }

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
}
