package com.coremedia.blueprint.studio.feedbackhub.siteimprove.components {
import com.coremedia.ui.components.LocalComboBox;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import mx.resources.ResourceManager;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueFilterComboBoxBase extends LocalComboBox {
  public static const VALUE_ALL:int = 0;
  public static const VALUE_A11Y:int = 1;
  public static const VALUE_QA:int = 2;
  public static const VALUE_SEO:int = 3;

  [Bindable]
  public var bindTo:ValueExpression;

  private var valuesExpression:ValueExpression;

  public function IssueFilterComboBoxBase(config:IssueFilterComboBox = null) {
    super(config);
  }

  protected function getFilterOptionsExpression():ValueExpression {
    if (!valuesExpression) {
      var values:Array = [];

//      values.push({
//        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_issues_all'),
//        'value': VALUE_ALL
//      });

//      values.push({
//        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_issues_a11n'),
//        'value': VALUE_A11Y
//      });
//
//      values.push({
//        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_issues_qa'),
//        'value': VALUE_QA
//      });
//
      values.push({
        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'feedbackItemPanel_siteimprove_issues_seo'),
        'value': VALUE_SEO
      });

      valuesExpression = ValueExpressionFactory.createFromValue(values);
    }
    return valuesExpression;
  }
}
}

