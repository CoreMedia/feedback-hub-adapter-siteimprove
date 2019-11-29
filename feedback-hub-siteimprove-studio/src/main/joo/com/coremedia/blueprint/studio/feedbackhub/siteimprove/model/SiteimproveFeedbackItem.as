package com.coremedia.blueprint.studio.feedbackhub.siteimprove.model {
import com.coremedia.cms.studio.feedbackhub.model.*;

public class SiteimproveFeedbackItem extends FeedbackItem {
  public var summary:Object;

  //noinspection ReservedWordAsName
  public function SiteimproveFeedbackItem(id:String, type:String, summary:Object) {
    super(id, type);
    this.summary = summary;
  }
}
}
