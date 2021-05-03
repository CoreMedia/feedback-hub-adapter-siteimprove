package com.coremedia.blueprint.studio.feedbackhub.siteimprove.itemtypes {
import com.coremedia.ui.components.LocalComboBox;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import mx.resources.ResourceManager;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove')]
public class IssueFilterComboBoxBase extends LocalComboBox {
  public static const VALUE_ALL:String = 'all';
  public static const VALUE_CONTENT:String = 'content';
  public static const VALUE_TECHNICAL:String = 'technical';


  [Bindable]
  public var bindTo:ValueExpression;

  private var valuesExpression:ValueExpression;

  public function IssueFilterComboBoxBase(config:IssueFilterComboBox = null) {
    super(config);
  }

  protected function getFilterOptionsExpression():ValueExpression {
    if (!valuesExpression) {
      var values:Array = [];


      values.push({
        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_issues_seo_technical'),
        'value': VALUE_TECHNICAL
      });

      values.push({
        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_issues_seo_content'),
        'value': VALUE_CONTENT
      });

      values.push({
        'description': ResourceManager.getInstance().getString('com.coremedia.blueprint.studio.feedbackhub.siteimprove.FeedbackHubSiteimprove', 'siteimprove_issues_all'),
        'value': VALUE_ALL
      });

      valuesExpression = ValueExpressionFactory.createFromValue(values);
    }
    return valuesExpression;
  }
}
}

