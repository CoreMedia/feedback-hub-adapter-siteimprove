package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes.ComparingGaugeFeedbackItem;
import com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes.ComparingGaugeFeedbackItemBuilder;
import com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes.FooterFeedbackItem;
import com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes.IssueListFeedbackItem;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.A11yPageIssueDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.A11yPageIssuesDocument;
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
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import com.coremedia.feedbackhub.items.ComparingScoreBarFeedbackItem;
import com.coremedia.feedbackhub.items.ExternalLinkFeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItemFactory;
import com.coremedia.feedbackhub.items.GaugeFeedbackItem;
import com.coremedia.feedbackhub.items.LabelFeedbackItem;
import com.coremedia.feedbackhub.items.ScoreBarFeedbackItem;
import com.coremedia.feedbackhub.provider.FeedbackProvider;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Provides {@link FeedbackItem}s for the page of the {@link Content}.
 */
@DefaultAnnotation(NonNull.class)
public class SiteimproveContentFeedbackProvider implements FeedbackProvider {
  public static final String TYPE = "siteimprove";
  private static final Logger LOG = LoggerFactory.getLogger(SiteimproveContentFeedbackProvider.class);

  private final SiteimproveSettings settings;
  private final SiteimproveService siteimproveService;

  SiteimproveContentFeedbackProvider(SiteimproveSettings settings,
                                     SiteimproveService siteimproveService) {
    this.settings = settings;
    this.siteimproveService = siteimproveService;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> provideFeedback(FeedbackContext context) {
    Content content = (Content) context.getEntity();
    List<FeedbackItem> items = new ArrayList<>();
    long lastPreviewUpdate = -1;
    long lastLiveUpdate = -1;

    try {
      PageDocument previewPage = findPage(settings, content, true);
      PageDocument livePage = null;
      ContentQualitySummaryDocument previewContentQualitySummary = getContentQualitySummary(settings, previewPage, true);
      if (previewContentQualitySummary.getPageDetailsDocument().getSummary().getPage().getLastSeen()!=  null) {
        lastPreviewUpdate = previewContentQualitySummary.getPageDetailsDocument().getSummary().getPage().getLastSeen().getTime();
      }
        generatePreviewTab(items, previewContentQualitySummary, lastPreviewUpdate, lastLiveUpdate);

        //when not published there is no summary for live document
        if (content.getRepository().getPublicationService().isPublished(content)) {
          livePage = findPage(settings, content, false);
          ContentQualitySummaryDocument liveContentQualitySummary = getContentQualitySummary(settings, livePage, false);
          lastLiveUpdate = liveContentQualitySummary.getPageDetailsDocument().getSummary().getPage().getLastSeen().getTime();
          generateLiveTab(items, previewContentQualitySummary, liveContentQualitySummary, lastPreviewUpdate, lastLiveUpdate);
        } else {
          items.add(FeedbackItemFactory.createEmptyItem(SiteimproveFeedbackTabs.COMPARISON));
        }
      //add footer
      items.add(new FooterFeedbackItem(lastPreviewUpdate, lastLiveUpdate, previewPage, livePage));
    } catch (Exception e) {
      return CompletableFuture.failedFuture(e);
    }

    return CompletableFuture.completedFuture(items);
  }

  /**
   * Generates the preview tab.
   *  @param items                        the list of items available for Siteimprove
   * @param previewContentQualitySummary the summary document which contains all data to display feedback for
   * @param lastPreviewUpdate
   * @param lastLiveUpdate
   */
  private void generatePreviewTab(List<FeedbackItem> items, ContentQualitySummaryDocument previewContentQualitySummary, long lastPreviewUpdate, long lastLiveUpdate) {
    GaugeFeedbackItem gaugeItem = GaugeFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.PREVIEW)
            .withTitle("siteimprove_digitalCertaintyIndex")
            .withAge(lastPreviewUpdate)
            .withLink(previewContentQualitySummary.getPageDetailsDocument().getSiteimprove().getSeo().getPageReport().getHref(), "siteimprove_preview_site_link")
            .withValue(previewContentQualitySummary.getDciOverallScoreDocument().getTotalScore(), -1, true)
            .build();
    items.add(gaugeItem);

    ScoreBarFeedbackItem a11yScore = ScoreBarFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.PREVIEW)
            .withLabel("siteimprove_a11y")
            .withDecimalPlaces(2)
            .withValue(previewContentQualitySummary.getDciOverallScoreDocument().getAccessibilityDocument().getTotal())
            .build();
    items.add(a11yScore);


