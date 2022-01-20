package com.coremedia.blueprint.feedbackhub.siteimprove.service;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;

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
