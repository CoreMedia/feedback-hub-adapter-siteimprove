package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.actions.RecrawlPageAction;
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.studio.feedbackhub.model.FeedbackItem;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.util.createComponentSelector;

import ext.DateUtil;
import ext.StringUtil;
import ext.button.Button;
import ext.panel.Panel;

public class SiteimproveTabBase extends Panel {
  public static const RECRAWL_BUTTON_ITEM_ID:String = "recrawlButton";

  [Bindable]
  public var feedbackItem:FeedbackItem;

  [Bindable]
  public var contentExpression:ValueExpression;

  [Bindable]
  public var preview:Boolean;

  private var feedbackErrorExpression:ValueExpression;
  private var recheckingExpression:ValueExpression;
  private var issueListExpression:ValueExpression;

  public function SiteimproveTabBase(config:SiteimproveTabBase = null) {
    super(config);
  }

  override protected function beforeRender():void {
    var prefix:String = preview ? "previewSummary" : "liveSummary";
    var checkingNowExpression:ValueExpression = ValueExpressionFactory.create(prefix + ".pageDetailsDocument.summary.page.checking_now", feedbackItem);
    if (checkingNowExpression.getValue()) {
      checkPageStatus();
    }
  }

  private function checkPageStatus():void {
    getRecheckingExpression().setValue(true);
    var recrawlButton:Button = down(createComponentSelector().itemId(RECRAWL_BUTTON_ITEM_ID).build()) as Button;
    var recrawlAction:RecrawlPageAction = recrawlButton.baseAction as RecrawlPageAction;
    recrawlAction.setCheckStatusOnly(true);
    recrawlButton.handler();
    recrawlAction.setCheckStatusOnly(false);
  }

  internal function getResource(resourceName:String):String {
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', resourceName);
  }

  internal function getRecheckingExpression():ValueExpression {
    if (!recheckingExpression) {
      recheckingExpression = ValueExpressionFactory.createFromValue(false);
    }
    return recheckingExpression;
  }

  internal function getLastCrawlDateExpression(config:SiteimproveTabBase, live:Boolean, sitePrefix:Boolean, diff:Boolean = true):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():String {
      var prefix:String = live ? 'liveSummary' : 'previewSummary';
      var expression:String = prefix + '.pageDetailsDocument.summary.page.last_seen';
      var date:Date = ValueExpressionFactory.create(expression, config.feedbackItem).getValue();
      var diffString:String = date ? (diff ? getDateDiff(date) : DateUtil.format(date, resourceManager.getString('com.coremedia.cms.editor.Editor', 'dateFormat'))) :
              getResource('feedbackItemPanel_siteimprove_unknown');
      if (sitePrefix) {
        var siteResourceName:String = live ? 'feedbackItemPanel_siteimprove_live_site' : 'feedbackItemPanel_siteimprove_preview_site';
        diffString = getResource(siteResourceName) + ": " + diffString;
      }

      return diffString;
    });
  }

  internal function getShowErrorExpression(config:SiteimproveTabBase):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():Boolean {
      if (getRecheckingExpression().getValue()) {
        return false;
      }
      return getFeedbackErrorExpression(config).getValue();
    });
  }

  private function getFeedbackErrorExpression(config:SiteimproveTabBase):ValueExpression {
    if (!feedbackErrorExpression) {
      var prefix:String = config.preview ? "previewSummary" : "liveSummary";
      feedbackErrorExpression = ValueExpressionFactory.create(prefix + ".page.errorCode", config.feedbackItem);
    }
    return feedbackErrorExpression;
  }

  internal function getFeedbackNoErrorExpression(config:SiteimproveTabBase):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():Boolean {
      return !getFeedbackErrorExpression(config).getValue();
    });
  }

  internal function getErrorMessage(config:SiteimproveTabBase):String {
    var errorCode:String = getFeedbackErrorExpression(config).getValue();
    var prefix:String = config.preview ?
            "feedbackItemPanel_error_SiteimproveFeedbackHubErrorCode_" :
            "feedbackItemPanel_error_SiteimproveFeedbackHubErrorCode_live_";
    return getResource(prefix + errorCode);
  }

  internal function getIssueListExpression(config:SiteimproveTabBase):ValueExpression {
    if (!issueListExpression) {
      issueListExpression = ValueExpressionFactory.createFromFunction(function ():Array {
        var item:SiteimproveFeedbackItem = SiteimproveFeedbackItem(config.feedbackItem);

        var result:Array = [];
        var seoIssues:Array = item.previewSummary.seov2IssuesDocument.items;
        if(seoIssues) {
          result = result.concat(seoIssues);
        }

        var a11nIssues:Array = item.previewSummary.accessibilityIssuesDocument.items;
        if(a11nIssues) {
          result = result.concat(a11nIssues);
        }

        return result;
      });
    }
    return issueListExpression;
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
    if (Math.floor(days) === 1) {
      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_one_day_ago'), Math.floor(days));
    }
    return StringUtil.format(getResource('feedbackItemPanel_siteimprove_days_ago'), Math.floor(days));
  }

  internal function getStatusMessage(config:SiteimproveTabBase):String {
    var previewScore:Number = ValueExpressionFactory.create('previewSummary.dciOverallScoreDocument.total', config.feedbackItem).getValue();
    var liveScore:Number = ValueExpressionFactory.create('liveSummary.dciOverallScoreDocument.total', config.feedbackItem).getValue();

    if (!previewScore || !liveScore) {
      return getResource('feedbackItemPanel_siteimprove_broken_score');
    }

    if (previewScore < liveScore) {
      var msg:String = getResource('feedbackItemPanel_siteimprove_lose_score');
      return StringUtil.format(msg, (liveScore - previewScore).toFixed(2));
    }

    var msg2:String = getResource('feedbackItemPanel_siteimprove_gain_score');
    return StringUtil.format(msg2, (previewScore - liveScore).toFixed(2));
  }

  protected function getStatusIconClass(config:SiteimproveTabBase):String {
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

  internal function openPreviewPageInSiteimprove():void {
    var url:String = ValueExpressionFactory.create('previewSummary.pageDetailsDocument._siteimprove.seo.page_report.href', feedbackItem).getValue();
    window.open(url, '_blank');
  }

  internal function openLivePageInSiteimprove():void {
    var url:String = ValueExpressionFactory.create('liveSummary.pageDetailsDocument._siteimprove.seo.page_report.href', feedbackItem).getValue();
    window.open(url, '_blank');
  }

}
}
