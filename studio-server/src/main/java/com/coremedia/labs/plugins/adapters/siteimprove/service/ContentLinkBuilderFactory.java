package com.coremedia.labs.plugins.adapters.siteimprove.service;

import com.coremedia.labs.plugins.adapters.siteimprove.SiteimproveSettings;

public class ContentLinkBuilderFactory {

  public static final String DIRECT_CONTENT_LINK_BUILDER_STRATEGY = "direct";

  public ContentLinkBuilder create(SiteimproveSettings config) {
    String strategy = config.getContentLinkBuilderStrategy();
    if (strategy.equals(DIRECT_CONTENT_LINK_BUILDER_STRATEGY)) {
      return new DirectContentLinkBuilder(config);
    } else {
      return new ServiceContentLinkBuilder(config);
    }
  }
}
