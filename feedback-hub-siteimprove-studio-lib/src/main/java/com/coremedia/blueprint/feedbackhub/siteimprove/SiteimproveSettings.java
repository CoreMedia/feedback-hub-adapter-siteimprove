package com.coremedia.blueprint.feedbackhub.siteimprove;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * Settings to create a {@link SiteimproveContentFeedbackProvider}.
 */
public interface SiteimproveSettings {

  @NonNull
  String getEmail();

  @NonNull
  String getApiKey();

  @Nullable
  String getCoreMediaSiteId();

  @NonNull
  String getSiteimproveLiveSiteId();

  @NonNull
  String getSiteimprovePreviewSiteId();

  @NonNull
  String getPreviewCaeBaseUrl();

  @NonNull
  String getLiveCaeBaseUrl();

  @Nullable
  Boolean getFilterSeoIssues();

  @Nullable
  Boolean getFilterAccessibilityIssues();
}
