package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Status information of a page check.
 */
public class PageCheckStatusDocument {

  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("url")
  private String url;

  @JsonProperty("check_allowed")
  private Boolean checkAllowed;

  @JsonProperty("check_paused")
  private Boolean checkPaused;

  @JsonProperty("checking_now")
  private Boolean checkingNow;

  @JsonProperty("first_seen")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date firstSeen;

  @JsonProperty("last_changed")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastChanged;

  @JsonProperty("last_seen")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastSeen;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Boolean getCheckAllowed() {
    return checkAllowed;
  }

  public void setCheckAllowed(Boolean checkAllowed) {
    this.checkAllowed = checkAllowed;
  }

  public Boolean getCheckPaused() {
    return checkPaused;
  }

  public void setCheckPaused(Boolean checkPaused) {
    this.checkPaused = checkPaused;
  }

  public Boolean getCheckingNow() {
    return checkingNow;
  }

  public void setCheckingNow(Boolean checkingNow) {
    this.checkingNow = checkingNow;
  }

  public Date getFirstSeen() {
    return firstSeen;
  }

  public void setFirstSeen(Date firstSeen) {
    this.firstSeen = firstSeen;
  }

  public Date getLastChanged() {
    return lastChanged;
  }

  public void setLastChanged(Date lastChanged) {
    this.lastChanged = lastChanged;
  }

  public Date getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(Date lastSeen) {
    this.lastSeen = lastSeen;
  }
}
