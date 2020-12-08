package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import java.util.List;

/**
 *
 */
public class ContentIssueOccurence {
  private String mode;
  private List<Occurrence> occurrences;

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public List<Occurrence> getOccurrences() {
    return occurrences;
  }

  public void setOccurrences(List<Occurrence> occurrences) {
    this.occurrences = occurrences;
  }
}
