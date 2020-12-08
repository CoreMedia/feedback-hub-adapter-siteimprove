package com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes;

import com.coremedia.feedbackhub.items.FeedbackItemImpl;
import com.coremedia.feedbackhub.items.GaugeFeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 * {@link FeedbackItem} that will be rendered as scoring wheel
 */
public class ComparingGaugeFeedbackItem extends FeedbackItemImpl {
  private final GaugeFeedbackItem gauge1;
  private final GaugeFeedbackItem gauge2;

  ComparingGaugeFeedbackItem(String collection, String title, String help, GaugeFeedbackItem gauge1, GaugeFeedbackItem gauge2) {
    super(collection, title, help);
    this.gauge1 = gauge1;
    this.gauge2 = gauge2;
  }

  public GaugeFeedbackItem getGauge1() {
    return gauge1;
  }

  public GaugeFeedbackItem getGauge2() {
    return gauge2;
  }

  @Override
  public String getType() {
    return "siteimproveComparingGauge";
  }
}
