import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import { bind } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import int from "@jangaroo/runtime/int";
import IssueFilterComboBoxBase from "./IssueFilterComboBoxBase";
import IssueListItemPanel from "./IssueListItemPanel";

interface IssueListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}

class IssueListItemPanelBase extends FeedbackItemPanel {
  declare Config: IssueListItemPanelBaseConfig;

  static readonly #VIEW_INCREMENT: int = 3;

  #loadCount: int = 0;

  #keyCounter: int = 0;

  #issuesExpression: ValueExpression = null;

  #issueListExpression: ValueExpression = null;

  #filterStatusExpression: ValueExpression = null;

  constructor(config: Config<IssueListItemPanel> = null) {
    super(config);
    this.showMore();
  }

  protected override afterRender(): void {
    super.afterRender();
    this.getFilteredIssuesList().addChangeListener(bind(this, this.#issuesChanged));
    this.#issuesChanged();
  }

  #issuesChanged(): void {
    this.#loadCount = 0;
    this.showMore();
  }

  getKey(): string {
    this.#keyCounter++;
    return "key-" + this.#keyCounter;
  }

  showMore(): void {
    this.#loadCount++;
    const allIssues: Array<any> = this.getFilteredIssuesList().getValue();
    const endIndex: int = this.#loadCount * IssueListItemPanelBase.#VIEW_INCREMENT;
    this.#issuesExpression.setValue(allIssues.slice(0, endIndex));
  }

  getIssuesExpression(): ValueExpression {
    if (!this.#issuesExpression) {
      this.#issuesExpression = ValueExpressionFactory.createFromValue([]);
    }
    return this.#issuesExpression;
  }

  getFilteredIssuesList(): ValueExpression {
    if (!this.#issueListExpression) {
      this.#issueListExpression = ValueExpressionFactory.createFromFunction((): Array<any> => {
        const issues: Array<any> = this.feedbackItem["issues"];
        const filterStatus: string = this.getFilterStatusExpression().getValue();

        let result = [];
        const seoIssues = [];
        const a11yIssues = [];
        const qaIssues = [];

        //        if(item.previewSummary.accessibilityIssuesDocument) {
        //          a11yIssues = item.previewSummary.accessibilityIssuesDocument.items;
        //        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_ALL) {
          result = issues;
        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_CONTENT) {
          result = result.concat(issues.filter((item: any): any => {
            if (item.issue_type === IssueFilterComboBoxBase.VALUE_CONTENT) {
              return item;
            }
          }));
        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_TECHNICAL) {
          result = result.concat(issues.filter((item: any): any => {
            if (item.issue_type === IssueFilterComboBoxBase.VALUE_TECHNICAL) {
              return item;
            }
          }));
        }

        return result;
      });
    }
    return this.#issueListExpression;
  }

  getMoreIssuesExpression(config: Config<IssueListItemPanel>): ValueExpression {
    return ValueExpressionFactory.createFromFunction((): boolean => {
      const allIssues: Array<any> = this.feedbackItem["issues"];
      const loadedIssues: Array<any> = this.getIssuesExpression().getValue() || [];
      return allIssues.length > loadedIssues.length;
    });
  }

  getFilterStatusExpression(): ValueExpression {
    if (!this.#filterStatusExpression) {
      this.#filterStatusExpression = ValueExpressionFactory.createFromValue(IssueFilterComboBoxBase.VALUE_ALL);
    }
    return this.#filterStatusExpression;
  }
}

export default IssueListItemPanelBase;
