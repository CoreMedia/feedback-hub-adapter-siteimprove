package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.cms.studio.feedbackhub.model.FeedbackItem;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.DateUtil;
import ext.StringUtil;

import ext.panel.Panel;

public class SiteimproveComparisonTabBase extends Panel {
  [Bindable]
  public var feedbackItem:FeedbackItem;

  [Bindable]
  public var contentExpression:ValueExpression;

  public function SiteimproveComparisonTabBase(config:SiteimproveComparisonTab = null) {
    super(config);
  }

  internal function getNextCrawlDateExpression(config:SiteimproveComparisonTab, live:Boolean = false):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var prefix:String = live ? 'liveSummary' : 'previewSummary';
      var expression:String = prefix + '.crawlStatus.next_crawl';
      var date:Date = ValueExpressionFactory.create(expression, config.feedbackItem).getValue();
      var dateString:String = date ?
              DateUtil.format(date, resourceManager.getString('com.coremedia.cms.editor.Editor', 'shortDateFormat')) :
              getResource('feedbackItemPanel_siteimprove_unknown');
      var siteResourceName:String = live ? 'feedbackItemPanel_siteimprove_live_site' : 'feedbackItemPanel_siteimprove_preview_site';
      return getResource(siteResourceName) + " - " + dateString;
    });
  }

  internal function getResource(resourceName:String):String {
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', resourceName);
  }

  internal function getLastCrawlDateExpression(config:SiteimproveComparisonTab, live:Boolean, sitePrefix:Boolean):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var prefix:String = live ? 'liveSummary' : 'previewSummary';
      var expression:String = prefix + '.pageDetailsDocument.summary.page.last_seen';
      var date:Date = ValueExpressionFactory.create(expression, config.feedbackItem).getValue();
      var diffString:String = date ? getDateDiff(date) : getResource('feedbackItemPanel_siteimprove_unknown');
      if (sitePrefix) {
        var siteResourceName:String = live ? 'feedbackItemPanel_siteimprove_live_site' : 'feedbackItemPanel_siteimprove_preview_site';
        diffString = getResource(siteResourceName) + ": " + diffString;
      }

      return diffString;
    });
  }

  private function getDateDiff(date:Date):String {
    var seconds:Number = (new Date().getTime() - date.getTime()) / 1000;
    if (seconds < 60) {
      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_seconds_ago'), Math.round(seconds));
    }

    var minutes:Number = seconds / 60;
    if (minutes < 60) {
      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_minutes_ago'), Math.round(minutes));
    }

    var hours:Number = minutes / 60;
    if (hours < 24) {
      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_hours_ago'), Math.round(hours));
    }

    var days:Number = hours / 60;
    return StringUtil.format(getResource('feedbackItemPanel_siteimprove_days_ago'), Math.round(days));
  }


}
}
