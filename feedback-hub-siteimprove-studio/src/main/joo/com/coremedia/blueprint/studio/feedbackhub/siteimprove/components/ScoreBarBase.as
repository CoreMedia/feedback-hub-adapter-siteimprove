package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.blueprint.studio.feedbackhub.siteimprove.ScoreUtil;
import com.coremedia.cms.studio.feedbackhub.model.FeedbackItem;
import com.coremedia.ui.data.PropertyPathExpression;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.container.Container;
import ext.form.field.DisplayField;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class ScoreBarBase extends Container {

  public const BAR_ITEM_ID:String = "barLabel";

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

    var barHeight:Number = 11;
    var score:Number = bindTo.getValue();
    var field:DisplayField = queryById(BAR_ITEM_ID) as DisplayField;
    field.setValue('<div style="width: 100%;text-align: center;margin-top: -6px;">' +
            '<div style="height:' + barHeight + 'px;border-radius: 2px;background-color:#dcdbdb;width: 100%;"></div>' +
            '<div style="height:' + barHeight + 'px;margin-top:-' + barHeight + 'px;border-radius: 2px;background-color:' +
            (color || ScoreUtil.getColor(score)) + ';width: ' + ScoreUtil.formatScore(score) + '%;"></div>' +
            '</div>');
  }

  internal function getLastExpression(config:ScoreBar):ValueExpression {
    var ppe:PropertyPathExpression = config.bindTo as PropertyPathExpression;
    var feedbackItem:FeedbackItem = ppe.getBean() as FeedbackItem;
    var propertyPathArcs:Array = ppe.getPropertyPathArcs().concat();
    //previewSummary.dciOverallScoreDocument.seo.total --> previewSummary.last.dciOverallScoreDocument.seo.total
    propertyPathArcs.splice(1, 0, "last");
    return ValueExpressionFactory.create(propertyPathArcs.join('.'), feedbackItem);
  }
}
}
