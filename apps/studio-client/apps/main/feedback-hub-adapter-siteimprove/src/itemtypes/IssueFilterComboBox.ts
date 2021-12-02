import BindListPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindListPlugin";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import DataField from "@coremedia/studio-client.ext.ui-components/store/DataField";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import IssueFilterComboBoxBase from "./IssueFilterComboBoxBase";

interface IssueFilterComboBoxConfig extends Config<IssueFilterComboBoxBase> {
}

class IssueFilterComboBox extends IssueFilterComboBoxBase {
  declare Config: IssueFilterComboBoxConfig;

  static override readonly xtype: string = "com.coremedia.blueprint.studio.siteimprove.config.issueFilterComboBox";

  constructor(config: Config<IssueFilterComboBox> = null) {
    super((()=> ConfigUtils.apply(Config(IssueFilterComboBox, {
      labelAlign: "top",
      labelSeparator: "",
      valueField: "value",
      displayField: "description",
      encodeItems: true,
      minWidth: 150,
      anyMatch: true,
      editable: false,
      allowBlank: false,
      ...ConfigUtils.append({
        plugins: [
          Config(BindListPlugin, {
            bindTo: this.getFilterOptionsExpression(),
            sortField: "code",
            fields: [
              Config(DataField, {
                name: "value",
                encode: false,
              }),
              Config(DataField, {
                name: "description",
                encode: false,
              }),
            ],
          }),
          Config(BindPropertyPlugin, {
            bindTo: config.bindTo,
            bidirectional: true,
            ifUndefined: IssueFilterComboBoxBase.VALUE_ALL,
            componentEvent: "change",
          }),
        ],
      }),
    }), config))());
  }
}

export default IssueFilterComboBox;
