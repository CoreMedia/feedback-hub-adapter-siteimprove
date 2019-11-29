package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.feedbackhub.keywords.BlobKeywordsContentFeedbackProvider;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * Settings to create a {@link SiteimproveContentFeedbackProvider}.
 */
@DefaultAnnotation(NonNull.class)
public interface SiteimproveSettings {

  String getEmail();

  String getApiKey();

  String getSiteId();

}
