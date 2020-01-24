package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public class ContentQualitySummaryDocument {
  private PageDocument page;

  private PageDetailsDocument pageDetailsDocument;

  private BrokenLinkPageDocument brokenLinkPageDocument;
  private DciOverallScoreDocument dciOverallScoreDocument;
  private PageDocument misspellingPage;
  private AccessibilityIssuesDocument accessibilityIssuesDocument;
  private Seov2IssuesDocument seov2IssuesDocument;
  private CrawlStatusDocument crawlStatus;
  private String siteId;

  private ContentQualitySummaryDocument last;

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
  public BrokenLinkPageDocument getBrokenLinkPageDocument() {
    return brokenLinkPageDocument;
  }

  public void setBrokenLinkPageDocument(@Nullable BrokenLinkPageDocument brokenLinkPageDocument) {
    this.brokenLinkPageDocument = brokenLinkPageDocument;
  }

  @Nullable
  public PageDocument getMisspellingPage() {
    return misspellingPage;
  }

  public void setMisspellingPage(@Nullable PageDocument misspellingPage) {
    this.misspellingPage = misspellingPage;
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

  public ContentQualitySummaryDocument getLast() {
    return last;
  }

  public void setLast(ContentQualitySummaryDocument last) {
    this.last = last;
  }
}
