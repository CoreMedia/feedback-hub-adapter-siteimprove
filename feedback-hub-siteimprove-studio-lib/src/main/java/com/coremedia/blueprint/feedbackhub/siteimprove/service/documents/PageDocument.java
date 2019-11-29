package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("url")
  private String url;

  @JsonProperty("_links")
  private LinkDocument detailLink;

  @JsonProperty("misspellings")
  private String misspellingCount;

  @JsonProperty("potential_misspellings")
  private String potentialMisspellingCount;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMisspellingCount() {
    return misspellingCount;
  }

  public void setMisspellingCount(String misspellingCount) {
    this.misspellingCount = misspellingCount;
  }

  public String getPotentialMisspellingCount() {
    return potentialMisspellingCount;
  }

  public void setPotentialMisspellingCount(String potentialMisspellingCount) {
    this.potentialMisspellingCount = potentialMisspellingCount;
  }
}
