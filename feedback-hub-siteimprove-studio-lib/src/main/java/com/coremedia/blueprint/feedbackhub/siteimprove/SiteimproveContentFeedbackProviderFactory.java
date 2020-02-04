package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import com.coremedia.feedbackhub.provider.ContentFeedbackProvider;
import com.coremedia.feedbackhub.provider.ContentFeedbackProviderFactory;

public class SiteimproveContentFeedbackProviderFactory implements ContentFeedbackProviderFactory<SiteimproveSettings> {
  private SiteimproveService siteimproveService;

  public SiteimproveContentFeedbackProviderFactory(SiteimproveService siteimproveService) {
    this.siteimproveService = siteimproveService;
  }

  @Override
  public String getId() {
    return SiteimproveFeedbackItem.TYPE;
  }

  @Override
  public ContentFeedbackProvider create(SiteimproveSettings settings) {
    String email = settings.getEmail();
    if (email == null) {
      throw new FeedbackHubException("settings must provide an email", SiteimproveFeedbackHubErrorCode.EMAIL_NOT_SET);
    }

    String apiKey = settings.getApiKey();
    if (apiKey == null) {
      throw new FeedbackHubException("settings must provide an apiKey", SiteimproveFeedbackHubErrorCode.API_KEY_NOT_SET);
    }

    String siteId = settings.getCoreMediaSiteId();
    if (siteId == null) {
      throw new FeedbackHubException("settings must provide a CoreMedia siteId", SiteimproveFeedbackHubErrorCode.SITE_ID_NOT_SET);
    }

    return new SiteimproveContentFeedbackProvider(settings, siteimproveService);
  }
}
