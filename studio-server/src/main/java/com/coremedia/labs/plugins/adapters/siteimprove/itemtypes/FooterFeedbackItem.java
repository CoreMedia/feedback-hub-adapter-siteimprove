package com.coremedia.labs.plugins.adapters.siteimprove.itemtypes;

import com.coremedia.labs.plugins.adapters.siteimprove.service.documents.PageDocument;
import com.coremedia.feedbackhub.FeedbackItemDefaultCollections;
import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 *
 */
public class FooterFeedbackItem implements FeedbackItem {

  private final long lastPreviewUpdate;
  private final long lastLiveUpdate;
  private String previewPageId;
  private String livePageId;
  private boolean isChecking;


  public FooterFeedbackItem(long lastPreviewUpdate, long lastLiveUpdate, PageDocument previewPage, PageDocument livePage, boolean isChecking) {
    this.lastPreviewUpdate = lastPreviewUpdate;
    this.lastLiveUpdate = lastLiveUpdate;
    this.isChecking = isChecking;


    if(previewPage != null) {
      previewPageId = previewPage.getId();
    }

    if(livePage != null) {
      livePageId = livePage.getId();
    }
  }

  public String getPreviewPageId() {
    return previewPageId;
  }

  public String getLivePageId() {
    return livePageId;
  }

  public long getLastPreviewUpdate() {
    return lastPreviewUpdate;
  }

  public long getLastLiveUpdate() {
    return lastLiveUpdate;
  }

  public boolean isChecking() {
    return isChecking;
  }

  @Override
  public String getCollection() {
    return FeedbackItemDefaultCollections.footer.name();
  }

  @Override
  public String getType() {
    return "siteimproveFooter";
  }
}
