package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class AccessibilityIssuesDocument {
  @JsonProperty("total_items")
  private int totalItems;

  @JsonProperty("total_pages")
  private int totalPages;

  @JsonProperty("items")
  private List<AccessibilityIssueDocument> items;

  @JsonProperty("links")
  private LinkDocument links;

  public int getTotalItems() {
    return totalItems;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public List<AccessibilityIssueDocument> getItems() {
    return items;
  }

  public void setItems(List<AccessibilityIssueDocument> items) {
    this.items = items;
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

  public String nextUrl() {
    return links.getNextUrl().getHref();
  }

  public void add(AccessibilityIssuesDocument nextIssues) {
    items.addAll(nextIssues.getItems());
    this.totalItems = items.size();
  }

}
