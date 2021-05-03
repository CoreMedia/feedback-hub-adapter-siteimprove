package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetatagNameDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("meta_tag")
  private String metaTag;

  @JsonProperty("occurrences")
  private int occurrences;

  @JsonProperty("_links")
  private LinkDocument links;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMetaTag() {
    return metaTag;
  }

  public void setMetaTag(String metaTag) {
    this.metaTag = metaTag;
  }

  public int getOccurrences() {
    return occurrences;
  }

  public void setOccurrences(int occurrences) {
    this.occurrences = occurrences;
  }

  public String getContentsUrl() {
    return links.getContents().getHref();
  }
}
