package com.coremedia.blueprint.feedbackhub.siteimprove.job;

import com.coremedia.blueprint.feedbackhub.siteimprove.HistoryService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class RecrawlPageJobFactory implements JobFactory {

  private SiteimproveService siteimproveService;
  private HistoryService historyService;
  private FeedbackService feedbackService;
  private SitesService sitesService;

  public RecrawlPageJobFactory(SiteimproveService siteimproveService, HistoryService historyService, FeedbackService feedbackService, SitesService sitesService) {
    this.siteimproveService = siteimproveService;
    this.historyService = historyService;
    this.feedbackService = feedbackService;
    this.sitesService = sitesService;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("recrawlPage");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new RecrawlPageJob(siteimproveService, historyService, feedbackService, sitesService);
  }
}
