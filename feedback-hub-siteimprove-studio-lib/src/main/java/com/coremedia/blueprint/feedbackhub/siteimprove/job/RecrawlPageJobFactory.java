package com.coremedia.blueprint.feedbackhub.siteimprove.job;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class RecrawlPageJobFactory implements JobFactory {

  private SiteimproveService siteimproveService;

  public RecrawlPageJobFactory(SiteimproveService siteimproveService) {
    this.siteimproveService = siteimproveService;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("recrawlPage");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new RecrawlPageJob(siteimproveService);
  }
}
