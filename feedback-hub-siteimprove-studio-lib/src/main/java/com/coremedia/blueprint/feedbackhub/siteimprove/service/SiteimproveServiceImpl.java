package com.coremedia.blueprint.feedbackhub.siteimprove.service;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.AccessibilityIssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.AnalyticsSummaryDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.BrokenLinkPagesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckResultDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.CrawlStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.DciOverallScoreDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.MetatagDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.MetatagsDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDetailsDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PagesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.QualitySummaryDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.SeoIssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Seov2IssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.SiteDocument;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class SiteimproveServiceImpl implements SiteimproveService {

  private SiteimproveRestConnector connector;

  SiteimproveServiceImpl(SiteimproveRestConnector siteimproveRestConnector) {
    this.connector = siteimproveRestConnector;
  }

  @Nullable
  @Override
  public ContentCheckStatusDocument contentCheck(@NonNull SiteimproveSettings config, String body) {
    String resourcePath = "/content/check";
    return connector.performPost(config, resourcePath, ContentCheckStatusDocument.class, null, body);
  }

  @Nullable
  @Override
  public ContentCheckResultDocument getContentCheckResult(@NonNull SiteimproveSettings config, String contentId) {
    String resourcePath = "/content/checks/" + contentId+ "/issues";
    ContentCheckResultDocument contentCheckResultDocument = connector.performGet(config, resourcePath, ContentCheckResultDocument.class, null);
    String message = contentCheckResultDocument.getMessage();
    if(StringUtils.isEmpty(message)) {
      return contentCheckResultDocument;
    }

    return null;
  }

  @Override
  @Nullable
  public CrawlStatusDocument triggerRecrawlSite(@NonNull SiteimproveSettings config) {
    String resourcePath = "/sites/" + config.getSiteId() + "/content/crawl";
    return connector.performGet(config, resourcePath, CrawlStatusDocument.class, null);
  }

  @Override
  @Nullable
  public DciOverallScoreDocument getDCIScore(@NonNull SiteimproveSettings config, @Nullable String pageId) {
    String resourcePath = "/sites/" + config.getSiteId() + "/dci/overview";
    MultiValueMap<String, String> queryParams = null;
    if (pageId != null) {
      queryParams = new LinkedMultiValueMap<>();
      queryParams.add("page_id", pageId);
    }
    return connector.performGet(config, resourcePath, DciOverallScoreDocument.class, queryParams);
  }

  @Override
  @Nullable
  public QualitySummaryDocument getQualitySummary(@NonNull SiteimproveSettings config) {
    String resourcePath = "/sites/" + config.getSiteId() + "/quality_assurance/overview/summary";
    QualitySummaryDocument qualitySummary = connector.performGet(config, resourcePath, QualitySummaryDocument.class, null);

    if (qualitySummary == null) {
      return null;
    }

    resourcePath = "/sites/" + config.getSiteId() + "/seo/issues";
    SeoIssuesDocument seoIssuesDocument = connector.performGet(config, resourcePath, SeoIssuesDocument.class, null);
    if (seoIssuesDocument != null) {
      qualitySummary.setSeoIssues(seoIssuesDocument.getTotalItems());
    }

    return qualitySummary;
  }

  @Override
  @Nullable
  public PagesDocument getMisspellingPages(@NonNull SiteimproveSettings config) {
    return getMisspellingPages(config, null);
  }

  @Nullable
  @Override
  public PagesDocument getMisspellingPages(@NonNull SiteimproveSettings config, @Nullable MultiValueMap<String, String> queryParamContentID) {
    String resourcePath = "/sites/" + config.getSiteId() + "/quality_assurance/spelling/pages";
    return connector.performGet(config, resourcePath, PagesDocument.class, queryParamContentID);
  }

  @Override
  @Nullable
  public SeoIssuesDocument getSeoIssuePages(@NonNull SiteimproveSettings config) {
    String resourcePath = "/sites/" + config.getSiteId() + "/seo/issues?page=1&page_size=100";
    return connector.performGet(config, resourcePath, SeoIssuesDocument.class, null);
  }

  @Nullable
  @Override
  public SeoIssuesDocument getSeoIssuePages(@NonNull SiteimproveSettings config, @NonNull String pageId) {
    //check SEO
    String seoPageIssuesUrl = "/sites/" + config.getSiteId() + "/seo/pages/" + pageId + "/issues";
    return connector.performGet(config, seoPageIssuesUrl, SeoIssuesDocument.class, null);
  }

  @Nullable
  @Override
  public Seov2IssuesDocument getSeov2IssuePages(@NonNull SiteimproveSettings config, @NonNull String pageId) {
    String seov2PageIssuesUrl = "/sites/" + config.getSiteId() + "/seov2/pages/" + pageId + "/issues";
    return connector.performGet(config, seov2PageIssuesUrl, Seov2IssuesDocument.class, null);
  }

  @Nullable
  @Override
  public AccessibilityIssuesDocument getAccessibilityIssuePages(@NonNull SiteimproveSettings config, @NonNull String pageId) {
    String seov2PageIssuesUrl = "/sites/" + config.getSiteId() + "/accessibility/pages/" + pageId + "/issues";
    return connector.performGet(config, seov2PageIssuesUrl, AccessibilityIssuesDocument.class, null);
  }

  @Nullable
  @Override
  public PageDetailsDocument getPageDetails(@NonNull SiteimproveSettings config, @NonNull String pageId) {
    String pageDetailsUrl = "/sites/" + config.getSiteId() + "/content/pages/" + pageId;
    return connector.performGet(config, pageDetailsUrl, PageDetailsDocument.class, null);
  }

  @Nullable
  @Override
  public BrokenLinkPagesDocument getBrokenLinkPages(@NonNull SiteimproveSettings config) {
    return getBrokenLinkPages(config, null);
  }

  @Nullable
  @Override
  public BrokenLinkPagesDocument getBrokenLinkPages(@NonNull SiteimproveSettings config, @Nullable MultiValueMap<String, String> queryParamContentID) {
    String resourcePath = "/sites/" + config.getSiteId() + "/quality_assurance/links/pages_with_broken_links";
    return connector.performGet(config, resourcePath, BrokenLinkPagesDocument.class, null);
  }

  @Nullable
  @Override
  public AnalyticsSummaryDocument getAnalyticsSummary(@NonNull SiteimproveSettings config) {
    String resourcePath = "/sites/" + config.getSiteId() + "/analytics/overview/summary";
    return connector.performGet(config, resourcePath, AnalyticsSummaryDocument.class, null);
  }

  @Nullable
  @Override
  public SiteDocument getSite(@NonNull SiteimproveSettings config) {
    String resourcePath = "/sites/" + config.getSiteId();
    MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
    return connector.performGet(config, resourcePath, SiteDocument.class, queryParam);
  }

  @Nullable
  @Override
  public PageDocument findPage(@NonNull SiteimproveSettings config,
                               @NonNull Content content) {

    MultiValueMap<String, String> contentMetatagQueryParam = new LinkedMultiValueMap<>();
    contentMetatagQueryParam.add("query", createMetaTag(content));
    String metaTagsResourcePath = "/sites/" + config.getSiteId() + "/quality_assurance/inventory/meta_tags";
    MetatagsDocument metatagsDocument = connector.performGet(config, metaTagsResourcePath, MetatagsDocument.class, contentMetatagQueryParam);

    if (metatagsDocument == null || metatagsDocument.getItems().isEmpty()) {
      return null;
    }

    MetatagDocument metatagDocument = metatagsDocument.getItems().get(0);
    metaTagsResourcePath = metaTagsResourcePath + "/" + metatagDocument.getId() + "/contents";
    metatagsDocument = connector.performGet(config, metaTagsResourcePath, MetatagsDocument.class, null);

    if (metatagsDocument == null || metatagsDocument.getItems().isEmpty()) {
      return null;
    }

    metatagDocument = metatagsDocument.getItems().get(0);
    metaTagsResourcePath += "/" + metatagDocument.getId() + "/pages";
    PagesDocument pagesDocument = connector.performGet(config, metaTagsResourcePath, PagesDocument.class, null);

    if (pagesDocument == null || pagesDocument.getPages().isEmpty()) {
      return null;
    }

    return pagesDocument.getPages().get(0);
  }

  private String createMetaTag(@NonNull Content content) {
    return "content:" + IdHelper.parseContentId(content.getId());
  }

}
