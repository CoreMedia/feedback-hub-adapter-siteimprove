package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.cms.studio.feedbackhub.model.FeedbackItem;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.DateUtil;
import ext.StringUtil;

import ext.panel.Panel;

public class SiteimprovePreviewTabBase extends Panel {
  [Bindable]
  public var feedbackItem:FeedbackItem;

  [Bindable]
  public var contentExpression:ValueExpression;

  public function SiteimprovePreviewTabBase(config:SiteimprovePreviewTab = null) {
    super(config);
  }

  internal function getNextCrawlDateExpression(config:SiteimprovePreviewTab):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var date:Date = ValueExpressionFactory.create('previewSummary.crawlStatus.next_crawl', config.feedbackItem).getValue();
      if(!date) {
        return getResource('feedbackItemPanel_siteimprove_unknown');
      }

      return DateUtil.format(date, resourceManager.getString('com.coremedia.cms.editor.Editor', 'shortDateFormat'));
    });
  }

  internal function getResource(resourceName:String):String {
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', resourceName);
  }

  internal function getLastCrawlDateExpression(config:SiteimprovePreviewTab):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var date:Date = ValueExpressionFactory.create('previewSummary.pageDetailsDocument.summary.page.last_seen', config.feedbackItem).getValue();
      if(!date) {
        return getResource('feedbackItemPanel_siteimprove_unknown');
      }

      return getDateDiff(date);
    });
  }

  internal function getSecondLastCrawlDateExpression(config:SiteimprovePreviewTab):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var date:* = ValueExpressionFactory.create('last.previewSummary.pageDetailsDocument.summary.page.last_seen', config.feedbackItem).getValue();

      if(!date) {
        return null;
      }

      return getDateDiff(date);
    });
  }

  private function getDateDiff(date:Date):String {
    if (!date) {
      return null;
    }

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

    var days:Number = hours / 24;
    if (Math.round(days) === 1) {
      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_one_day_ago'), Math.round(days));
    }
    return StringUtil.format(getResource('feedbackItemPanel_siteimprove_days_ago'), Math.round(days));
  }

  internal function openPageInSiteimprove():void {
    var url:String = ValueExpressionFactory.create('previewSummary.pageDetailsDocument._siteimprove.seo.page_report.href', feedbackItem).getValue();
    window.open(url, '_blank');
  }

}
}
