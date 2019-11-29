package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class SEODocument {

  @JsonProperty("content")
  private double content;

  @JsonProperty("mobile")
  private double mobile;

  @JsonProperty("technical")
  private double technical;

  @JsonProperty("total")
  private double total;

  @JsonProperty("ux")
  private double ux;

  public double getContent() {
    return content;
  }

  public void setContent(double content) {
    this.content = content;
  }

  public double getMobile() {
    return mobile;
  }

  public void setMobile(double mobile) {
    this.mobile = mobile;
  }

  public double getTechnical() {
    return technical;
  }

  public void setTechnical(double technical) {
    this.technical = technical;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public double getUx() {
    return ux;
  }

  public void setUx(double ux) {
    this.ux = ux;
  }
}
