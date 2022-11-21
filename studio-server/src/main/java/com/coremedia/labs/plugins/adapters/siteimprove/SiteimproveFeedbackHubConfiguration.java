package com.coremedia.labs.plugins.adapters.siteimprove;

import com.coremedia.labs.plugins.adapters.siteimprove.job.RecrawlPageJobFactory;
import com.coremedia.labs.plugins.adapters.siteimprove.service.SiteimproveService;
import com.coremedia.labs.plugins.adapters.siteimprove.service.SiteimproveServiceConfiguration;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.FeedbackHubConfiguration;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.feedbackhub.provider.FeedbackProviderFactory;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({SiteimproveServiceConfiguration.class, FeedbackHubConfiguration.class})
@ImportResource(
        value = {
                "classpath:/com/coremedia/cap/multisite/multisite-services.xml"
        },
        reader = ResourceAwareXmlBeanDefinitionReader.class
)
public class SiteimproveFeedbackHubConfiguration {
  @Bean
  public FeedbackProviderFactory siteimproveContentFeedbackProviderFactory(@NonNull SiteimproveService siteimproveService) {
    return new SiteimproveContentFeedbackProviderFactory(siteimproveService);
  }

  @Bean
  public RecrawlPageJobFactory recrawlPageJobFactory(@NonNull SiteimproveService siteimproveService, @NonNull FeedbackService feedbackService, @NonNull SitesService sitesService) {
    return new RecrawlPageJobFactory(siteimproveService, feedbackService, sitesService);
  }
}
