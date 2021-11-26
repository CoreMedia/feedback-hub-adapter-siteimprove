import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import IssueEntryBase from "./IssueEntryBase";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface IssueEntryConfig extends Config<IssueEntryBase> {
}



    class IssueEntry extends IssueEntryBase{
  declare Config: IssueEntryConfig;

  static override readonly xtype:string = "com.coremedia.blueprint.studio.feedbackhub.siteimprove.config.IssueEntry";

  constructor(config:Config<IssueEntry> = null){
    super((()=> ConfigUtils.apply(Config(IssueEntry, {

  items:[
    Config(Container, { flex: 1,
      items:[
        Config(Button, { margin: "0 0 0 -9", ui:  ButtonSkin.SIMPLE_PRIMARY.getSkin(), text:  this.resolveLinkLabel(config.issue), handler: bind(this,this.openIssue)})
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "start"
      })
    }),
    Config(DisplayField, { width: 100, value: this.getIssueCategory(config.issue) }),
    Config(Container, { width: 120,
      items:[
        Config(DisplayField, {  ui:  DisplayFieldSkin.BOLD.getSkin(), value: config.issue.seo_points_to_gain }),
        Config(Container, { width: 3 }),
        Config(DisplayField, { value: FeedbackHubSiteimprove_properties.siteimprove_points }),
        Config(Container, { width: 3 })
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "end"
      })
    })
  ],
  layout: Config(HBoxLayout, { align: "stretch"
  })
}),config))());
  }}
export default IssueEntry;
