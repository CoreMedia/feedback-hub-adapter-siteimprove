package com.coremedia.blueprint.studio.feedbackhub.siteimprove.actions {
import com.coremedia.cap.common.JobExecutionError;
import com.coremedia.cap.common.impl.GenericRemoteJob;
import com.coremedia.cap.common.jobService;
import com.coremedia.cap.content.Content;
import com.coremedia.cms.editor.sdk.actions.ActionConfigUtil;
import com.coremedia.cms.editor.sdk.actions.ContentAction;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackGroupPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.skins.LoadMaskSkin;
import com.coremedia.ui.util.createComponentSelector;

import ext.Component;
import ext.LoadMask;
import ext.button.Button;

[ResourceBundle('com.coremedia.cms.studio.feedbackhub.actions.FeedbackHubActions')]
[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class RecrawlPageAction extends ContentAction {

  [Bindable]
  public var feedbackExpression:ValueExpression;

  [Bindable]
  public var preview:Boolean;

  private var owner:Component;

  public function RecrawlPageAction(config:RecrawlPageAction = null) {
    super(RecrawlPageAction(ActionConfigUtil.extendConfig(config, 'recrawlPage', {handler: recrawlPage})));
    feedbackExpression = config.feedbackExpression;
    preview = config.preview;
  }

  override public function addComponent(comp:Component):void {
    super.addComponent(comp);
    owner = comp;
  }

  private function recrawlPage():void {
    var content:Content = getContents()[0];
    if (!content) {
      return;
    }

    setDisabled(true);
    var JOB_TYPE:String = "recrawlPage";
    var pageId:String = feedbackExpression.extendBy("page.id").getValue();

    jobService.executeJob(
            new GenericRemoteJob(JOB_TYPE, {content: content, preview: preview, pageId: pageId}),
            //on success
            function (result:Object):void {

              if (loadMask && !loadMask.destroyed) {
                loadMask.destroy();
              }
              setDisabled(false);

              reloadFeedback();
            },
            //on error
            function (error:JobExecutionError):void {
              trace('[ERROR]', "Error loading Feedback : " + error);
              //TODO: Error handling

              destoryLoadmask(loadMask);
              setDisabled(false);
            }
    );
    var loadMaskConfig:LoadMask = LoadMask({});
    loadMaskConfig.ui = LoadMaskSkin.TRANSPARENT.getSkin();
    loadMaskConfig.msg = '';
    loadMaskConfig.target = owner;
    var loadMask:LoadMask = new LoadMask(loadMaskConfig);
    loadMask.show();

  }

  private static function destoryLoadmask(loadMask:LoadMask):void {
    if (loadMask && !loadMask.destroyed) {
      loadMask.destroy();
    }
  }

  /**
   * reload the feedback by triggering the reload feedback action
   * TODO: this is a workaround
   */
  private function reloadFeedback():void {
    var feedbackGroupPanel:FeedbackGroupPanel = owner.up(createComponentSelector()._xtype(FeedbackGroupPanel.xtype).build())
            as FeedbackGroupPanel;
    var feebackButton:Button = feedbackGroupPanel.down(createComponentSelector().itemId("loadFeedbackButton").build()) as Button;
    feebackButton.handler();
  }
}
}
