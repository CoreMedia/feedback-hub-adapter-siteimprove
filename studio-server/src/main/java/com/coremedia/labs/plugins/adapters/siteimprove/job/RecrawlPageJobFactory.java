package com.coremedia.labs.plugins.adapters.siteimprove.job;

import com.coremedia.labs.plugins.adapters.siteimprove.service.SiteimproveService;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class RecrawlPageJobFactory implements JobFactory {

  private SiteimproveService siteimproveService;
  private FeedbackService feedbackService;
  private SitesService sitesService;

  public RecrawlPageJobFactory(SiteimproveService siteimproveService, FeedbackService feedbackService, SitesService sitesService) {
    this.siteimproveService = siteimproveService;
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
    return new RecrawlPageJob(siteimproveService, feedbackService, sitesService);
  }
}
