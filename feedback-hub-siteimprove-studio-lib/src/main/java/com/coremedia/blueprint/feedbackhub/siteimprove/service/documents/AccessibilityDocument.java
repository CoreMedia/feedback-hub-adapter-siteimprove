package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class AccessibilityDocument {
  @JsonProperty("errorpages")
  private double errorpages;

  @JsonProperty("errors")
  private double errors;

  @JsonProperty("total")
  private double total;

  @JsonProperty("warnings")
  private double warnings;

  public double getErrorpages() {
    return errorpages;
  }

  public void setErrorpages(double errorpages) {
    this.errorpages = errorpages;
  }

  public double getErrors() {
    return errors;
  }

  public void setErrors(double errors) {
    this.errors = errors;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public double getWarnings() {
    return warnings;
  }

  public void setWarnings(double warnings) {
    this.warnings = warnings;
  }

}
