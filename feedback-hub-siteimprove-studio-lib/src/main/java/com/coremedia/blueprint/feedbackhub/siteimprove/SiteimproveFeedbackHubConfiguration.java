package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveServiceConfiguration;
import com.coremedia.blueprint.feedbackhub.siteimprove.validators.SiteimproveValidator;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.feedbackhub.provider.ContentFeedbackProviderFactory;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(SiteimproveServiceConfiguration.class)
@ImportResource(
        value = {
                "classpath:/com/coremedia/cap/multisite/multisite-services.xml"
        },
        reader = ResourceAwareXmlBeanDefinitionReader.class
)
public class SiteimproveFeedbackHubConfiguration {
  @Bean
  public ContentFeedbackProviderFactory siteimproveContentFeedbackProviderFactory(@NonNull SiteimproveService siteimproveService) {
    return new SiteimproveContentFeedbackProviderFactory(siteimproveService);
  }

  @Bean
  SiteimproveValidator siteimproveValidator(@NonNull CapConnection connection,
                                            @NonNull SiteimproveService siteimproveService,
                                            @NonNull SitesService sitesService,
                                            @NonNull FeedbackService feedbackService) {
    SiteimproveValidator validator = new SiteimproveValidator();
    validator.setConnection(connection);
    validator.setSiteimproveService(siteimproveService);
    validator.setFeedbackService(feedbackService);
    validator.setValidatingSubtypes(true);
    validator.setSitesService(sitesService);
    validator.setContentType("CMTeasable");
    validator.setMarkupProperty("detailText");
    return validator;
  }
}
