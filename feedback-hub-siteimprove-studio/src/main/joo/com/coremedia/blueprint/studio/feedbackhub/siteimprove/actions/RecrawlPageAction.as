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

  private var checkStatusOnly:Boolean;

  public function RecrawlPageAction(config:RecrawlPageAction = null) {
    super(RecrawlPageAction(ActionConfigUtil.extendConfig(config, 'recrawlPage', {handler: recrawlPage})));
    feedbackExpression = config.feedbackExpression;
    preview = config.preview;
  }

  override public function addComponent(comp:Component):void {
    super.addComponent(comp);
    owner = comp;
  }

  public function setCheckStatusOnly(checkStatusOnly:Boolean):void {
    this.checkStatusOnly = checkStatusOnly
  }

  private function recrawlPage():void {
    var content:Content = getContents()[0];
    if (!content) {
      return;
    }

    var loadMaskTarget:Component;
    if (owner.isVisible()) {
      loadMaskTarget = owner;
      setDisabled(true);
    } else {
      //the recrawl action is programmtically triggered.
      //show the load mask over the load feedback button
      loadMaskTarget = getFeedbackButton();
      loadMaskTarget.setDisabled(true);
    }

    var JOB_TYPE:String = "recrawlPage";
    var pageId:String = feedbackExpression.extendBy("page.id").getValue();

    jobService.executeJob(
            new GenericRemoteJob(JOB_TYPE, {content: content, preview: preview, pageId: pageId, checkStatusOnly: checkStatusOnly}),
            //on success
            function (result:Object):void {

              if (loadMask && !loadMask.destroyed) {
                loadMask.destroy();
              }
              if (owner.isVisible()) {
                setDisabled(false);
              } else {
                loadMaskTarget.setDisabled(false);
              }

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
    loadMaskConfig.target = loadMaskTarget;
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
    var feebackButton:Button = getFeedbackButton();
    feebackButton.handler();
  }

  private function getFeedbackButton():Button {
    var feedbackGroupPanel:FeedbackGroupPanel = owner.up(createComponentSelector()._xtype(FeedbackGroupPanel.xtype).build())
            as FeedbackGroupPanel;
    var feebackButton:Button = feedbackGroupPanel.down(createComponentSelector().itemId("loadFeedbackButton").build()) as Button;
    return feebackButton;
  }
}
}
