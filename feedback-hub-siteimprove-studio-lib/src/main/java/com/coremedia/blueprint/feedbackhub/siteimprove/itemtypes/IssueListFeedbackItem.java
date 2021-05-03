package com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Seov2IssueDocument;
import com.coremedia.feedbackhub.items.FeedbackItemImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class IssueListFeedbackItem extends FeedbackItemImpl {

  private List<Seov2IssueDocument> issues = new ArrayList<>();

  public IssueListFeedbackItem(String collection, String title) {
    super(collection, title, null);
  }

  @Override
  public String getType() {
    return "siteimproveIssueList";
  }

  public List<Seov2IssueDocument> getIssues() {
    return issues;
  }

  public void addIssue(Seov2IssueDocument issueItem) {
    this.issues.add(issueItem);
  }
}
