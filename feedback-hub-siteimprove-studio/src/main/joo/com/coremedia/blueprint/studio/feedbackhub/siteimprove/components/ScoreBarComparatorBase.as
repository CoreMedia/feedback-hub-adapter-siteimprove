package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.ui.data.ValueExpression;

import ext.container.Container;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class ScoreBarComparatorBase extends Container {

  [Bindable]
  public var label:String;

  [Bindable]
  public var bindToValue1:ValueExpression;

  [Bindable]
  public var bindToValue2:ValueExpression;

  [Bindable]
  public var color1:String;

  [Bindable]
  public var color2:String;

  public function ScoreBarComparatorBase(config:ScoreBarComparator = null) {
    super(config);
  }


  protected function getScore(config:ScoreBarComparator):String {
    var val1:Number = parseFloat(config.bindToValue1.getValue());
    var val2:Number = parseFloat(config.bindToValue1.getValue());
    var diff:Number = val1 - val2;
    return (val1 - val2).toFixed(2);
  }

  protected function getIconClass(config:ScoreBarComparator):String {
    var val1:Number = parseFloat(config.bindToValue1.getValue());
    var val2:Number = parseFloat(config.bindToValue1.getValue());
    var diff:Number = val1 - val2;

    var iconCls:String = 'long_arrow_right';
    if (diff > 0) {
      iconCls = 'long_arrow_up';
    }
    else if (diff < 0) {
      iconCls = 'long_arrow_down';
    }

    return iconCls;
  }
}
}
