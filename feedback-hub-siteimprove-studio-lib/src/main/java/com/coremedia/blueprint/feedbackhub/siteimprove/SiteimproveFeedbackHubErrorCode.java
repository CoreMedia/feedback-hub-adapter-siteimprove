package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.feedbackhub.adapter.FeedbackHubErrorCode;

/**
 * Error codes for Imagga adapter
 */
enum SiteimproveFeedbackHubErrorCode implements FeedbackHubErrorCode {
  EMAIL_NOT_SET,
  API_KEY_NOT_SET,
  CONTENT_CHECKING_DISABLED,
  SITE_ID_NOT_SET
}
