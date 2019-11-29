package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentQualitySummaryDocument;
import com.coremedia.feedbackhub.provider.FeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Objects;

/**
 * {@link FeedbackItem} that returns Siteimprove feedback
 */
@DefaultAnnotation(NonNull.class)
public class SiteimproveFeedbackItem implements FeedbackItem {

  static final String TYPE = "siteimprove";
  private ContentQualitySummaryDocument summary;

  SiteimproveFeedbackItem(ContentQualitySummaryDocument contentQualitySummary) {

    this.summary = contentQualitySummary;
  }

  @Override
  public String getType() {
    return TYPE;
  }


  public ContentQualitySummaryDocument getSummary() {
    return summary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SiteimproveFeedbackItem that = (SiteimproveFeedbackItem) o;
    return Objects.equals(this, that);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    return "SiteimproveFeedbackItem";
  }
}
