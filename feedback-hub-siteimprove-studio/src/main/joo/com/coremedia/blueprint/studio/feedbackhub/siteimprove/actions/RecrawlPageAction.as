package com.coremedia.blueprint.studio.feedbackhub.siteimprove.actions {
import com.coremedia.cap.common.JobExecutionError;
import com.coremedia.cap.common.impl.GenericRemoteJob;
import com.coremedia.cap.common.jobService;
import com.coremedia.cms.editor.sdk.actions.ActionConfigUtil;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.skins.LoadMaskSkin;

import ext.Action;
import ext.Component;
import ext.LoadMask;

[ResourceBundle('com.coremedia.cms.studio.feedbackhub.actions.FeedbackHubActions')]
[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class RecrawlPageAction extends Action {

  [Bindable]
  public var feedbackExpression:ValueExpression;

  private var owner:Component;

  public function RecrawlPageAction(config:RecrawlPageAction = null) {
    super(RecrawlPageAction(ActionConfigUtil.extendConfig(config, 'recrawlPage', {handler: recrawlPage})));
    feedbackExpression = config.feedbackExpression;
  }

  override public function addComponent(comp:Component):void {
    super.addComponent(comp);
    owner = comp;
  }

  private function recrawlPage():void {
      setDisabled(true);
      var JOB_TYPE:String = "recrawlPage";
      jobService.executeJob(
              new GenericRemoteJob(JOB_TYPE, {}),
              //on success
              function (result:Object):void {
                feedbackExpression.setValue(result);
                if (loadMask && !loadMask.destroyed) {
                  loadMask.destroy();
                }
                setDisabled(false);
              },
              //on error
              function (error:JobExecutionError):void {
                trace('[ERROR]', "Error loading Feedback : " + error);
                feedbackExpression.setValue([{
                  type: 'error',
                  errorCode: error.getErrorCode()
                }]);
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
}
}
