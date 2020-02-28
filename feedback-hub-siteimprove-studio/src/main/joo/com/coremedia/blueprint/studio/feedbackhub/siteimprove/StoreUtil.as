package com.coremedia.blueprint.studio.feedbackhub.siteimprove {
public class StoreUtil {
  private static const STORE:Object = {};

  public static function saveFeedbackItem(config:SiteimproveFeedbackItemPanel):void {
    STORE[getContentKey(config)] = config.feedbackItem;
  }

  public static function getFeedbackItem(config:SiteimproveFeedbackItemPanel):Object {
    return STORE[getContentKey(config)];
  }


  private static  function getContentKey(config:SiteimproveFeedbackItemPanel):String {
    return config.contentExpression.getValue();
  }


}
}
