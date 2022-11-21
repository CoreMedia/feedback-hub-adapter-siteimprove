package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SitesDocument {

  @JsonProperty("items")
  private List<SiteDocument> siteDocumentList;


  public List<SiteDocument> getSiteDocumentList() {
    return this.siteDocumentList;
  }
}