    ScoreBarFeedbackItem qaScore = ScoreBarFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.PREVIEW)
            .withLabel("siteimprove_quality")
            .withDecimalPlaces(2)
            .withValue(previewContentQualitySummary.getDciOverallScoreDocument().getQaDocument().getTotal())
            .build();
    items.add(qaScore);


    ScoreBarFeedbackItem seoScore = ScoreBarFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.PREVIEW)
            .withLabel("siteimprove_seo")
            .withDecimalPlaces(2)
            .withValue(previewContentQualitySummary.getDciOverallScoreDocument().getSeoDocument().getTotal())
            .build();
    items.add(seoScore);

    if (previewContentQualitySummary.getAccessibilityIssuesDocument().getItems() != null && !previewContentQualitySummary.getAccessibilityIssuesDocument().getItems().isEmpty()) {
      A11yPageIssueDocument a11yPageIssueDocument = previewContentQualitySummary.getAccessibilityIssuesDocument().getItems().get(0);

      items.add(LabelFeedbackItem.builder()
              .withCollection(SiteimproveFeedbackTabs.PREVIEW)
              .withBold()
              .withLabel("siteimprove_issues_a11y_count", previewContentQualitySummary.getAccessibilityIssuesDocument().getItems().size())
              .build());

      items.add(ExternalLinkFeedbackItem.builder()
              .withCollection(SiteimproveFeedbackTabs.PREVIEW)
              .withText("siteimprove_issue_link")
              .withUrl(a11yPageIssueDocument.getUrl())
              .build());
    }

    if (previewContentQualitySummary.getMisspellingPages() != null && previewContentQualitySummary.getMisspellingPages().getPages() != null && !previewContentQualitySummary.getMisspellingPages().getPages().isEmpty()) {
      items.add(LabelFeedbackItem.builder()
              .withCollection(SiteimproveFeedbackTabs.PREVIEW)
              .withBold()
              .withLabel("siteimprove_issues_qa_count", previewContentQualitySummary.getMisspellingPages().getPages().size())
              .build());

      PageDocument pageDocument = previewContentQualitySummary.getMisspellingPages().getPages().get(0);
      items.add(ExternalLinkFeedbackItem.builder()
              .withCollection(SiteimproveFeedbackTabs.PREVIEW)
              .withText("siteimprove_issue_link")
              .withUrl(pageDocument.getUrl())
              .build());
    }

    IssueListFeedbackItem issues = new IssueListFeedbackItem(SiteimproveFeedbackTabs.PREVIEW, null);
    if (previewContentQualitySummary.getSeov2IssuesDocument() != null) {
      List<Seov2IssueDocument> seov2IssueDocuments = previewContentQualitySummary.getSeov2IssuesDocument().getItems();
      for (Seov2IssueDocument issue : seov2IssueDocuments) {
        issues.addIssue(issue);
      }
    }
    items.add(LabelFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.PREVIEW)
            .withBold()
            .withLabel("siteimprove_issues_seo_count", issues.getIssues().size())
            .build());
    items.add(issues);
  }

  /**
   * Generates the live tab, may be empty when the document has not been published yet.
   *
   * @param items                        the list of feedback items for Siteimprove
   * @param previewContentQualitySummary the overall feedback from Siteimprove for the current of the document
   * @param liveContentQualitySummary    the overall feedback from Siteimprove for the published document
   * @param lastPreviewUpdate            timestamp of the last preview update
   * @param lastLiveUpdate               timestamp of the last live update
   */
  private void generateLiveTab(List<FeedbackItem> items, ContentQualitySummaryDocument previewContentQualitySummary, ContentQualitySummaryDocument liveContentQualitySummary, long lastPreviewUpdate, long lastLiveUpdate) {
    ComparingGaugeFeedbackItem comparingGaugeFeedbackItem = new ComparingGaugeFeedbackItemBuilder()
            .withCollection(SiteimproveFeedbackTabs.COMPARISON)
            .withTitle("siteimprove_digitalCertaintyIndex")
            .withGauge1Value(previewContentQualitySummary.getDciOverallScoreDocument().getTotalScore(), -1, 1)
            .withGauge2Value(liveContentQualitySummary.getDciOverallScoreDocument().getTotalScore(), -1, 1)
            .withGaugeAge1(lastPreviewUpdate)
            .withGaugeAge2(lastLiveUpdate)
            .withScore1Color("#006CB3")
            .withScore2Color("#A94998")
            .withGauge1Link(previewContentQualitySummary.getPageDetailsDocument().getSiteimprove().getSeo().getPageReport().getHref(), "siteimprove_preview_site_link")
            .withGauge2Link(liveContentQualitySummary.getPageDetailsDocument().getSiteimprove().getSeo().getPageReport().getHref(), "siteimprove_live_site_link")
            .build();
    items.add(comparingGaugeFeedbackItem);


    float a11yPreview = (float) previewContentQualitySummary.getDciOverallScoreDocument().getAccessibilityDocument().getTotal();
    float a11yLive = (float) liveContentQualitySummary.getDciOverallScoreDocument().getAccessibilityDocument().getTotal();
    ComparingScoreBarFeedbackItem a11yCompare = ComparingScoreBarFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.COMPARISON)
            .withDecimalPlaces(2)
            .withTitle(null)
            .withBarTitle("siteimprove_scoreDetails")
            .withLabel("siteimprove_a11y")
            .withUnitLabel("siteimprove_points")
            .withUnitTitle("siteimprove_scoreDifferences")
            .withScore1Value(a11yPreview)
            .withScore2Value(a11yLive)
            .withScore1Color("#006CB3")
            .withScore2Color("#A94998")
            .build();
    items.add(a11yCompare);


    float qaPreview = (float) previewContentQualitySummary.getDciOverallScoreDocument().getQaDocument().getTotal();
    float qaLive = (float) liveContentQualitySummary.getDciOverallScoreDocument().getQaDocument().getTotal();
    ComparingScoreBarFeedbackItem qaCompare = ComparingScoreBarFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.COMPARISON)
            .withDecimalPlaces(2)
            .withLabel("siteimprove_quality")
            .withUnitLabel("siteimprove_points")
            .withScore1Value(qaPreview)
            .withScore2Value(qaLive)
            .withScore1Color("#006CB3")
            .withScore2Color("#A94998")
            .build();
    items.add(qaCompare);

    float seoPreview = (float) previewContentQualitySummary.getDciOverallScoreDocument().getSeoDocument().getTotal();
    float seoLive = (float) liveContentQualitySummary.getDciOverallScoreDocument().getSeoDocument().getTotal();
    ComparingScoreBarFeedbackItem seoCompare = ComparingScoreBarFeedbackItem.builder()
            .withCollection(SiteimproveFeedbackTabs.COMPARISON)
            .withDecimalPlaces(2)
            .withLabel("siteimprove_seo")
            .withUnitLabel("siteimprove_points")
            .withScore1Value(seoPreview)
            .withScore2Value(seoLive)
            .withScore1Color("#006CB3")
            .withScore2Color("#A94998")
            .build();
    items.add(seoCompare);

  }

  private ContentQualitySummaryDocument getContentQualitySummary(SiteimproveSettings config, PageDocument page, boolean preview) {
    String siteimproveSiteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();

    MultiValueMap<String, String> queryParamContentID = new LinkedMultiValueMap<>();
    queryParamContentID.add("ids", page.getId());
    queryParamContentID.add("page_id", page.getId());

    ContentQualitySummaryDocument contentQualitySummaryDocument = new ContentQualitySummaryDocument(page, siteimproveSiteId);
    //Creating content quality summary
    DciOverallScoreDocument dciScore = siteimproveService.getDCIScore(settings, siteimproveSiteId, page.getId());
    contentQualitySummaryDocument.setDciOverallScoreDocument(dciScore);

    PageDetailsDocument pageDetailsDocument = siteimproveService.getPageDetails(settings, siteimproveSiteId, page.getId());
    contentQualitySummaryDocument.setPageDetailsDocument(pageDetailsDocument);

    BrokenLinkPagesDocument brokenLinkPagesDocument = siteimproveService.getBrokenLinkPages(settings, siteimproveSiteId, queryParamContentID);
    contentQualitySummaryDocument.setBrokenLinkPagesDocument(brokenLinkPagesDocument);

    PagesDocument misspellingPages = siteimproveService.getMisspellingPages(settings, siteimproveSiteId, queryParamContentID);
    contentQualitySummaryDocument.setMisspellingPages(misspellingPages);

    Seov2IssuesDocument seoV2IssuesDocument = siteimproveService.getSeov2IssuePages(settings, siteimproveSiteId, page.getId());
    contentQualitySummaryDocument.setSeov2IssuesDocument(seoV2IssuesDocument);

    A11yPageIssuesDocument accessibilityIssuePages = siteimproveService.getAccessibilityPageIssues(settings, siteimproveSiteId, page.getId());
    contentQualitySummaryDocument.setAccessibilityIssuesDocument(accessibilityIssuePages);

    CrawlStatusDocument crawlStatus = siteimproveService.getCrawlStatus(settings, siteimproveSiteId);
    contentQualitySummaryDocument.setCrawlStatus(crawlStatus);

    return contentQualitySummaryDocument;
  }

  @NonNull
  private PageDocument findPage(SiteimproveSettings config, Content content, boolean preview) {
    PageDocument page;
    try {
      String siteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();
      page = siteimproveService.findPage(config, siteId, content);
    } catch (FeedbackHubException e) {
      PageCheckResultDocument pageCheckResultDocument = siteimproveService.pageCheck(config, preview, content, null);
      if (pageCheckResultDocument == null || !pageCheckResultDocument.getSuccess()) {
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
}
