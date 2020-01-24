package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.model.SiteimproveFeedbackItem;
import com.coremedia.cms.editor.sdk.util.TimeUtil;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.DateUtil;

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
      return getResource('feedbackItemPanel_siteimprove_broken_score');
    }


    if (previewScore < liveScore) {
      var msg:String = getResource('feedbackItemPanel_siteimprove_lose_score');
      return StringUtil.format(msg, (liveScore - previewScore).toFixed(2));
    }

    var msg2:String = getResource('feedbackItemPanel_siteimprove_gain_score');
    return StringUtil.format(msg2, (previewScore - liveScore).toFixed(2));
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
    window.open(url, '_blank');
  }

  internal function getLastCrawlDateExpression(config:SiteimproveFeedbackItemPanel):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var date:Date = ValueExpressionFactory.create('previewSummary.pageDetailsDocument.summary.page.last_seen', config.feedbackItem).getValue();
      if(!date) {
        return getResource('feedbackItemPanel_siteimprove_preview_unknown');
      }

      if (TimeUtil.isToday(date)) {
        return getResource('feedbackItemPanel_siteimprove_preview_today');
      }

      var diff:Number = Math.ceil((new Date().getTime() - date.getTime()) / 86400000); //in days
      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_preview_days_ago'), diff);
    });
  }

  internal function getNextCrawlDateExpression(config:SiteimproveFeedbackItemPanel):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():String {
      var date:Date = ValueExpressionFactory.create('previewSummary.crawlStatus.next_crawl', config.feedbackItem).getValue();
      if(!date) {
        return getResource('feedbackItemPanel_siteimprove_preview_unknown');
      }

      return StringUtil.format(getResource('feedbackItemPanel_siteimprove_preview_date'),
              DateUtil.format(date, resourceManager.getString('com.coremedia.cms.editor.Editor', 'shortDateFormat')));
    });
  }

  internal function getResource(resourceName:String):String {
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', resourceName);
  }
}
}
