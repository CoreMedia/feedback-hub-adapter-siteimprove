package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

public class SiteimproveFeedbackItemPanelBase extends FeedbackItemPanel{

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

  //TODO fix layout glitch caused by bindComponents panel of issues
  override protected function afterRender():void {
    super.afterRender();
    window.setTimeout(function():void {
      updateLayout();
    }, 200);
  }

  internal function openSiteimprove():void {
    var url:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimproveSettings', 'siteimprove_url');
    window.open(url, '_blank');
  }
}
}
