package com.coremedia.labs.plugins.adapters.siteimprove.itemtypes;

import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 * Shown when Siteimprove content has not been finished.
 */
public class UnpublishedContentFeedbackItem implements FeedbackItem {

  private final String collection;

  public UnpublishedContentFeedbackItem(String collection) {
    this.collection = collection;
  }

  @Override
  public String getType() {
    return "siteimproveUnpublished";
  }

  @Override
  public String getCollection() {
    return collection;
  }
}
