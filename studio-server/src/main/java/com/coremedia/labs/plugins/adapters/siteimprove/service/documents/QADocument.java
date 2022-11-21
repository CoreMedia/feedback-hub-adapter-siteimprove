package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class QADocument {
  @JsonProperty("content")
  private double content;

  @JsonProperty("freshness")
  private double freshness;

  @JsonProperty("security")
  private double security;

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

  public double getFreshness() {
    return freshness;
  }

  public void setFreshness(double freshness) {
    this.freshness = freshness;
  }

  public double getSecurity() {
    return security;
  }

  public void setSecurity(double security) {
    this.security = security;
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
