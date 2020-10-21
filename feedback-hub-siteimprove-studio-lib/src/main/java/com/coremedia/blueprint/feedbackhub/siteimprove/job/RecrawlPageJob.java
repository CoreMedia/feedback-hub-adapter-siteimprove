package com.coremedia.blueprint.feedbackhub.siteimprove.job;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageCheckResultDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.PageCheckStatusDocument;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cap.multisite.impl.SitesServiceImpl;
import com.coremedia.feedbackhub.Binding;
import com.coremedia.feedbackhub.BindingsService;
import com.coremedia.rest.cap.jobs.GenericJobErrorCode;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobContext;
import com.coremedia.rest.cap.jobs.JobExecutionException;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RecrawlPageJob implements Job {
  private static final Logger LOG = LoggerFactory.getLogger(RecrawlPageJob.class);

  private final SiteimproveService siteimproveService;
  private final BindingsService bindingsService;
  private final SitesService sitesService;

  private Boolean preview = false;
  private Content content;
  private String pageId;
  private Boolean checkStatusOnly = false;
  private ScheduledFuture<?> scheduledFuture;

  public RecrawlPageJob(SiteimproveService siteimproveService, BindingsService bindingsService, SitesService sitesService) {
    this.siteimproveService = siteimproveService;
    this.bindingsService = bindingsService;
    this.sitesService = sitesService;
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

  @JsonProperty("checkStatusOnly")
  public void setCheckStatusOnly(Boolean checkStatusOnly) {
    this.checkStatusOnly = checkStatusOnly;
  }

  @Nullable
  @Override
  public Object call(@NonNull JobContext jobContext) throws JobExecutionException {
    SiteimproveSettings config = getConfig(content);
    String siteId = preview ? config.getSiteimprovePreviewSiteId() : config.getSiteimproveLiveSiteId();
    try {
      if (!checkStatusOnly) {
        PageCheckResultDocument pageCheckResultDocument = siteimproveService.pageCheck(config, preview, content, pageId);
        if (!pageCheckResultDocument.getSuccess()) {
          throw new JobExecutionException(GenericJobErrorCode.FAILED);
        }
      }

      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
        PageCheckStatusDocument pageCheckStatus = siteimproveService.getPageCheckStatus(config, preview, content, pageId);
        if (!pageCheckStatus.getCheckingNow()) {
          cancel();
        }
      }, 1, 1, TimeUnit.SECONDS);

      //wait until the the future is canceled.
      try {
        scheduledFuture.get();
      } catch (CancellationException ignore) {
        //expected
      }
      return "page recrawl completed";
    } catch (Exception e) {
      LOG.error("Cannot trigger page recrawl for {} / {}", siteId, pageId, e);
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }

  private void cancel() {
    if (scheduledFuture != null) {
      scheduledFuture.cancel(false);
    }
  }
  //Use the injected bindingsService to access the Siteimprove settings
  //TODO: make it better.
  private SiteimproveSettings getConfig(Content content) {
    Site site = ((SitesServiceImpl) sitesService).getSiteFor(content);
    Map<Site, Collection<Binding>> siteLocalBindings = bindingsService.getSiteLocalBindings();
    SiteimproveSettings config = getConfig(siteLocalBindings.get(site));

    if (config != null) {
      return config;
    }

    return getConfig(bindingsService.getGlobalBindings());
  }

  private SiteimproveSettings getConfig(Collection<Binding> bindings) {
    if (bindings != null) {
      Optional<Binding> siteimprove = bindings.stream()
              .filter(binding -> binding.getFactoryId().equals("siteimprove"))
              .findFirst();
      if (siteimprove.isPresent()) {
        return siteimprove.get().getSettings(SiteimproveSettings.class);
      }
    }
    return null;
  }
}
