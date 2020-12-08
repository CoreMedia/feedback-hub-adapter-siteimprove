package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class ContentCheckIssuesDocument {
  @JsonProperty("policy")
  private List<ContentIssue> policy;

  @JsonProperty("quality_assurance")
  private List<ContentIssue> qualityAssurance;

  @JsonProperty("seo")
  private List<ContentIssue> seo;

  private String message;

  public List<ContentIssue> getPolicy() {
    return policy;
  }

  public void setPolicy(List<ContentIssue> policy) {
    this.policy = policy;
  }

  public List<ContentIssue> getQualityAssurance() {
    return qualityAssurance;
  }

  public void setQualityAssurance(List<ContentIssue> qualityAssurance) {
    this.qualityAssurance = qualityAssurance;
  }

  public List<ContentIssue> getSeo() {
    return seo;
  }

  public void setSeo(List<ContentIssue> seo) {
    this.seo = seo;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
