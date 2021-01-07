package com.coremedia.blueprint.feedbackhub.siteimprove.job;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class RecrawlPageJobFactory implements JobFactory {

  private SiteimproveService siteimproveService;
  private SitesService sitesService;

  public RecrawlPageJobFactory(SiteimproveService siteimproveService, SitesService sitesService) {
    this.siteimproveService = siteimproveService;
    this.sitesService = sitesService;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("recrawlPage");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new RecrawlPageJob(siteimproveService,  sitesService);
  }
}
