package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.AccessibilityIssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.BrokenLinkPageDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.BrokenLinkPagesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentQualitySummaryDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.CrawlStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.DciOverallScoreDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageCheckResultDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDetailsDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PagesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Seov2IssueDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Seov2IssuesDocument;
import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.ErrorFeedbackItem;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import com.coremedia.feedbackhub.provider.ContentFeedbackProvider;
import com.coremedia.feedbackhub.provider.FeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveFeedbackHubErrorCode.NOT_PUBLISHED;

/**
 * SiteimproveContentFeedbackProvider provides Feedbacks for the page of the {@link Content}.
 */
@DefaultAnnotation(NonNull.class)
public class SiteimproveContentFeedbackProvider implements ContentFeedbackProvider {
  private static final Logger LOG = LoggerFactory.getLogger(SiteimproveContentFeedbackProvider.class);

  private SiteimproveSettings settings;
  private SiteimproveService siteimproveService;

  SiteimproveContentFeedbackProvider(SiteimproveSettings settings,
                                     SiteimproveService siteimproveService) {
    this.settings = settings;
    this.siteimproveService = siteimproveService;
  }

  public SiteimproveSettings getSettings() {
    return this.settings;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> provideFeedback(Content content) {
    try {
      ContentQualitySummaryDocument previewContentQualitySummary = getContentQualitySummary(settings, content, true);
      ContentQualitySummaryDocument liveContentQualitySummary = getContentQualitySummary(settings, content, false);

      SiteimproveFeedbackItem feedbackItem = new SiteimproveFeedbackItem(previewContentQualitySummary, liveContentQualitySummary);
      return CompletableFuture.completedFuture(feedbackItem)
              .thenApply(this::asFeedbackItems);
    } catch (FeedbackHubException e) {
      ErrorFeedbackItem errorFeedbackItem = new ErrorFeedbackItem(e.getErrorCode(), e.getArguments());
      return CompletableFuture.completedFuture(errorFeedbackItem)
              .thenApply(this::asFeedbackItems);
    } catch (Exception e) {
      LOG.error("Failed to collect siteimprove report for content {}: {}", content, e.getMessage());
      return CompletableFuture.failedFuture(e);
    }
  }

  @Override
  public String getFeedbackType() {
    return SiteimproveFeedbackItem.TYPE;
  }

  private Collection<FeedbackItem> asFeedbackItems(FeedbackItem feedbackItem) {
    return Collections.singleton(feedbackItem);
  }

  private ContentQualitySummaryDocument getContentQualitySummary(SiteimproveSettings config, Content content, Boolean preview) {

    PageDocument page;
    String siteimproveSiteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();

    //when not published there is no summary for live document
    if (!preview && !content.getRepository().getPublicationService().isPublished(content)) {
      page = new PageDocument();
      page.setErrorCode(NOT_PUBLISHED);
      return new ContentQualitySummaryDocument(page, siteimproveSiteId);
    }

    try {
      page = findPage(settings, content, preview);
    } catch (FeedbackHubException e) {
      page = new PageDocument();
      page.setErrorCode(e.getErrorCode());
      return new ContentQualitySummaryDocument(page, siteimproveSiteId);
    }

    MultiValueMap<String, String> queryParamContentID = new LinkedMultiValueMap<>();
    queryParamContentID.add("ids", page.getId());

    ContentQualitySummaryDocument contentQualitySummaryDocument = new ContentQualitySummaryDocument(page, siteimproveSiteId);
    //Creating content quality summary
    DciOverallScoreDocument dciScore = siteimproveService.getDCIScore(settings, siteimproveSiteId, page.getId());
    contentQualitySummaryDocument.setDciOverallScoreDocument(dciScore);

    PageDetailsDocument pageDetailsDocument = siteimproveService.getPageDetails(settings, siteimproveSiteId, page.getId());
    contentQualitySummaryDocument.setPageDetailsDocument(pageDetailsDocument);


    BrokenLinkPagesDocument brokenLinkPagesDocument = siteimproveService.getBrokenLinkPages(settings, siteimproveSiteId, queryParamContentID);
    if (brokenLinkPagesDocument != null && !brokenLinkPagesDocument.getBrokenPages().isEmpty()) {
      BrokenLinkPageDocument brokenLinkPageDocument = brokenLinkPagesDocument.getBrokenPages().get(0);
      contentQualitySummaryDocument.setBrokenLinkPageDocument(brokenLinkPageDocument);
    }

    PagesDocument misspellingPages = siteimproveService.getMisspellingPages(settings, siteimproveSiteId, queryParamContentID);
    if (misspellingPages != null && !misspellingPages.getPages().isEmpty()) {
      PageDocument misspellingPage = misspellingPages.getPages().get(0);
      contentQualitySummaryDocument.setMisspellingPage(misspellingPage);
    }

    Seov2IssuesDocument seoIssuesDocument = siteimproveService.getSeov2IssuePages(settings, siteimproveSiteId, page.getId());
    if(settings.getFilterSeoIssues() != null && settings.getFilterSeoIssues()) {
      List<Seov2IssueDocument> filteredSeov2IssueDocuments = filterSeoIssuesForEditor(seoIssuesDocument.getItems());
      seoIssuesDocument = new Seov2IssuesDocument(filteredSeov2IssueDocuments);
    }
    contentQualitySummaryDocument.setSeov2IssuesDocument(seoIssuesDocument);

    if(settings.getFilterAccessibilityIssues() == null || !settings.getFilterAccessibilityIssues()) {
      AccessibilityIssuesDocument accessibilityIssuePages = siteimproveService.getAccessibilityIssuePages(settings, siteimproveSiteId, page.getId());
      contentQualitySummaryDocument.setAccessibilityIssuesDocument(accessibilityIssuePages);
    }

    CrawlStatusDocument crawlStatus = siteimproveService.getCrawlStatus(settings, siteimproveSiteId);
    contentQualitySummaryDocument.setCrawlStatus(crawlStatus);

    return contentQualitySummaryDocument;
  }

  @Nullable
  private PageDocument findPage(SiteimproveSettings config, Content content, Boolean preview) {
    PageDocument page;
    try {
      String siteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();
      page = siteimproveService.findPage(config, siteId, content);
    } catch (FeedbackHubException e) {
      PageCheckResultDocument pageCheckResultDocument = siteimproveService.pageCheck(config, preview, content, null);
      if (!pageCheckResultDocument.getSuccess()) {
        throw e;
      }
      page = siteimproveService.findPageByUrl(config, content, preview);
      if (page == null) {
        throw e;
      }
      return page;
    }
    return page;
  }

  /**
   * We don't want to display all SEO issues to the editor.
   * When an error is caused by markup he can't do anything about it, so we filter those issues.
   *
   * @param issues the SEO issues
   * @return the list of filtered SEO issues
   */
  private List<Seov2IssueDocument> filterSeoIssuesForEditor(List<Seov2IssueDocument> issues) {
    List<Seov2IssueDocument> result = new ArrayList<>();

    List<String> whitelist = Arrays.stream(SeoIssueTypesEditorWhitelist.class.getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    for (Seov2IssueDocument issue : issues) {
      if (whitelist.contains(issue.getIssueName())) {
        result.add(issue);
      }
    }
    return result;
  }

}
