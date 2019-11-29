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

  public ContentQualitySummaryDocument(@NonNull PageDocument page) {
    this.page = page;
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
}
