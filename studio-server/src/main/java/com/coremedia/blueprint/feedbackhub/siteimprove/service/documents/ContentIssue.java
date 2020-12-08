package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import java.util.List;

/**
 *
 */
public class ContentIssue {
  private String name;
  private List<Match> matches;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Match> getMatches() {
    return matches;
  }

  public void setMatches(List<Match> matches) {
    this.matches = matches;
  }
}
