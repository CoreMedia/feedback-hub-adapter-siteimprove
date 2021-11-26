import Config from "@jangaroo/runtime/Config";
import { bind } from "@jangaroo/runtime";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import IssueFilterComboBoxBase from "./IssueFilterComboBoxBase";
import IssueListItemPanel from "./IssueListItemPanel";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import int from "@jangaroo/runtime/int";
interface IssueListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}



class IssueListItemPanelBase extends FeedbackItemPanel {
  declare Config: IssueListItemPanelBaseConfig;
  static readonly #VIEW_INCREMENT:int = 3;

  #loadCount:int = 0;

  #keyCounter:int = 0;

  #issuesExpression:ValueExpression = null;
  #issueListExpression:ValueExpression = null;
  #filterStatusExpression:ValueExpression = null;

  constructor(config:Config<IssueListItemPanel> = null) {
    super(config);
    this.showMore();
  }

  protected override afterRender():void {
    super.afterRender();
    this.getFilteredIssuesList().addChangeListener(bind(this,this.#issuesChanged));
    this.#issuesChanged();
  }

  #issuesChanged():void {
    this.#loadCount = 0;
    this.showMore();
  }

  getKey():string {
    this.#keyCounter++;
    return "key-" + this.#keyCounter;
  }

  showMore():void {
    this.#loadCount++;
    var allIssues:Array<any> = this.getFilteredIssuesList().getValue();
    var endIndex:int = this.#loadCount * IssueListItemPanelBase.#VIEW_INCREMENT;
    this.#issuesExpression.setValue(allIssues.slice(0, endIndex));
  }

  getIssuesExpression():ValueExpression {
    if (!this.#issuesExpression) {
      this.#issuesExpression = ValueExpressionFactory.createFromValue([]);
    }
    return this.#issuesExpression;
  }

  getFilteredIssuesList():ValueExpression {
    if (!this.#issueListExpression) {
      this.#issueListExpression = ValueExpressionFactory.createFromFunction(():Array<any> => {
        var issues:Array<any> = this.feedbackItem["issues"];
        var filterStatus:string = this.getFilterStatusExpression().getValue();

        var result = [];
        var seoIssues = [];
        var a11yIssues = [];
        var qaIssues = [];

//        if(item.previewSummary.accessibilityIssuesDocument) {
//          a11yIssues = item.previewSummary.accessibilityIssuesDocument.items;
//        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_ALL) {
          result = issues;
        }


        if (filterStatus === IssueFilterComboBoxBase.VALUE_CONTENT) {
          result = result.concat(issues.filter((item:any):any => {
            if (item.issue_type === IssueFilterComboBoxBase.VALUE_CONTENT) {
              return item;
            }
          }));
        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_TECHNICAL) {
          result = result.concat(issues.filter((item:any):any => {
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

  getMoreIssuesExpression(config:Config<IssueListItemPanel>):ValueExpression {
    return ValueExpressionFactory.createFromFunction(():boolean => {
      var allIssues:Array<any> = this.feedbackItem["issues"];
      var loadedIssues:Array<any> = this.getIssuesExpression().getValue() || [];
      return allIssues.length > loadedIssues.length;
    });
  }

  getFilterStatusExpression():ValueExpression {
    if (!this.#filterStatusExpression) {
      this.#filterStatusExpression = ValueExpressionFactory.createFromValue(IssueFilterComboBoxBase.VALUE_ALL);
    }
    return this.#filterStatusExpression;
  }
}
export default IssueListItemPanelBase;
