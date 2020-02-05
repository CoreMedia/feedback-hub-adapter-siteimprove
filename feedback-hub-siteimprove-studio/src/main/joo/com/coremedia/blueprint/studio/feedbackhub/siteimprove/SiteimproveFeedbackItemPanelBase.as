package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.DateUtil;

import ext.StringUtil;

import js.JSON;

public class SiteimproveFeedbackItemPanelBase extends FeedbackItemPanel {

  private var feedbackLoadedExpression:ValueExpression;
  private var feedbackNotLoadedExpression:ValueExpression;

  private static const PREVIEW_LASTSEEN:String = 'previewSummary.pageDetailsDocument.summary.page.last_seen';

  public function SiteimproveFeedbackItemPanelBase(config:SiteimproveFeedbackItemPanel = null) {
    if(!config.feedbackItem.isStub && getLastSeenDate(config.feedbackItem)) {
      var lastFeedbackItem:Object = getLastFeedbackItem(config);
      if (lastFeedbackItem) {
        if (getLastSeenDate(lastFeedbackItem) !== getLastSeenDate(config.feedbackItem))  {
          config.feedbackItem['last'] = lastFeedbackItem;
        }
      }
      save(config)
    }
    super(config);

  }

  private function getLastSeenDate(item:Object) {
    var value:* = ValueExpressionFactory.create(PREVIEW_LASTSEEN, item).getValue();

    //TODO:this is a workaround
    //when from the parsed json then the value is a string like "2020-02-05T12:51:51.000Z"
    //only without the trailing 'Z' the string can be parsed.
    if (value is String) {
      if (StringUtil.endsWith(value, 'Z')) {
        value = String(value).substr(0, String(value).length - 1);
      }
      value = DateUtil.parse(value, "Y-m-dTg:i:s.000");
    }
    return value;
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

  private function save(config:SiteimproveFeedbackItemPanel):void {
    window.sessionStorage.setItem(getContentKey(config), JSON.stringify(config.feedbackItem));
  }

  private function getLastFeedbackItem(config:SiteimproveFeedbackItemPanel):Object {
    return JSON.parse(window.sessionStorage.getItem(getContentKey(config)));
  }

  private function getContentKey(config:SiteimproveFeedbackItemPanel):String {
    return "siteImprove." + config.contentExpression.getValue();
  }
}
}
