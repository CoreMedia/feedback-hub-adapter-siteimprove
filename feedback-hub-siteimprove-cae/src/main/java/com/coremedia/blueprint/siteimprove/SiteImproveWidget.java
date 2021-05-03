package com.coremedia.blueprint.siteimprove;

/**
 * SiteImproveConfiguration configuration interface.
 */
public interface SiteImproveWidget {

  boolean isEnabled();

  String getCmsToken();

  String getUsername();

  String getApiKey();

  String getAnalyticsUrl();

}
