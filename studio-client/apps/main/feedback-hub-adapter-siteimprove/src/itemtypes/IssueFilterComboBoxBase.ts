import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import LocalComboBox from "@coremedia/studio-client.ext.ui-components/components/LocalComboBox";
import Config from "@jangaroo/runtime/Config";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import IssueFilterComboBox from "./IssueFilterComboBox";

interface IssueFilterComboBoxBaseConfig extends Config<LocalComboBox>, Partial<Pick<IssueFilterComboBoxBase,
  "bindTo"
>> {
}

class IssueFilterComboBoxBase extends LocalComboBox {
  declare Config: IssueFilterComboBoxBaseConfig;

  static readonly VALUE_ALL: string = "all";

  static readonly VALUE_CONTENT: string = "content";

  static readonly VALUE_TECHNICAL: string = "technical";

  #bindTo: ValueExpression = null;

  get bindTo(): ValueExpression {
    return this.#bindTo;
  }

  set bindTo(value: ValueExpression) {
    this.#bindTo = value;
  }

  #valuesExpression: ValueExpression = null;

  constructor(config: Config<IssueFilterComboBox> = null) {
    super(config);
  }

  protected getFilterOptionsExpression(): ValueExpression {
    if (!this.#valuesExpression) {
      const values = [];

      values.push({
        "description": FeedbackHubSiteimprove_properties.siteimprove_issues_seo_technical,
        "value": IssueFilterComboBoxBase.VALUE_TECHNICAL,
      });

      values.push({
        "description": FeedbackHubSiteimprove_properties.siteimprove_issues_seo_content,
        "value": IssueFilterComboBoxBase.VALUE_CONTENT,
      });

      values.push({
        "description": FeedbackHubSiteimprove_properties.siteimprove_issues_all,
        "value": IssueFilterComboBoxBase.VALUE_ALL,
      });

      this.#valuesExpression = ValueExpressionFactory.createFromValue(values);
    }
    return this.#valuesExpression;
  }
}

export default IssueFilterComboBoxBase;
