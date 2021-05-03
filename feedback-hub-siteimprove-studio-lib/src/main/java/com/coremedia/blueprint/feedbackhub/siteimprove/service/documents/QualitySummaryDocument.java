package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Count of different aspects of the page from Siteimprove
 */
public class QualitySummaryDocument {

  @JsonProperty("broken_links")
  private String brokenLinks;
  @JsonProperty("misspellings")
  private String misspelling;
  @JsonProperty("pages")
  private String pages;
  //number of pages affected by broken links
  @JsonProperty("pages_affected_by_broken_links")
  private String brokenLinkPages;
  @JsonProperty("potential_misspellings")
  private String potentialMisspelling;

  private int seoIssues = -1;

  public String getBrokenLinks() {
    return brokenLinks;
  }

  public void setBrokenLinks(String brokenLinks) {
    this.brokenLinks = brokenLinks;
  }

  public String getMisspelling() {
    return misspelling;
  }

  public void setMisspelling(String misspelling) {
    this.misspelling = misspelling;
  }

  public String getPages() {
    return pages;
  }

  public void setPages(String pages) {
    this.pages = pages;
  }

  public String getBrokenLinkPages() {
    return brokenLinkPages;
  }

  public void setBrokenLinkPages(String brokenLinkPages) {
    this.brokenLinkPages = brokenLinkPages;
  }

  public String getPotentialMisspelling() {
    return potentialMisspelling;
  }

  public void setPotentialMisspelling(String potentialMisspelling) {
    this.potentialMisspelling = potentialMisspelling;
  }


  public int getSeoIssues() {
    return seoIssues;
  }

  public void setSeoIssues(int seoIssues) {
    this.seoIssues = seoIssues;
  }
}
