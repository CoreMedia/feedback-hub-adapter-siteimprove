package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentQualitySummaryDocument;
import com.coremedia.cap.content.Content;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryService {

  private Map<String, Map<Content, ContentQualitySummaryDocument>> cache, history;

  public HistoryService() {
    cache = new ConcurrentHashMap<>();
    history = new ConcurrentHashMap<>();
  }

  public ContentQualitySummaryDocument get(Content content, String siteId) {
    Map<Content, ContentQualitySummaryDocument> contentToSummary = cache.get(siteId);

    if (contentToSummary == null) {
      contentToSummary = new ConcurrentHashMap<>();
      cache.put(siteId, contentToSummary);
    }

    return contentToSummary.get(content);
  }

  public void save(Content content, String siteId, ContentQualitySummaryDocument summary) {
    Map<Content, ContentQualitySummaryDocument> contentToSummary = cache.get(siteId);
    Map<Content, ContentQualitySummaryDocument> contentToHistory = history.get(siteId);
    if (contentToHistory == null) {
      contentToHistory = new ConcurrentHashMap<>();
      history.put(siteId, contentToHistory);
    }
    ContentQualitySummaryDocument oldSummary = contentToHistory.get(content);
    summary.setLast(oldSummary);
    contentToSummary.put(content, summary);
  }

  public void invalidate(Content content, String siteId) {
    Map<Content, ContentQualitySummaryDocument> contentToSummary = cache.get(siteId);

    if (contentToSummary != null) {
      ContentQualitySummaryDocument summary = contentToSummary.get(content);
      if (summary != null) {
        //move the summary from cache to history
        contentToSummary.remove(content);
        Map<Content, ContentQualitySummaryDocument> contentToHistory = history.get(siteId);
        if (summary.getLast() != null) {
          summary.setLast(null);
        }
        contentToHistory.put(content, summary);
      }
    }
  }

  public ContentQualitySummaryDocument withHistory(Content content, String siteId, ContentQualitySummaryDocument summary) {

    ContentQualitySummaryDocument oldSummary = fromHistory(content, siteId, summary);
    if (oldSummary != null && !isFromSameCrawl(oldSummary, summary)) {
      summary.setLast(oldSummary);
    }
    return summary;
  }

  private ContentQualitySummaryDocument fromHistory(Content content, String siteId, ContentQualitySummaryDocument summary) {
    Map<Content, ContentQualitySummaryDocument> contentToSummary = history.get(siteId);

    if (contentToSummary == null) {
      contentToSummary = new ConcurrentHashMap<>();
      history.put(siteId, contentToSummary);
    }

    ContentQualitySummaryDocument oldSummary = contentToSummary.get(content);
    contentToSummary.put(content, summary);

    return oldSummary;
  }

  private boolean isFromSameCrawl(@NonNull ContentQualitySummaryDocument summary1, @NonNull ContentQualitySummaryDocument summary2) {
    return summary1.getPageDetailsDocument().getSummary().getPage().getLastSeen().equals(
            summary2.getPageDetailsDocument().getSummary().getPage().getLastSeen());
  }
}
