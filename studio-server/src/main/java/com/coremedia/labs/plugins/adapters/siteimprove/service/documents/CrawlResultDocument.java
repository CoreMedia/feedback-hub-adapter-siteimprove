package com.coremedia.labs.plugins.adapters.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result of a site crawl request
 */
public class CrawlResultDocument {

  @JsonProperty("message")
  private String message;
  @JsonProperty("status_code")
  private int statusCode;
  @JsonProperty("success")
  private Boolean success;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }
}
