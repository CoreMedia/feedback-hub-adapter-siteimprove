package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.ui.data.ValueExpression;

import ext.container.Container;
import ext.form.field.DisplayField;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueListBase extends Container {

  [Bindable]
  public var feedbackData:ValueExpression;

  [Bindable]
  public var bindTo:ValueExpression;

  public function IssueListBase(config:IssueList = null) {
    super(config);
  }
}
}
