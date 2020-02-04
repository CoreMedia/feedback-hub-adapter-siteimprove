package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetatagNameContentDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("content")
  private String content;

  @JsonProperty("pages")
  private int pages;

  @JsonProperty("_links")
  private LinkDocument links;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public LinkDocument getLinks() {
    return links;
  }

  public void setLinks(LinkDocument links) {
    this.links = links;
  }

  public String getPagesUrl() {
    return links.getPages().getHref();
  }
}
