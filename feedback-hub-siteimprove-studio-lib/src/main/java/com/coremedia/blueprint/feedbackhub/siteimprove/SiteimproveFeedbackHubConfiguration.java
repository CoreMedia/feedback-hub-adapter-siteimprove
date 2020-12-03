package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.job.RecrawlPageJobFactory;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveServiceConfiguration;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beansforplugins.plugin.CommonBeansForPluginsConfiguration;
import com.coremedia.feedbackhub.BindingsService;
import com.coremedia.feedbackhub.beansforplugins.FeedbackHubBeansForPluginsConfiguration;
import com.coremedia.feedbackhub.provider.ContentFeedbackProviderFactory;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SiteimproveServiceConfiguration.class,
        FeedbackHubBeansForPluginsConfiguration.class,
        CommonBeansForPluginsConfiguration.class})
public class SiteimproveFeedbackHubConfiguration {
  @Bean
  public ContentFeedbackProviderFactory<?> siteimproveContentFeedbackProviderFactory(@NonNull SiteimproveService siteimproveService) {
    return new SiteimproveContentFeedbackProviderFactory(siteimproveService);
  }

  @Bean
  public RecrawlPageJobFactory recrawlPageJobFactory(@NonNull SiteimproveService siteimproveService,
                                                     @NonNull BindingsService bindingsService,
                                                     @NonNull SitesService sitesService) {
    return new RecrawlPageJobFactory(siteimproveService, bindingsService, sitesService);
  }

/* Out of scope

  @Bean
  SiteimproveValidator siteimproveValidator(@NonNull CapConnection connection,
                                            @NonNull SiteimproveService siteimproveService,
                                            @NonNull SitesService sitesService,
                                            @NonNull BindingsService bindingsService) {
    SiteimproveValidator validator = new SiteimproveValidator();
    validator.setConnection(connection);
    validator.setSiteimproveService(siteimproveService);
    validator.setBindingsService(bindingsService);
    validator.setValidatingSubtypes(true);
    validator.setSitesService(sitesService);
    validator.setContentType("CMTeasable");
    validator.setMarkupProperty("detailText");
    return validator;
  }
*/
}
