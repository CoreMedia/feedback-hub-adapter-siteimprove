package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.panel.Panel;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueListBase extends Panel {
  private static const VIEW_INCREMENT:int = 3;

  private var loadCount:int = 0;

  [Bindable]
  public var bindTo:ValueExpression;

  private var issuesExpression:ValueExpression;

  public function IssueListBase(config:IssueList = null) {
    super(config);
    showMore();
  }

  internal function showMore():void {
    loadCount++;
    var allIssues:Array = bindTo.getValue() || [];
    var endIndex:int = loadCount * VIEW_INCREMENT;
    issuesExpression.setValue(allIssues.slice(0, endIndex));

  }

  internal function getIssuesExpression():ValueExpression {
    if (!issuesExpression) {
      issuesExpression = ValueExpressionFactory.createFromValue([]);
    }

    return issuesExpression;
  }

  internal function getMoreIssuesExpression(config:IssueList):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():Boolean {
      var allIssues:Array = config.bindTo.getValue() || [];
      var loadedIssues:Array = getIssuesExpression().getValue() || [];
      return allIssues.length > loadedIssues.length;
    })
  }

}
}
