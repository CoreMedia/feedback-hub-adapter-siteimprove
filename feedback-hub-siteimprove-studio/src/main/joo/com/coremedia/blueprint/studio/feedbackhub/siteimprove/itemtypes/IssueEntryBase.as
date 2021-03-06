package com.coremedia.blueprint.studio.feedbackhub.siteimprove.itemtypes {
import ext.container.Container;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueEntryBase extends Container {

  [Bindable]
  public var issue:Object;

  public function IssueEntryBase(config:IssueEntry = null) {
    super(config);
  }

  protected function resolveLinkLabel(issue:Object):String {
    var label:String = "---";
    if(issue.issue_name) {
      label = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'issue_' + issue.issue_name);
      if (!label) {
        label = formatKey(issue.issue_name);
      }
    }

    return label;
  }

  internal function getIssueCategory(issue:Object):String {
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove','siteimprove_issue_category_seov2');
  }

  private function formatKey(key:String):String {
    while (key.indexOf("_") !== -1) {
      key = key.replace('_', ' ');
    }

    return key.replace(/(?:^\w|[A-Z]|\b\w)/g, function (letter:*, index:*):String {
      return letter.toUpperCase();
    }).replace(/\s+/g, ' ');
  }

  protected function openIssue():void {
    var url:String = issue._siteimprove.page_report.href;
    window.open(url, '_blank');
  }
}
}
