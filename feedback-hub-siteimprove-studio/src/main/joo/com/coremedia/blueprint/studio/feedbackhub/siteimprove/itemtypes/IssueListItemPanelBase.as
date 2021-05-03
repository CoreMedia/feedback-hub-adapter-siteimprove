package com.coremedia.blueprint.studio.feedbackhub.siteimprove.itemtypes {
import com.coremedia.cms.studio.feedbackhub.components.itempanels.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueListItemPanelBase extends FeedbackItemPanel {
  private static const VIEW_INCREMENT:int = 3;

  private var loadCount:int = 0;

  private var keyCounter:int = 0;

  private var issuesExpression:ValueExpression;
  private var issueListExpression:ValueExpression;
  private var filterStatusExpression:ValueExpression;

  public function IssueListItemPanelBase(config:IssueListItemPanel = null) {
    super(config);
    showMore();
  }

  override protected function afterRender():void {
    super.afterRender();
    getFilteredIssuesList().addChangeListener(issuesChanged);
    issuesChanged();
  }

  private function issuesChanged():void {
    loadCount = 0;
    showMore();
  }

  internal function getKey():String {
    keyCounter++;
    return "key-" + keyCounter;
  }

  internal function showMore():void {
    loadCount++;
    var allIssues:Array = getFilteredIssuesList().getValue();
    var endIndex:int = loadCount * VIEW_INCREMENT;
    issuesExpression.setValue(allIssues.slice(0, endIndex));
  }

  internal function getIssuesExpression():ValueExpression {
    if (!issuesExpression) {
      issuesExpression = ValueExpressionFactory.createFromValue([]);
    }
    return issuesExpression;
  }

  internal function getFilteredIssuesList():ValueExpression {
    if (!issueListExpression) {
      issueListExpression = ValueExpressionFactory.createFromFunction(function ():Array {
        var issues:Array = feedbackItem['issues'];
        var filterStatus:String = getFilterStatusExpression().getValue();

        var result:Array = [];
        var seoIssues:Array = [];
        var a11yIssues:Array = [];
        var qaIssues:Array = [];

//        if(item.previewSummary.accessibilityIssuesDocument) {
//          a11yIssues = item.previewSummary.accessibilityIssuesDocument.items;
//        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_ALL) {
          result = issues;
        }


        if (filterStatus === IssueFilterComboBoxBase.VALUE_CONTENT) {
          result = result.concat(issues.filter(function (item:Object):Object {
            if (item.issue_type === IssueFilterComboBoxBase.VALUE_CONTENT) {
              return item;
            }
          }));
        }

        if (filterStatus === IssueFilterComboBoxBase.VALUE_TECHNICAL) {
          result = result.concat(issues.filter(function (item:Object):Object {
            if (item.issue_type === IssueFilterComboBoxBase.VALUE_TECHNICAL) {
              return item;
            }
          }));
        }

        return result;
      });
    }
    return issueListExpression;
  }

  internal function getMoreIssuesExpression(config:IssueListItemPanel):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function ():Boolean {
      var allIssues:Array = feedbackItem['issues'];
      var loadedIssues:Array = getIssuesExpression().getValue() || [];
      return allIssues.length > loadedIssues.length;
    })
  }

  internal function getFilterStatusExpression():ValueExpression {
    if (!filterStatusExpression) {
      filterStatusExpression = ValueExpressionFactory.createFromValue(IssueFilterComboBoxBase.VALUE_ALL);
    }
    return filterStatusExpression;
  }
}
}
