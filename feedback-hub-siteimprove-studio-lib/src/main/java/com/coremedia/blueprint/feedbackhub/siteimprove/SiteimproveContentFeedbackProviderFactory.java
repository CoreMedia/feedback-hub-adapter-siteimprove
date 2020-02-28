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
    if (apiKey == null || apiKey.isEmpty()) {
      throw new FeedbackHubException("settings must provide an apiKey", SiteimproveFeedbackHubErrorCode.API_KEY_NOT_SET);
    }

    String siteId = settings.getCoreMediaSiteId();
    if (siteId == null) {
      throw new FeedbackHubException("settings must provide a CoreMedia siteId", SiteimproveFeedbackHubErrorCode.SITE_ID_NOT_SET);
    }

    String siteimprovePreviewSiteId = settings.getSiteimprovePreviewSiteId();
    if (siteimprovePreviewSiteId == null) {
      throw new FeedbackHubException("settings must provide the id of the Siteimprove preview site", SiteimproveFeedbackHubErrorCode.SITEIMPROVE_PREVIEW_SITE_ID_NOT_SET);
    }

    String siteimproveLiveSiteId = settings.getSiteimproveLiveSiteId();
    if (siteimproveLiveSiteId == null) {
      throw new FeedbackHubException("settings must provide the id of the Siteimprove live site", SiteimproveFeedbackHubErrorCode.SITEIMPROVE_LIVE_SITE_ID_NOT_SET);
    }

    String previewCaeBaseUrl = settings.getPreviewCaeBaseUrl();
    if (previewCaeBaseUrl == null) {
      throw new FeedbackHubException("settings must provide the base url of the preview cae", SiteimproveFeedbackHubErrorCode.PREVIEW_BASE_URL_NOT_SET);
    }

    String liveCaeBaseUrl = settings.getLiveCaeBaseUrl();
    if (liveCaeBaseUrl == null) {
      throw new FeedbackHubException("settings must provide the base url of the live cae", SiteimproveFeedbackHubErrorCode.LIVE_BASE_URL_NOT_SET);
    }

    return new SiteimproveContentFeedbackProvider(settings, siteimproveService);
  }
}
