package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.job.RecrawlPageJobFactory;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveServiceConfiguration;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beansforplugins.CommonBeansForPluginsConfiguration;
import com.coremedia.feedbackhub.provider.FeedbackProviderFactory;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SiteimproveServiceConfiguration.class, CommonBeansForPluginsConfiguration.class})
public class SiteimproveFeedbackHubConfiguration {
  @Bean
  public FeedbackProviderFactory siteimproveContentFeedbackProviderFactory(@NonNull SiteimproveService siteimproveService) {
    return new SiteimproveContentFeedbackProviderFactory(siteimproveService);
  }

  @Bean
  public RecrawlPageJobFactory recrawlPageJobFactory(@NonNull SiteimproveService siteimproveService, @NonNull SitesService sitesService) {
    return new RecrawlPageJobFactory(siteimproveService, sitesService);
  }
}
