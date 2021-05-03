package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BrokenLinkPagesDocument {

  @JsonProperty("siteimprove")
  private SiteimproveWebappDocument siteimprove;

  @JsonProperty("items")
  private List<BrokenLinkPageDocument> brokenLinkPageDocuments;

  public List<BrokenLinkPageDocument> getBrokenPages() {
    return this.brokenLinkPageDocuments;
  }

  public SiteimproveWebappDocument getSiteimprove() {
    return siteimprove;
  }

  public void setSiteimprove(SiteimproveWebappDocument siteimprove) {
    this.siteimprove = siteimprove;
  }
}
