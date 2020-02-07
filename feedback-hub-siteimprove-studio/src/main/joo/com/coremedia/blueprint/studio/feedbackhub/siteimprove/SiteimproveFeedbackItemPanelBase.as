package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;

public class SiteimproveFeedbackItemPanelBase extends FeedbackItemPanel {

  private var feedbackLoadedExpression:ValueExpression;
  private var feedbackNotLoadedExpression:ValueExpression;

  private static const PREVIEW_LASTSEEN:String = 'previewSummary.pageDetailsDocument.summary.page.last_seen';

  public function SiteimproveFeedbackItemPanelBase(config:SiteimproveFeedbackItemPanel = null) {

    if(!config.feedbackItem.isStub && getLastSeenDate(config.feedbackItem)) {
      var lastFeedbackItem:Object = StoreUtil.getFeedbackItem(config);
      if (lastFeedbackItem) {
        if (getLastSeenDate(lastFeedbackItem) !== getLastSeenDate(config.feedbackItem))  {
          lastFeedbackItem['last'] = null;
          config.feedbackItem['last'] = lastFeedbackItem;
        }
      }
      StoreUtil.saveFeedbackItem(config);
    }
    super(config);

  }

  internal function getEmptyText(config:SiteimproveFeedbackItemPanel):String {
    return config.feedbackItem.isStub
            ? getResource('siteimproveFeedbackItemPanel_no_feedback_loaded_text')
            : getResource('siteimproveFeedbackItemPanel_no_feedback_available_text');
  }

  private function getLastSeenDate(item:Object):Date {
    return ValueExpressionFactory.create(PREVIEW_LASTSEEN, item).getValue();
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
    window.setTimeout(function ():void {
      updateLayout();
    }, 200);
  }

  internal function openSiteimprove():void {
    var url:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimproveSettings', 'siteimprove_url');
    var siteId:String = ValueExpressionFactory.create('previewSummary.siteId', feedbackItem).getValue();
    url = StringUtil.format(url, siteId);
    window.open(url, '_blank');
  }

  internal function getResource(resourceName:String):String {
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', resourceName);
  }
}
}
