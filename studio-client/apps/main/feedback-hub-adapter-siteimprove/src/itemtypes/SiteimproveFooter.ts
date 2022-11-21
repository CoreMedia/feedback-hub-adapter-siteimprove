import SpacingBEMEntities from "@coremedia/studio-client.ext.ui-components/bem/SpacingBEMEntities";
import SwitchingContainer from "@coremedia/studio-client.ext.ui-components/components/SwitchingContainer";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import VerticalSpacingPlugin from "@coremedia/studio-client.ext.ui-components/plugins/VerticalSpacingPlugin";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import Component from "@jangaroo/ext-ts/Component";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import { bind } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import SiteimproveFooterBase from "./SiteimproveFooterBase";

interface SiteimproveFooterConfig extends Config<SiteimproveFooterBase> {
}

class SiteimproveFooter extends SiteimproveFooterBase {
  declare Config: SiteimproveFooterConfig;

  static readonly SITEIMPROVE_STATUS: string = "siteimprove_status";

  static readonly SITEIMPROVE_CHECKING: string = "siteimprove_checking";

  static override readonly xtype: string = "com.coremedia.blueprint.studio.feedbackhub.siteimprove.config.SiteimproveFooter";

  constructor(config: Config<SiteimproveFooter> = null) {
    super((()=> ConfigUtils.apply(Config(SiteimproveFooter, {

      items: [

        Config(SwitchingContainer, {
          activeItemValueExpression: this.getActiveStateExpression(config),

          items: [
            Config(Container, {
              itemId: SiteimproveFooter.SITEIMPROVE_STATUS,
              items: [
                Config(DisplayField, {
                  ui: DisplayFieldSkin.BOLD.getSkin(),
                  value: FeedbackHubSiteimprove_properties.siteimprove_last_crawl,
                }),
                Config(DisplayField, {
                  plugins: [
                    Config(BindPropertyPlugin, { bindTo: this.getLastPreviewCrawlDateExpression(config) }),
                  ],
                }),
                Config(DisplayField, {
                  plugins: [
                    Config(BindPropertyPlugin, { bindTo: this.getLastLiveCrawlDateExpression(config) }),
                  ],
                }),
              ],
              layout: Config(VBoxLayout, {
                align: "stretch",
                pack: "start",
              }),
            }),

            Config(Container, {
              itemId: SiteimproveFooter.SITEIMPROVE_CHECKING,
              items: [
                Config(DisplayField, {
                  ui: DisplayFieldSkin.BOLD.getSkin(),
                  value: FeedbackHubSiteimprove_properties.siteimprove_checking,

                }),
              ],
              layout: Config(VBoxLayout, {
                align: "stretch",
                pack: "start",
              }),
            }),
          ],
        }),

        Config(Component, { flex: 1 }),
        Config(Container, {
          width: 180,
          items: [
            Config(Button, {
              itemId: SiteimproveFooterBase.RECRAWL_BUTTON_ITEM_ID,
              ui: ButtonSkin.MATERIAL_SECONDARY.getSkin(),
              handler: bind(this, this.recrawlPage),
              text: FeedbackHubSiteimprove_properties.recrawl_preview_page_btn_text,
              ariaLabel: FeedbackHubSiteimprove_properties.recrawl_preview_page_btn_tooltip,
              tooltip: FeedbackHubSiteimprove_properties.recrawl_preview_page_btn_tooltip,
            }),
            Config(Button, {
              itemId: SiteimproveFooterBase.RELOAD_BUTTON_ITEM_ID,
              ui: ButtonSkin.MATERIAL_SECONDARY.getSkin(),
              handler: bind(this, this.reload),
              text: FeedbackHub_properties.reload_btn_text,
              ariaLabel: FeedbackHub_properties.reload_btn_text,
            }),
          ],
          plugins: [
            Config(VerticalSpacingPlugin, { modifier: SpacingBEMEntities.VERTICAL_SPACING_MODIFIER_50 }),
          ],
          layout: Config(VBoxLayout, { align: "stretch" }),
        }),
      ],
      layout: Config(HBoxLayout, { align: "stretch" }),
    }), config))());
  }
}

export default SiteimproveFooter;
