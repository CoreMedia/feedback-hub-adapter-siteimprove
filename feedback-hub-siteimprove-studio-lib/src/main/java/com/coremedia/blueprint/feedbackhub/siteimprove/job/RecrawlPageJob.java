package com.coremedia.blueprint.feedbackhub.siteimprove.job;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.cap.content.Content;
import com.coremedia.rest.cap.jobs.GenericJobErrorCode;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobContext;
import com.coremedia.rest.cap.jobs.JobExecutionException;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecrawlPageJob implements Job {
  private static final Logger LOG = LoggerFactory.getLogger(RecrawlPageJob.class);

  private Boolean preview;
  private Content content;
  private String pageId;
  private SiteimproveService siteimproveService;

  public RecrawlPageJob(SiteimproveService siteimproveService) {
    this.siteimproveService = siteimproveService;
  }

  @JsonProperty("preview")
  public void setPreview(Boolean preview) {
    this.preview = preview;
  }

  @JsonProperty("content")
  public void setContent(Content content) {
    this.content = content;
  }

  @JsonProperty("pageId")
  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  @Nullable
  @Override
  public Object call(@NonNull JobContext jobContext) throws JobExecutionException {
    SiteimproveSettings config = getConfig(content);
    String siteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();
    try {
      return siteimproveService.pageCheck(config, siteId, pageId);
    } catch (Exception e) {
      LOG.error("Cannot trigger page recrawl for {} / {}", siteId, pageId, e);
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }

  private SiteimproveSettings getConfig(Content content) {
    //TODO
    return null;
  }
}
