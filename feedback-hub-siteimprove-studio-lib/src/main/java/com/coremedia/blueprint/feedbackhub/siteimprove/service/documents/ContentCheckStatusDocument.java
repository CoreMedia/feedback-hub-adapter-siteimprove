package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class ContentCheckStatusDocument {
  private boolean success;

  @JsonProperty("content_id")
  private String contentId;

  @JsonProperty("status_code")
  private int statusCode;

  private String message;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
