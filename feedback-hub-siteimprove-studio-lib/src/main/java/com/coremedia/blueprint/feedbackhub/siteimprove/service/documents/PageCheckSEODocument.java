package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Status information of a page seo.
 */
public class PageCheckSEODocument {

  @JsonProperty("content_issues")
  private Integer content_issues;

  @JsonProperty("technical_issues")
  private Integer technical_issues;

  @JsonProperty("ux_issues")
  private Integer ux_issues;

  public Integer getContent_issues() {
    return content_issues;
  }

  public void setContent_issues(Integer content_issues) {
    this.content_issues = content_issues;
  }

  public Integer getTechnical_issues() {
    return technical_issues;
  }

  public void setTechnical_issues(Integer technical_issues) {
    this.technical_issues = technical_issues;
  }

  public Integer getUx_issues() {
    return ux_issues;
  }

  public void setUx_issues(Integer ux_issues) {
    this.ux_issues = ux_issues;
  }

  public Integer getIssuesCount(){
    return technical_issues + content_issues + ux_issues;
  }
}
