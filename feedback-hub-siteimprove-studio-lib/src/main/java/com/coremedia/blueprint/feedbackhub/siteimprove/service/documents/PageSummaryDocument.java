package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageSummaryDocument {

  @JsonProperty("page")
  private PageCheckStatusDocument page;

  public PageCheckStatusDocument getPage() {
    return page;
  }

  public void setPage(PageCheckStatusDocument page) {
    this.page = page;
  }
}
