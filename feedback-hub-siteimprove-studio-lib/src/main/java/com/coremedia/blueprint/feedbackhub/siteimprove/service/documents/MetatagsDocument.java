package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class MetatagsDocument {

  @JsonProperty("items")
  private List<MetatagDocument> items;

  public List<MetatagDocument> getItems() {
    return items;
  }

  public void setItems(List<MetatagDocument> items) {
    this.items = items;
  }
}
