package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentQualitySummaryDocument;
import com.coremedia.cap.content.Content;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryService {

  private Map<String, Map<Content, ContentQualitySummaryDocument>> history;

  public HistoryService() {
    history = new ConcurrentHashMap<>();
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
