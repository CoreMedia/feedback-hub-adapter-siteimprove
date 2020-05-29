package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.ui.data.ValueExpression;

import ext.container.Container;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueEntryBase extends Container {

  [Bindable]
  public var issue:Object;

  public function IssueEntryBase(config:IssueEntry = null) {
    super(config);
  }

  protected function resolveLinkLabel(key:String):String {
    var label:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'issue_' + key);
    if (!label) {
      label = formatKey(key);
    }
    return label;
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
