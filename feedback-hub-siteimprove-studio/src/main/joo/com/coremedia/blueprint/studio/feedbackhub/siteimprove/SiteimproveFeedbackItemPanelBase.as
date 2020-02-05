package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.cms.studio.feedbackhub.model.FeedbackItem;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;

import js.JSON;

public class SiteimproveFeedbackItemPanelBase extends FeedbackItemPanel {

  private var feedbackLoadedExpression:ValueExpression;
  private var feedbackNotLoadedExpression:ValueExpression;

  private static const PREVIEW_LASTSEEN:String = 'previewSummary.pageDetailsDocument.summary.page.last_seen';

  public function SiteimproveFeedbackItemPanelBase(config:SiteimproveFeedbackItemPanel = null) {
    if(!config.feedbackItem.isStub && config.feedbackItem[PREVIEW_LASTSEEN]) {
      var lastFeedbackItem:FeedbackItem = getLastFeedbackItem();
      if (lastFeedbackItem) {
        if (lastFeedbackItem[PREVIEW_LASTSEEN] !== config.feedbackItem[PREVIEW_LASTSEEN])  {
          config.feedbackItem['last'] = lastFeedbackItem;
        }
      }
      save(config.feedbackItem)
    }
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

  private function save(feebackItem:FeedbackItem):void {
    window.sessionStorage.setItem(getContentKey(), JSON.stringify(feebackItem));
  }

  private function getLastFeedbackItem():FeedbackItem {
    return JSON.parse(window.sessionStorage.getItem(getContentKey())) as FeedbackItem;
  }

  private function getContentKey():String {
    return "siteImprove." + contentExpression.getValue();
  }
}
}
