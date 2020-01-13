package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;

public class SiteimproveFeedbackItemPanelBase extends FeedbackItemPanel {

  private var feedbackLoadedExpression:ValueExpression;
  private var feedbackNotLoadedExpression:ValueExpression;

  public function SiteimproveFeedbackItemPanelBase(config:SiteimproveFeedbackItemPanel = null) {
    super(config);
  }

  internal function getFeedbackLoadedExpression(config:SiteimproveFeedbackItemPanel):ValueExpression {
    if (!feedbackLoadedExpression) {
      feedbackLoadedExpression = ValueExpressionFactory.createFromFunction(function ():Boolean {
        return SiteimproveFeedbackItem(config.feedbackItem).previewSummary;
      });
    }
    return feedbackLoadedExpression;
  }

  internal function getFeedbackNotLoadedExpression(config:SiteimproveFeedbackItemPanel):ValueExpression {
    if (!feedbackNotLoadedExpression) {
      feedbackNotLoadedExpression = ValueExpressionFactory.createFromFunction(function ():Boolean {
        return !SiteimproveFeedbackItem(config.feedbackItem).previewSummary;
      });
    }
    return feedbackNotLoadedExpression;
  }

  protected function getStatusIconClass(config:SiteimproveFeedbackItemPanel):String {
    var previewScore:Number = ValueExpressionFactory.create('previewSummary.dciOverallScoreDocument.total', config.feedbackItem).getValue();
    var liveScore:Number = ValueExpressionFactory.create('liveSummary.dciOverallScoreDocument.total', config.feedbackItem).getValue();

    if (!previewScore || !liveScore) {
      return 'exclamation_mark';
    }

    if (previewScore < liveScore) {
      return 'exclamation_mark';
    }

    return 'approve';
  }

  protected function getStatusMessage(config:SiteimproveFeedbackItemPanel):String {
    var previewScore:Number = ValueExpressionFactory.create('previewSummary.dciOverallScoreDocument.total', config.feedbackItem).getValue();
    var liveScore:Number = ValueExpressionFactory.create('liveSummary.dciOverallScoreDocument.total', config.feedbackItem).getValue();

    if (!previewScore || !liveScore) {
      return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_broken_score');
    }


    if (previewScore < liveScore) {
      var msg:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_lose_score');
      return StringUtil.format(msg, (liveScore - previewScore).toFixed(2));
    }

    var msg2:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_gain_score');
    return StringUtil.format(msg2, (previewScore - liveScore).toFixed(2));
  }

  //TODO fix layout glitch caused by bindComponents panel of issues
  override protected function afterRender():void {
    super.afterRender();
    window.setTimeout(function ():void {
      updateLayout();
    }, 200);
  }
}
}
