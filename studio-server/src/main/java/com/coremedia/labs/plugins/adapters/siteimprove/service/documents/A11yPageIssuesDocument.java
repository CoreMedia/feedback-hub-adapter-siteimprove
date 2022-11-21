package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class A11yPageIssuesDocument {

  @JsonProperty("items")
  private List<A11yPageIssueDocument> items;

  @JsonProperty("siteimprove")
  private SiteimproveWebappDocument siteimprove;

  public List<A11yPageIssueDocument> getItems() {
    return items;
  }

  public void setItems(List<A11yPageIssueDocument> items) {
    this.items = items;
  }

  public SiteimproveWebappDocument getSiteimprove() {
    return siteimprove;
  }

  public void setSiteimprove(SiteimproveWebappDocument siteimprove) {
    this.siteimprove = siteimprove;
  }
}
