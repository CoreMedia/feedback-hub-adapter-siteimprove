package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.feedbackhub.adapter.FeedbackHubErrorCode;

/**
 * Error codes for Imagga adapter
 */
public enum SiteimproveFeedbackHubErrorCode implements FeedbackHubErrorCode {
  EMAIL_NOT_SET,
  API_KEY_NOT_SET,
  SITE_ID_NOT_SET,
  SITEIMPROVE_PREVIEW_SITE_ID_NOT_SET,
  SITEIMPROVE_LIVE_SITE_ID_NOT_SET,
  PREVIEW_BASE_URL_NOT_SET,
  LIVE_BASE_URL_NOT_SET,
  REST_ERROR,
  NO_CONTENT_METATAG_FOUND,
  NO_CONTENT_METATAG_WITH_CONTENT_ID_FOUND,
  NO_CONTENT_FOUND,
  NOT_PUBLISHED
}
