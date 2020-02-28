package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class Seov2IssuesDocument {
  @JsonProperty("total_items")
  private int totalItems;

  @JsonProperty("total_pages")
  private int totalPages;

  @JsonProperty("items")
  private List<Seov2IssueDocument> items;

  @JsonProperty("links")
  private LinkDocument links;

  public Seov2IssuesDocument() {
    //default
  }

  public Seov2IssuesDocument(List<Seov2IssueDocument> issuesDocuments) {
    this.items = issuesDocuments;
    this.totalItems = issuesDocuments.size();
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

  public List<Seov2IssueDocument> getItems() {
    return items;
  }

  public void setItems(List<Seov2IssueDocument> items) {
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

  public void add(Seov2IssuesDocument nextIssues) {
    items.addAll(nextIssues.getItems());
    this.totalItems = items.size();
  }
}
