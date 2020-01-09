package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.ScoreUtil;
import com.coremedia.ui.data.ValueExpression;

import ext.container.Container;
import ext.form.field.DisplayField;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class ScoreBarBase extends Container {

  public const BAR_ITEM_ID:String = "barLabel";

  [Bindable]
  public var label:String;

  [Bindable]
  public var color:String;

  [Bindable]
  public var bindTo:ValueExpression;

  public function ScoreBarBase(config:ScoreBar = null) {
    super(config);
  }

  override protected function afterRender():void {
    super.afterRender();


    var barHeight:Number = 8;
    var score:Number = bindTo.getValue();
    var field:DisplayField = queryById(BAR_ITEM_ID) as DisplayField;
    field.setValue('<div style="width: 100%;text-align: center;">' +
            '<div style="height:' + barHeight + 'px;background-color:#dcdbdb;width: 100%;"></div>' +
            '<div style="height:' + barHeight + 'px;margin-top:-' + barHeight + 'px;background-color:' +
            color + ';width: ' + ScoreUtil.formatScore(score) + '%;"></div>' +
            '</div>');
  }
}
}
