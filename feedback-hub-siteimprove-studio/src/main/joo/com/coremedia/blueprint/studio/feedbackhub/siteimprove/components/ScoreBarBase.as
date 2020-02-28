package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.ScoreUtil;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.container.Container;
import ext.form.field.DisplayField;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class ScoreBarBase extends Container {

  public const BAR_ITEM_ID:String = "barLabel";

  private const BAR_HEIGHT:Number = 11;

  [Bindable]
  public var label:String;

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var color:String;

  [Bindable]
  public var showDiff:Boolean;

  [Bindable]
  public var showPoints:Boolean;

  public function ScoreBarBase(config:ScoreBar = null) {
    super(config);
  }

  override protected function afterRender():void {
    super.afterRender();

    var score:Number = bindTo.getValue();
    var field:DisplayField = queryById(BAR_ITEM_ID) as DisplayField;
    field.setValue('<div style="width: 100%;text-align: center;">' +
            '<div style="height:' + BAR_HEIGHT + 'px;background-color:#dcdbdb;width: 100%;"></div>' +
            '<div style="height:' + BAR_HEIGHT + 'px;margin-top:-' + BAR_HEIGHT + 'px;background-color:' +
            (color || ScoreUtil.getColor(score)) + ';width: ' + ScoreUtil.formatScore(score) + '%;"></div>' +
            '</div>');
  }

  internal function getScoreVisibleExpression(config:ScoreBar):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():Boolean {
      if (!config.showDiff) {
        return false;
      }
      return ScoreUtil.getLastExpression(config.bindTo).getValue();
    });
  }
 }
}
