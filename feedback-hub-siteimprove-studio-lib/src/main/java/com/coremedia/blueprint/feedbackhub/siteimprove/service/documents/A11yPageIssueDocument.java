package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  "items": [
 *         {
 *             "id": 27836403334,
 *             "title": "| CoreMedia AG",
 *             "url": "https://preview.sherlock-labs.testsystem.coremedia.io/blueprint/servlet/corporate-de-de/for-professionals/products/2112-2112",
 *             "a_issues": 42,
 *             "aa_issues": 14,
 *             "aaa_issues": 8,
 *             "checking_now": false,
 *             "page_level": 0,
 *             "_siteimprove": {
 *                 "page_report": {
 *                     "href": "http://my2.siteimprove.com/Inspector/948803/Accessibility/Page?pageId=27836403334&impmd=07B55DBE45CF5044F1627F9400343328"
 *                 }
 *             }
 *         }
 *     ],
 */
public class A11yPageIssueDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("url")
  private String url;

  @JsonProperty("a_issues")
  private int aIssues;

  @JsonProperty("aa_issues")
  private int aaIssues;

  @JsonProperty("aaa_issues")
  private int aaaIssues;

  @JsonProperty("_siteimprove")
  private SiteimproveWebappDocument siteimprove;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getaIssues() {
    return aIssues;
  }

  public void setaIssues(int aIssues) {
    this.aIssues = aIssues;
  }

  public int getAaIssues() {
    return aaIssues;
  }

  public void setAaIssues(int aaIssues) {
    this.aaIssues = aaIssues;
  }

  public int getAaaIssues() {
    return aaaIssues;
  }

  public void setAaaIssues(int aaaIssues) {
    this.aaaIssues = aaaIssues;
  }

  public SiteimproveWebappDocument getSiteimprove() {
    return siteimprove;
  }

  public void setSiteimprove(SiteimproveWebappDocument siteimprove) {
    this.siteimprove = siteimprove;
  }
}
