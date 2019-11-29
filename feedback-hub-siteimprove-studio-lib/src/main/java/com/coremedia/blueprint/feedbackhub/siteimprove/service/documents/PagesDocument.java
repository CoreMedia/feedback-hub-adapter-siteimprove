package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PagesDocument {
  @JsonProperty("items")
  private List<PageDocument> pages;

  private LinkDocument links;

  public List<PageDocument> getPages() {
    return pages;
  }

  public void setPages(List<PageDocument> pages) {
    this.pages = pages;
  }

  public LinkDocument getLinks() {
    return links;
  }

  public void setLinks(LinkDocument links) {
    this.links = links;
  }

  public boolean hasNext() {
    return links.getNextUrl() != null;
  }

  public String next() {
    return links.getNextUrl().getHref();
  }
}
