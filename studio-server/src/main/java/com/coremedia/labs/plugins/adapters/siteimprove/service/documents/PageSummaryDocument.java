package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageSummaryDocument {

  @JsonProperty("page")
  private PageCheckStatusDocument page;

  @JsonProperty("seov2")
  private PageCheckSEODocument seov2;

  public PageCheckStatusDocument getPage() {
    return page;
  }

  public void setPage(PageCheckStatusDocument page) {
    this.page = page;
  }

  public PageCheckSEODocument getSeov2() {
    return seov2;
  }

  public void setSeov2(PageCheckSEODocument seov2) {
    this.seov2 = seov2;
  }
}
