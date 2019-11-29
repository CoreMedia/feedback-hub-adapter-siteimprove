package com.coremedia.blueprint.feedbackhub.siteimprove.service;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.AccessibilityIssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.AnalyticsSummaryDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.BrokenLinkPagesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckResultDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.CrawlStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.DciOverallScoreDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDetailsDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PagesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.QualitySummaryDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.SeoIssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Seov2IssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.SiteDocument;
import com.coremedia.cap.content.Content;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.springframework.util.MultiValueMap;

public interface SiteimproveService {
  @Nullable
  CrawlStatusDocument triggerRecrawlSite(@NonNull SiteimproveSettings config);

  @Nullable
  default DciOverallScoreDocument getDCIScore(@NonNull SiteimproveSettings config) {
    return getDCIScore(config, null);
  }

  @Nullable
  DciOverallScoreDocument getDCIScore(@NonNull SiteimproveSettings config, @Nullable String pageId);

  @Nullable
  QualitySummaryDocument getQualitySummary(@NonNull SiteimproveSettings config);

  @Nullable
  PagesDocument getMisspellingPages(@NonNull SiteimproveSettings config);

  @Nullable
  PagesDocument getMisspellingPages(@NonNull SiteimproveSettings config, @Nullable MultiValueMap<String, String> queryParamContentID);

  @Nullable
  SeoIssuesDocument getSeoIssuePages(@NonNull SiteimproveSettings config);

  @Nullable
  SeoIssuesDocument getSeoIssuePages(@NonNull SiteimproveSettings config, @NonNull String pageId);

  @Nullable
  Seov2IssuesDocument getSeov2IssuePages(@NonNull SiteimproveSettings config, @NonNull String pageId);

  @Nullable
  AccessibilityIssuesDocument getAccessibilityIssuePages(@NonNull SiteimproveSettings config, @NonNull String pageId);

  @Nullable
  PageDetailsDocument getPageDetails(@NonNull SiteimproveSettings config, @NonNull String pageId);

  @Nullable
  BrokenLinkPagesDocument getBrokenLinkPages(@NonNull SiteimproveSettings config);

  @Nullable
  BrokenLinkPagesDocument getBrokenLinkPages(@NonNull SiteimproveSettings config, @Nullable MultiValueMap<String, String> queryParamContentID);

  @Nullable
  AnalyticsSummaryDocument getAnalyticsSummary(@NonNull SiteimproveSettings config);

  @Nullable
  SiteDocument getSite(@NonNull SiteimproveSettings config);

  @Nullable
  PageDocument findPage(@NonNull SiteimproveSettings config, @NonNull Content content);

  @Nullable
  ContentCheckStatusDocument contentCheck(@NonNull SiteimproveSettings config, String body);

  @Nullable
  ContentCheckResultDocument getContentCheckResult(@NonNull SiteimproveSettings config, String contentId);
}
