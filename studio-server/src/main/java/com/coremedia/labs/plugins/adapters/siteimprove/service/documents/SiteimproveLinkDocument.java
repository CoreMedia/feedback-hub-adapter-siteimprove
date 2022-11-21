package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteimproveLinkDocument {

  @JsonProperty("accessibility")
  private LinkDocument accessibility;

  @JsonProperty("policy")
  private LinkDocument policy;

  @JsonProperty("quality_assurance")
  private LinkDocument qualityAssurance;

  @JsonProperty("seo")
  private LinkDocument seo;


  public LinkDocument getAccessibility() {
    return accessibility;
  }

  public void setAccessibility(LinkDocument accessibility) {
    this.accessibility = accessibility;
  }

  public LinkDocument getPolicy() {
    return policy;
  }

  public void setPolicy(LinkDocument policy) {
    this.policy = policy;
  }

  public LinkDocument getQualityAssurance() {
    return qualityAssurance;
  }

  public void setQualityAssurance(LinkDocument qualityAssurance) {
    this.qualityAssurance = qualityAssurance;
  }

  public LinkDocument getSeo() {
    return seo;
  }

  public void setSeo(LinkDocument seo) {
    this.seo = seo;
  }
}
