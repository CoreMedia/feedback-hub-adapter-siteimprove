package com.coremedia.labs.plugins.adapters.siteimprove.service;

import com.coremedia.labs.plugins.adapters.siteimprove.SiteimproveFeedbackHubErrorCode;
import com.coremedia.labs.plugins.adapters.siteimprove.SiteimproveSettings;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.A11yPageIssuesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.AccessibilityIssuesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.AnalyticsSummaryDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.BrokenLinkPagesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.ContentCheckIssuesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.ContentCheckResultDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.CrawlResultDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.CrawlStatusDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.DciOverallScoreDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.MetatagNameContentDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.MetatagNameContentListDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.MetatagNameDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.MetatagNameListDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.PageCheckResultDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.PageCheckStatusDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.PageDetailsDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.PageDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.PagesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.QualitySummaryDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.SeoIssuesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.Seov2IssuesDocument;
import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.SiteDocument;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SiteimproveServiceImpl implements SiteimproveService {

  private static final String SITES = "/sites/";
  private SiteimproveRestConnector connector;
  private ContentLinkBuilderFactory contentLinkBuilderFactory;

  SiteimproveServiceImpl(SiteimproveRestConnector siteimproveRestConnector) {
    this.connector = siteimproveRestConnector;
    this.contentLinkBuilderFactory = new ContentLinkBuilderFactory();
  }

  @Nullable
  @Override
  public CrawlResultDocument crawl(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    String resourcePath = SITES + siteId + "/content/crawl";
    return connector.performPost(config, resourcePath, CrawlResultDocument.class, null);
  }

  @Override
  @Nullable
  public CrawlStatusDocument getCrawlStatus(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    String resourcePath = SITES + siteId + "/content/crawl";
    return connector.performGet(config, resourcePath, CrawlStatusDocument.class, null);
  }

  @Nullable
  @Override
  public PageCheckResultDocument pageCheck(@NonNull SiteimproveSettings config, @NonNull Boolean preview, @NonNull Content content, @Nullable String pageId) {
    String resourcePath = getPageCheckResourcePath(config, preview, pageId);
    MultiValueMap<String, String> queryParams = getPageUrlQueryParams(config, preview, content, pageId);

    return connector.performPost(config, resourcePath, PageCheckResultDocument.class, queryParams);
  }

  @Nullable
  @Override
  public PageCheckStatusDocument getPageCheckStatus(@NonNull SiteimproveSettings config, @NonNull Boolean preview, @NonNull Content content, @Nullable String pageId) {
    String resourcePath = getPageCheckResourcePath(config, preview, pageId);
    MultiValueMap<String, String> queryParams = getPageUrlQueryParams(config, preview, content, pageId);

    return connector.performGet(config, resourcePath, PageCheckStatusDocument.class, queryParams);
  }

  private String getPageCheckResourcePath(@NonNull SiteimproveSettings config, @NonNull Boolean preview, @Nullable String pageId) {
    String siteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();
    return pageId == null ? SITES + siteId + "/content/check/page" : SITES + siteId + "/content/check/page/" + pageId;
  }

  private MultiValueMap<String, String> getPageUrlQueryParams(@NonNull SiteimproveSettings config, @NonNull Boolean preview, @NonNull Content content, @Nullable String pageId) {
    MultiValueMap<String, String> queryParams = null;

    if (pageId == null) {
      queryParams = new LinkedMultiValueMap<>();
      String pageUrl = findPageUrl(config, content, preview);
      queryParams.add("url", pageUrl);
    }

    return queryParams;
  }


  @Nullable
  @Override
  public ContentCheckResultDocument contentCheck(@NonNull SiteimproveSettings config, @NonNull String body) {
    return connector.performPost(config, "/content/check", ContentCheckResultDocument.class, null, body);
  }

  @Nullable
  @Override
  public ContentCheckIssuesDocument getContentCheckIssues(@NonNull SiteimproveSettings config, @NonNull String contentId) {
    String resourcePath = "/content/checks/" + contentId + "/issues";
    ContentCheckIssuesDocument contentCheckIssuesDocument = connector.performGet(config, resourcePath, ContentCheckIssuesDocument.class, null);
    String message = contentCheckIssuesDocument.getMessage();
    if (StringUtils.isEmpty(message)) {
      return contentCheckIssuesDocument;
    }

    return null;
  }

  @Override
  @Nullable
  public DciOverallScoreDocument getDCIScore(@NonNull SiteimproveSettings config, @NonNull String siteId, @Nullable String pageId) {
    String resourcePath = SITES + siteId + "/dci/overview";
    MultiValueMap<String, String> queryParams = null;
    if (pageId != null) {
      queryParams = new LinkedMultiValueMap<>();
      queryParams.add("page_id", pageId);
    }
    return connector.performGet(config, resourcePath, DciOverallScoreDocument.class, queryParams);
  }

  @Override
  @Nullable
  public QualitySummaryDocument getQualitySummary(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    String resourcePath = SITES + siteId + "/quality_assurance/overview/summary";
    QualitySummaryDocument qualitySummary = connector.performGet(config, resourcePath, QualitySummaryDocument.class, null);

    if (qualitySummary == null) {
      return null;
    }

    resourcePath = SITES + siteId + "/seo/issues";
    SeoIssuesDocument seoIssuesDocument = connector.performGet(config, resourcePath, SeoIssuesDocument.class, null);
    if (seoIssuesDocument != null) {
      qualitySummary.setSeoIssues(seoIssuesDocument.getTotalItems());
    }

    return qualitySummary;
  }

  @Override
  @Nullable
  public PagesDocument getMisspellingPages(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    return getMisspellingPages(config, siteId, null);
  }

  @Nullable
  @Override
  public PagesDocument getMisspellingPages(@NonNull SiteimproveSettings config, @NonNull String siteId, @Nullable String pageId) {
    MultiValueMap<String, String> queryParams = null;
    if (pageId != null) {
      queryParams = new LinkedMultiValueMap<>();
      queryParams.add("page_size", "500");
    }

    String resourcePath = SITES + siteId + "/quality_assurance/spelling/pages";
    PagesDocument pagesDocument = connector.performGet(config, resourcePath, PagesDocument.class, queryParams);
    PagesDocument newPagesDocument = new PagesDocument();
    newPagesDocument.setPages(pagesDocument.getPages().stream().filter(page -> page.getId().equals(pageId)).collect(Collectors.toList()));
    newPagesDocument.setSiteimprove(pagesDocument.getSiteimprove());
    newPagesDocument.setLinks(pagesDocument.getLinks());
    return newPagesDocument;
  }

  @Override
  @Nullable
  public SeoIssuesDocument getSeoIssuePages(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    String resourcePath = SITES + siteId + "/seo/issues?page=1&page_size=100";
    return connector.performGet(config, resourcePath, SeoIssuesDocument.class, null);
  }

  @Nullable
  @Override
  public SeoIssuesDocument getSeoIssuePages(@NonNull SiteimproveSettings config, @NonNull String siteId, @NonNull String pageId) {
    //check SEO
    String seoPageIssuesUrl = SITES + siteId + "/seo/pages/" + pageId + "/issues";
    return connector.performGet(config, seoPageIssuesUrl, SeoIssuesDocument.class, null);
  }

  @Nullable
  @Override
  public Seov2IssuesDocument getSeov2IssuePages(@NonNull SiteimproveSettings config, @NonNull String siteId, @NonNull String pageId) {
    String seov2PageIssuesUrl = SITES + siteId + "/seov2/pages/" + pageId + "/issues";
    Seov2IssuesDocument issues = connector.performGet(config, seov2PageIssuesUrl, Seov2IssuesDocument.class, null);

    //paging
    Seov2IssuesDocument nextIssues = issues;
    while (nextIssues.hasNext()) {
      nextIssues = connector.performGet(config, nextIssues.nextUrl(), Seov2IssuesDocument.class, null);
      issues.add(nextIssues);
    }
    return issues;
  }

  @Nullable
  @Override
  public AccessibilityIssuesDocument getAccessibilityIssuePages(@NonNull SiteimproveSettings config, @NonNull String siteId, @NonNull String pageId) {
    String seov2PageIssuesUrl = SITES + siteId + "/accessibility/pages/" + pageId + "/issues";
    AccessibilityIssuesDocument issues = connector.performGet(config, seov2PageIssuesUrl, AccessibilityIssuesDocument.class, null);

    return issues;
  }

  @Nullable
  @Override
  public A11yPageIssuesDocument getAccessibilityPageIssues(@NonNull SiteimproveSettings config, @NonNull String siteId, @NonNull String pageId) {
    String url = SITES + siteId + "/accessibility/pages?ids=" + pageId;
    return connector.performGet(config, url, A11yPageIssuesDocument.class, null);
  }

  @Nullable
  @Override
  public PageDetailsDocument getPageDetails(@NonNull SiteimproveSettings config, @NonNull String siteId, @NonNull String pageId) {
    String pageDetailsUrl = SITES + siteId + "/content/pages/" + pageId;
    return connector.performGet(config, pageDetailsUrl, PageDetailsDocument.class, null);
  }

  @Nullable
  @Override
  public BrokenLinkPagesDocument getBrokenLinkPages(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    return getBrokenLinkPages(config, siteId, null);
  }

  @Nullable
  @Override
  public BrokenLinkPagesDocument getBrokenLinkPages(@NonNull SiteimproveSettings config, @NonNull String siteId, @Nullable MultiValueMap<String, String> queryParams) {
    String resourcePath = SITES + siteId + "/quality_assurance/links/pages_with_broken_links";
    return connector.performGet(config, resourcePath, BrokenLinkPagesDocument.class, queryParams);
  }

  @Nullable
  @Override
  public AnalyticsSummaryDocument getAnalyticsSummary(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    String resourcePath = SITES + siteId + "/analytics/overview/summary";
    return connector.performGet(config, resourcePath, AnalyticsSummaryDocument.class, null);
  }

  @Nullable
  @Override
  public SiteDocument getSite(@NonNull SiteimproveSettings config, @NonNull String siteId) {
    String resourcePath = SITES + siteId;
    MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
    return connector.performGet(config, resourcePath, SiteDocument.class, queryParam);
  }

  /**
   * Find a page to the given content using the siteImprove metatag service:
   * This means that you can now add your meta-tags with the id properly in the content-attribute, not tacked onto the tag-name itself. Something like this:
   *
   * <meta name="coremedia:content-id" content="xyz123_the_id_goes_here">
   * <p>
   * And then find these exact pages by searching for content for that tag matching exactly the desired id. The search, like for the meta-tag name itself, is case-insensitive.
   * The query-parameter for doing that search will be like what is used for e.g. the meta-tags themselves, namely “query= xyz123_the_id_goes_here“.
   * <p>
   * The documentation for the endpoint will be updated once deployed:
   * https://api.siteimprove.com/v2/documentation#!/Quality_Assurance/get_sites_site_id_quality_assurance_inventory_meta_tags_meta_name_id_contents
   */
  @NonNull
  @Override
  public PageDocument findPage(@NonNull SiteimproveSettings config,
                               @NonNull String siteId,
                               @NonNull Content content) {

    MultiValueMap<String, String> metatagNameQueryParam = new LinkedMultiValueMap<>();
    metatagNameQueryParam.add("query", "coremedia:content-id");
    String metaTagsResourcePath = SITES + siteId + "/quality_assurance/inventory/meta_tags";
    MetatagNameListDocument metatagNameListDocument = connector.performGet(config, metaTagsResourcePath,
            MetatagNameListDocument.class, metatagNameQueryParam);

    if (metatagNameListDocument == null || metatagNameListDocument.getItems().isEmpty()) {
      throw new FeedbackHubException("No Content Meta-Tag found", SiteimproveFeedbackHubErrorCode.NO_CONTENT_METATAG_FOUND);
    }

    MetatagNameDocument metatagNameDocument = metatagNameListDocument.getItems().get(0);
    MultiValueMap<String, String> metatagNameContentQueryParam = new LinkedMultiValueMap<>();
    String coreMediaContentId = String.valueOf(IdHelper.parseContentId(content.getId()));
    metatagNameContentQueryParam.add("query", coreMediaContentId);
    MetatagNameContentListDocument metatagNameContentListDocument = connector.performGet(config, metatagNameDocument.getContentsUrl(),
            MetatagNameContentListDocument.class, metatagNameContentQueryParam);

    if (metatagNameContentListDocument == null || metatagNameContentListDocument.getItems().isEmpty()) {
      throw new FeedbackHubException("No Content Meta-Tag with the content found",
              SiteimproveFeedbackHubErrorCode.NO_CONTENT_METATAG_WITH_CONTENT_ID_FOUND);
    }

    MetatagNameContentDocument metatagNameContentDocument = getExactMatch(metatagNameContentListDocument.getItems(), coreMediaContentId);
    PagesDocument pagesDocument = connector.performGet(config, metatagNameContentDocument.getPagesUrl(), PagesDocument.class, null);

    if (pagesDocument == null || pagesDocument.getPages().isEmpty()) {
      throw new FeedbackHubException("No content found",
              SiteimproveFeedbackHubErrorCode.NO_CONTENT_FOUND);
    }

    return pagesDocument.getPages().get(0);
  }

  private MetatagNameContentDocument getExactMatch(List<MetatagNameContentDocument> items, String coreMediaContentId) {
    if (items == null || items.size() == 0) {
      throw new FeedbackHubException("No Content found",
              SiteimproveFeedbackHubErrorCode.NO_CONTENT_FOUND);
    }
    Optional<MetatagNameContentDocument> firstHit = items.stream().filter(item -> item.getContent().equals(coreMediaContentId)).findFirst();
    if (firstHit.isEmpty()) throw new FeedbackHubException("No exact content match found for content id:" + coreMediaContentId, SiteimproveFeedbackHubErrorCode.NO_CONTENT_FOUND);

    return firstHit.orElse(null);
  }

  @Nullable
  @Override
  public PageDocument findPageByUrl(@NonNull SiteimproveSettings config, @NonNull Content content, Boolean preview) {
    String pageUrl = findPageUrl(config, content, preview);
    String siteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();

    String resourcePath = SITES + siteId + "/content/pages";
    MultiValueMap<String, String> queryParams = getPageUrlQueryParams(config, preview, content, null);

    PagesDocument pagesDocument = connector.performGet(config, resourcePath, PagesDocument.class, queryParams);

    if (pagesDocument == null || pagesDocument.getPages().isEmpty()) {
      throw new FeedbackHubException("No content found",
              SiteimproveFeedbackHubErrorCode.NO_CONTENT_FOUND);
    }

    return pagesDocument.getPages().get(0);
  }

  private String findPageUrl(@NonNull SiteimproveSettings config, @NonNull Content content, Boolean preview) {
    ContentLinkBuilder contentLinkBuilder = contentLinkBuilderFactory.create(config);
    return contentLinkBuilder.buildLink(preview, content);
  }

}
