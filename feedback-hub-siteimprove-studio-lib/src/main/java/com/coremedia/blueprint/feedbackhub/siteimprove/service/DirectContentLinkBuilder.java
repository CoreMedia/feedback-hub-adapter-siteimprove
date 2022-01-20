package com.coremedia.blueprint.feedbackhub.siteimprove.service;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriTemplate;

import java.util.HashMap;
import java.util.Map;

public class DirectContentLinkBuilder implements ContentLinkBuilder {
  private static final Logger LOG = LoggerFactory.getLogger(DirectContentLinkBuilder.class);

  private final SiteimproveSettings config;

  public DirectContentLinkBuilder(SiteimproveSettings config) {
    this.config = config;
  }

  @Nullable
  @Override
  public String buildLink(@NonNull Boolean isPreview, @NonNull Content content) {
    String baseUrl = isPreview ? config.getPreviewBaseUrl() : config.getLiveBaseUrl();
    UriTemplate uriTemplate = new UriTemplate(baseUrl);
    Map<String, Object> params = new HashMap<>();
    params.put("contentId", IdHelper.parseContentId(content.getId()));
    String url = uriTemplate.expand(params).toString();
    LOG.info("Build Link for content " + content.getId() + ": " + url);
    return url;
  }
}
