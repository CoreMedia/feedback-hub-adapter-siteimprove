package com.coremedia.labs.plugins.adapters.siteimprove;

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
  @Deprecated
  String getPreviewCaeBaseUrl();

  @NonNull
  @Deprecated
  String getLiveCaeBaseUrl();

  @NonNull
  String getContentLinkBuilderStrategy();

  @NonNull
  String getPreviewBaseUrl();

  @NonNull
  String getLiveBaseUrl();
}
