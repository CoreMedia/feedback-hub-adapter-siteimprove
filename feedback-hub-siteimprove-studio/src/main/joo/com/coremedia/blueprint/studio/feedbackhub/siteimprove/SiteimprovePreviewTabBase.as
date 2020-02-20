package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.actions.RecrawlPageAction;
import com.coremedia.cms.studio.feedbackhub.model.FeedbackItem;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.util.createComponentSelector;

import ext.DateUtil;
import ext.StringUtil;
import ext.button.Button;
import ext.panel.Panel;

public class SiteimprovePreviewTabBase extends Panel {
  [Bindable]
  public var feedbackItem:FeedbackItem;

  [Bindable]
  public var contentExpression:ValueExpression;

  private var feedbackErrorExpression:ValueExpression;
  private var recheckingExpression:ValueExpression;

  public function SiteimprovePreviewTabBase(config:SiteimprovePreviewTab = null) {
    super(config);
  }

  override protected function beforeRender():void {
    var checkingNowExpression:ValueExpression = ValueExpressionFactory.create("previewSummary.pageDetailsDocument.summary.page.checking_now", feedbackItem);
    if (checkingNowExpression.getValue()) {
      checkPageStatus();
    }
  }

  private function checkPageStatus():void {
    getRecheckingExpression().setValue(true);
    var recrawlButton:Button = down(createComponentSelector().itemId(SiteimprovePreviewTab.RECRAWL_BUTTON_ITEM_ID).build()) as Button;
    var recrawlAction:RecrawlPageAction = recrawlButton.baseAction as RecrawlPageAction;
    recrawlAction.setCheckStatusOnly(true);
    recrawlButton.handler();
    recrawlAction.setCheckStatusOnly(false);
  }

  private function getRecheckingExpression():ValueExpression {
    if (!recheckingExpression) {
      recheckingExpression = ValueExpressionFactory.createFromValue(false);
    }
    return recheckingExpression;
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

  internal function getShowErrorExpression(config:SiteimprovePreviewTab):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():Boolean {
      if (getRecheckingExpression().getValue()) {
        return false;
      }
      return getFeedbackErrorExpression(config).getValue();
    });
  }

  private function getFeedbackErrorExpression(config:SiteimprovePreviewTab):ValueExpression {
    if (!feedbackErrorExpression) {
      feedbackErrorExpression = ValueExpressionFactory.create("previewSummary.page.errorCode", config.feedbackItem);
    }
    return feedbackErrorExpression;
  }

  internal function getFeedbackNoErrorExpression(config:SiteimprovePreviewTab):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():Boolean {
      return !getFeedbackErrorExpression(config).getValue();
    });
  }

  internal function getErrorMessage(config:SiteimprovePreviewTab):String {
    var errorCode:String = getFeedbackErrorExpression(config).getValue();
    return getResource("feedbackItemPanel_error_SiteimproveFeedbackHubErrorCode_" + errorCode);
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
