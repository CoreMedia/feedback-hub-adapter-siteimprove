package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Seov2PagesDocument {
  @JsonProperty("items")
  private List<Seov2PageDocument> pages;

  @JsonProperty("total_items")
  private int totalItems;

  @JsonProperty("total_pages")
  private int totalPages;

  private LinkDocument links;

  public List<Seov2PageDocument> getPages() {
    return pages;
  }

  public void setPages(List<Seov2PageDocument> pages) {
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
