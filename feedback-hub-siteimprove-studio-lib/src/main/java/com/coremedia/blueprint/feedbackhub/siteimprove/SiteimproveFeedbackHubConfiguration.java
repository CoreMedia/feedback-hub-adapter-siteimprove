package com.coremedia.blueprint.feedbackhub.siteimprove;

import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveServiceConfiguration;
import com.coremedia.blueprint.feedbackhub.siteimprove.validators.SiteimproveValidator;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.feedbackhub.provider.ContentFeedbackProviderFactory;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SiteimproveServiceConfiguration.class)
public class SiteimproveFeedbackHubConfiguration {
  @Bean
  public ContentFeedbackProviderFactory siteimproveContentFeedbackProviderFactory(@NonNull SiteimproveService siteimproveService) {
    return new SiteimproveContentFeedbackProviderFactory(siteimproveService);
  }

  @Bean
  SiteimproveValidator siteimproveValidator(@NonNull CapConnection connection,
                                            @NonNull SiteimproveService siteimproveService) {
    SiteimproveValidator validator = new SiteimproveValidator();
    validator.setConnection(connection);
    validator.setSiteimproveService(siteimproveService);
    validator.setValidatingSubtypes(true);
    validator.setContentType("CMTeasable");
    return validator;
  }
}
