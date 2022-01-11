package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public class ContentQualitySummaryDocument {
  private PageDocument page;

  private PageDetailsDocument pageDetailsDocument;

  private BrokenLinkPagesDocument brokenLinkPagesDocument;
  private DciOverallScoreDocument dciOverallScoreDocument;
  private PagesDocument misspellingPages;
  private AccessibilityIssuesDocument accessibilityIssuesDocument;
  private Seov2IssuesDocument seov2IssuesDocument;
  private CrawlStatusDocument crawlStatus;
  private String siteId;

  public ContentQualitySummaryDocument(@Nullable PageDocument page, @NonNull String siteId) {
    this.page = page;
    this.siteId = siteId;
  }

  @NonNull
  public PageDocument getPage() {
    return page;
  }

  public PageDetailsDocument getPageDetailsDocument() {
    return pageDetailsDocument;
  }

  public void setPageDetailsDocument(PageDetailsDocument pageDetailsDocument) {
    this.pageDetailsDocument = pageDetailsDocument;
  }

  public DciOverallScoreDocument getDciOverallScoreDocument() {
    return dciOverallScoreDocument;
  }

  public void setDciOverallScoreDocument(DciOverallScoreDocument dciOverallScoreDocument) {
    this.dciOverallScoreDocument = dciOverallScoreDocument;
  }

  @Nullable
  public BrokenLinkPagesDocument getBrokenLinkPagesDocument() {
    return brokenLinkPagesDocument;
  }

  public void setBrokenLinkPagesDocument(@Nullable BrokenLinkPagesDocument brokenLinkPagesDocument) {
    this.brokenLinkPagesDocument = brokenLinkPagesDocument;
  }

  @Nullable
  public PagesDocument getMisspellingPages() {
    return misspellingPages;
  }

  public void setMisspellingPages(@Nullable PagesDocument misspellingPages) {
    this.misspellingPages = misspellingPages;
  }

  @Nullable
  public Seov2IssuesDocument getSeov2IssuesDocument() {
    return seov2IssuesDocument;
  }

  public void setSeov2IssuesDocument(@Nullable Seov2IssuesDocument seov2IssuesDocument) {
    this.seov2IssuesDocument = seov2IssuesDocument;
  }

  public AccessibilityIssuesDocument getAccessibilityIssuesDocument() {
    return accessibilityIssuesDocument;
  }

  public void setAccessibilityIssuesDocument(AccessibilityIssuesDocument accessibilityIssuesDocument) {
    this.accessibilityIssuesDocument = accessibilityIssuesDocument;
  }

  public CrawlStatusDocument getCrawlStatus() {
    return crawlStatus;
  }

  public void setCrawlStatus(CrawlStatusDocument crawlStatus) {
    this.crawlStatus = crawlStatus;
  }

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }
}
