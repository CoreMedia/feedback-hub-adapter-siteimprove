package com.coremedia.blueprint.studio.feedbackhub.siteimprove.itemtypes {
import com.coremedia.cms.studio.feedbackhub.components.itempanels.*;
import com.coremedia.cms.studio.feedbackhub.util.FeedbackHubPropertyNames;
import com.coremedia.ui.skins.DisplayFieldSkin;

import ext.StringUtil;

[ResourceBundle('com.coremedia.cms.studio.feedbackhub.FeedbackHub')]
public class ComparingGaugeItemPanelBase extends FeedbackItemPanel {

  public function ComparingGaugeItemPanelBase(config:ComparingGaugeItemPanel = null) {
    super(config);
  }

  protected function getDiffLabel(config:ComparingGaugeItemPanel):String {
    var diff:Number = getScoringDifference(config);
    if(diff == 0) {
      return null;
    }

    var msg:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_gain_score');
    if (diff > 0) {
      msg = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_lose_score');
    }

    msg = StringUtil.format(msg, diff.toFixed(2));
    return msg;
  }

  protected function getDiffIconCls(config:ComparingGaugeItemPanel):String {
    var diff:Number = getScoringDifference(config);
    var iconCls:String = resourceManager.getString('com.coremedia.icons.CoreIcons', 'check_circle');
    if (diff >= 0) {
      iconCls = resourceManager.getString('com.coremedia.icons.CoreIcons', 'error_circle');
    }

    return iconCls;
  }

  private function getScoringDifference(config:ComparingGaugeItemPanel):Number {
    var value1:Number = config.feedbackItem['gauge1'].value;
    var value2:Number = config.feedbackItem['gauge2'].value;

    return value1 - value2;
  }

  internal function getIconUI(config:ComparingGaugeItemPanel):String {
    var diff:Number = getScoringDifference(config);
    var iconUI:String = DisplayFieldSkin.DEFAULT.getSkin();
    if (diff < 0) {
      iconUI = DisplayFieldSkin.GREEN.getSkin();
    }
    else if (diff > 0) {
      iconUI = DisplayFieldSkin.RED.getSkin();
    }

    return iconUI;
  }
}
}
