import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import IssueEntry from "./IssueEntry";
import IssueFilterComboBox from "./IssueFilterComboBox";
import IssueListItemPanelBase from "./IssueListItemPanelBase";
import BindComponentsPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindComponentsPlugin";
import BindVisibilityPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindVisibilityPlugin";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import ContainerSkin from "@coremedia/studio-client.ext.ui-components/skins/ContainerSkin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface IssueListItemPanelConfig extends Config<IssueListItemPanelBase> {
}



    class IssueListItemPanel extends IssueListItemPanelBase{
  declare Config: IssueListItemPanelConfig;

  static override readonly xtype:string = "com.coremedia.blueprint.studio.feedbackhub.siteimprove.config.IssueList";

  constructor(config:Config<IssueListItemPanel> = null){
    super((()=> ConfigUtils.apply(Config(IssueListItemPanel, {
                              ui:  ContainerSkin.GRID_100.getSkin(),

  items:[

    Config(Container, {
      items:[
        Config(DisplayField, { value: FeedbackHubSiteimprove_properties.siteimprove_filter}),
        Config(Container, { flex: 1}),
        Config(IssueFilterComboBox, { bindTo: this.getFilterStatusExpression()})
      ],
      layout: Config(HBoxLayout, { align: "stretch"
      })
    }),
    Config(Container, { style: "background: #f1f1f1; padding: 1px;margin-top: 6px;",
      items:[
        Config(Container, { style: "padding: 3px;",
          items:[
            Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(),
                          flex: 1,
                          value: FeedbackHubSiteimprove_properties.siteimprove_issue}),
            Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(),
                          width: 100,
                          value: FeedbackHubSiteimprove_properties.siteimprove_category}),
            Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(),
                          width: 120,
                          value: FeedbackHubSiteimprove_properties.siteimprove_points_to_gain})
          ],
          layout: Config(HBoxLayout, { align: "stretch", pack: "center"
          })
        }),
        Config(Container, { style: "background: #FFF; padding: 3px;",
          plugins:[
            Config(BindComponentsPlugin, { valueExpression: this.getIssuesExpression(),
                                     getKey: bind(this,this.getKey),
                                     configBeanParameterName: "issue",
                                     clearBeforeUpdate: true,
                                     reuseComponents: false,
              template: Config(IssueEntry)
            })
          ]
        })
      ],
      layout: Config(VBoxLayout, { align: "stretch"
      })
    }),

    Config(Container, { margin: "0 0 0 -9",
      items:[
        Config(Button, { ui: ConfigUtils.asString( ButtonSkin.SIMPLE),
                text:  FeedbackHubSiteimprove_properties.siteimprove_show_more, handler: bind(this,this.showMore),
          plugins:[
            Config(BindVisibilityPlugin, { bindTo: this.getMoreIssuesExpression(config)})
          ]
        })
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "start"
      })
    })
  ],
  layout: Config(VBoxLayout, { align: "stretch"
  })
}),config))());
  }}
export default IssueListItemPanel;
