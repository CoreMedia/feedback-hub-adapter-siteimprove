package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetatagDocument {

  @JsonProperty("id")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
