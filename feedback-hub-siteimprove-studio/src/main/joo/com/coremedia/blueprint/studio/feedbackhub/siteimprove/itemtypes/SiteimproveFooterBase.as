package com.coremedia.blueprint.studio.feedbackhub.siteimprove.itemtypes {
import com.coremedia.cap.common.JobExecutionError;
import com.coremedia.cap.common.impl.GenericRemoteJob;
import com.coremedia.cap.common.jobService;
import com.coremedia.cms.studio.feedbackhub.components.itempanels.FeedbackItemPanel;
import com.coremedia.cms.studio.feedbackhub.util.FeedbackHelper;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.skins.LoadMaskSkin;

import ext.LoadMask;
import ext.button.Button;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class SiteimproveFooterBase extends FeedbackItemPanel {

  public static const RECRAWL_BUTTON_ITEM_ID:String = "recrawlButton";
  public static const RELOAD_BUTTON_ITEM_ID:String = "reloadButton";

  //they match the collection id of the feedback items
  public static const PREVEW_ITEM_ID:String = "preview";
  public static const LIVE_ITEM_ID:String = "comparison";

  private var loadMask:LoadMask;

  public function SiteimproveFooterBase(config:SiteimproveFooter = null) {
    super(config);
  }

  internal function getLastPreviewCrawlDateExpression(config:SiteimproveFooter):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():String {
      var label:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_preview_site');
      var time:Number = config.feedbackItem['lastPreviewUpdate'];
      if (time > 0) {
        var date:Date = new Date(time);
        label = label + ": " + FeedbackHelper.getDateDiff(date);
      } else{
        label = label + ": " +resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_not_crawled');
      }

      return label;
    });
  }

  internal function getLastLiveCrawlDateExpression(config:SiteimproveFooter):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():String {
      var label:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_live_site');
      var time:Number = config.feedbackItem['lastLiveUpdate'];
      if (time > 0) {
        var date:Date = new Date(time);
        label = label + ": " + FeedbackHelper.getDateDiff(date);
      } else{
        label = label + ": " +resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_not_crawled');
      }

      return label;
    });
  }

  protected function recrawlPage(b:Button):void {
    var JOB_TYPE:String = "recrawlPage";
    var pageId:String = feedbackItem['previewPageId'];
    var preview:Boolean = getFeedbackGroupPanel().getSubTabPanel().getActiveTab().itemId === PREVEW_ITEM_ID;
    if(!preview) {
      pageId = feedbackItem['livePageId'];
    }

    b.setDisabled(true);
    jobService.executeJob(
            new GenericRemoteJob(JOB_TYPE, {
              content: contentExpression.getValue(),
              preview: preview,
              pageId: pageId,
              checkStatusOnly: false
            }),
            //on success
            function (result:Object):void {

              if (loadMask && !loadMask.destroyed) {
                loadMask.destroy();
              }

              b.setDisabled(false);
              reload();
            },
            //on error
            function (error:JobExecutionError):void {
              trace('[ERROR]', "Error loading Feedback : " + error);
              loadMask.hide();
              setDisabled(false);
            }
    );

    if(!loadMask) {
      var loadMaskConfig:LoadMask = LoadMask({});
      loadMaskConfig.ui = LoadMaskSkin.TRANSPARENT.getSkin();
      loadMaskConfig.msg = '';
      loadMaskConfig.target = b;
      loadMask = new LoadMask(loadMaskConfig);
    }

    loadMask.show();
  }


  override protected function onDestroy():void {
    super.onDestroy();
    if(loadMask) {
      loadMask.destroy();
    }
  }
}
}
