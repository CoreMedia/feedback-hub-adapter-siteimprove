import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import IconDisplayField from "@coremedia/studio-client.ext.ui-components/components/IconDisplayField";
import OverflowBehaviour from "@coremedia/studio-client.ext.ui-components/mixins/OverflowBehaviour";
import BindVisibilityPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindVisibilityPlugin";
import FeedbackItem from "@coremedia/studio-client.feedback-hub-models/FeedbackItem";
import GaugeFeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/GaugeFeedbackItemPanel";
import Component from "@jangaroo/ext-ts/Component";
import Container from "@jangaroo/ext-ts/container/Container";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import { cast } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import ComparingGaugeItemPanelBase from "./ComparingGaugeItemPanelBase";

interface ComparingGaugeItemPanelConfig extends Config<ComparingGaugeItemPanelBase> {
}

class ComparingGaugeItemPanel extends ComparingGaugeItemPanelBase {
  declare Config: ComparingGaugeItemPanelConfig;

  static override readonly xtype: string = "com.coremedia.blueprint.studio.feedbackhub.siteimprove.config.comparingGaugeItemPanel";

  constructor(config: Config<ComparingGaugeItemPanel> = null) {
    super((()=> ConfigUtils.apply(Config(ComparingGaugeItemPanel, {
      minWidth: 360,

      items: [
        Config(Container, {
          flex: 1,
          items: [
            Config(GaugeFeedbackItemPanel, {
              flex: 1,
              feedbackGroup: config.feedbackGroup,
              feedbackItem: cast(FeedbackItem, config.feedbackItem["gauge1"]),
            }),
            Config(GaugeFeedbackItemPanel, {
              flex: 1,
              feedbackGroup: config.feedbackGroup,
              feedbackItem: cast(FeedbackItem, config.feedbackItem["gauge2"]),
            }),
          ],
          layout: Config(HBoxLayout, { align: "stretch" }),
        }),
        Config(Component, { height: 12 }),
        Config(Container, {
          items: [
            Config(IconDisplayField, {
              iconCls: this.getDiffIconCls(config),
              ui: this.getIconUI(config),
              scale: "medium",
              margin: "0 6 0 0",
            }),
            Config(IconDisplayField, {
              value: this.getDiffLabel(config),
              overflowBehaviour: OverflowBehaviour.ELLIPSIS,
            }),
          ],
          plugins: [
            Config(BindVisibilityPlugin, { bindTo: ValueExpressionFactory.createFromValue(this.getDiffLabel(config)) }),
          ],
          layout: Config(HBoxLayout, {
            align: "stretch",
            pack: "start",
          }),
        }),
        Config(Component, { height: 24 }),
      ],
      layout: Config(VBoxLayout, { align: "stretch" }),
    }), config))());
  }
}

export default ComparingGaugeItemPanel;
