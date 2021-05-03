package com.coremedia.blueprint.feedbackhub.siteimprove.itemtypes;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageDocument;
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


  public FooterFeedbackItem(long lastPreviewUpdate, long lastLiveUpdate, PageDocument previewPage, PageDocument livePage) {
    this.lastPreviewUpdate = lastPreviewUpdate;
    this.lastLiveUpdate = lastLiveUpdate;

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

  @Override
  public String getCollection() {
    return FeedbackItemDefaultCollections.footer.name();
  }

  @Override
  public String getType() {
    return "siteimproveFooter";
  }
}
