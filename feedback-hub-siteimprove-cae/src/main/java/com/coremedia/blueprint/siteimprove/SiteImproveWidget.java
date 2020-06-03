package com.coremedia.blueprint.siteimprove;

import java.util.Map;

/**
 * SiteImproveConfiguration configuration interface.
 */
public class SiteImproveWidget {

  private Map<String, Object> data;

  public SiteImproveWidget(Map<String,Object> data) {
    this.data = data;
  }

  public boolean isEnabled() {
    return (boolean) data.getOrDefault("enabled", false);
  }

  public String getCmsToken() {
    return (String) data.get("cmsToken");
  }


  public String getSiteId() {
    return (String) data.get("siteId");
  }

  public String getApiKey() {
    return (String) data.get("apiKey");
  }

  public String getEmail() {
    return (String) data.get("email");
  }
}
