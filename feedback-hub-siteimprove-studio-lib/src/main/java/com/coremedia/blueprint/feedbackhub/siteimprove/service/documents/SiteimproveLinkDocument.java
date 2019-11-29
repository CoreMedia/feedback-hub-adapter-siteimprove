package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

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


}
