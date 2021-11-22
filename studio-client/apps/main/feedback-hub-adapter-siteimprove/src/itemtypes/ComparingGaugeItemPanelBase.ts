import Config from "@jangaroo/runtime/Config";
import { asConfig } from "@jangaroo/runtime";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import ComparingGaugeItemPanel from "./ComparingGaugeItemPanel";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import StringUtil from "@jangaroo/ext-ts/String";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface ComparingGaugeItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}



class ComparingGaugeItemPanelBase extends FeedbackItemPanel {
  declare Config: ComparingGaugeItemPanelBaseConfig;

  constructor(config:Config<ComparingGaugeItemPanel> = null) {
    super(config);
  }

  protected getDiffLabel(config:Config<ComparingGaugeItemPanel>):string {
    var diff = this.#getScoringDifference(config);
    if(diff == 0) {
      return null;
    }

    var msg = FeedbackHubSiteimprove_properties.siteimprove_gain_score;
    if (diff <= 0) {
      msg = FeedbackHubSiteimprove_properties.siteimprove_lose_score;
    }

    msg = StringUtil.format(msg, diff.toFixed(2));
    return msg;
  }

  protected getDiffIconCls(config:Config<ComparingGaugeItemPanel>):string {
    var diff = this.#getScoringDifference(config);
    var iconCls = CoreIcons_properties.check_circle;
    if (diff >= 0) {
      iconCls = CoreIcons_properties.error_circle;
    }

    return iconCls;
  }

  #getScoringDifference(config:Config<ComparingGaugeItemPanel>):number {
    var value1:number = config.feedbackItem["gauge1"].value;
    var value2:number = config.feedbackItem["gauge2"].value;

    return value1 - value2;
  }

  getIconUI(config:Config<ComparingGaugeItemPanel>):string {
    var diff = this.#getScoringDifference(config);
    var iconUI = DisplayFieldSkin.DEFAULT.getSkin();
    if (diff < 0) {
      iconUI = DisplayFieldSkin.GREEN.getSkin();
    }
    else if (diff > 0) {
      iconUI = DisplayFieldSkin.RED.getSkin();
    }

    return iconUI;
  }
}
export default ComparingGaugeItemPanelBase;
