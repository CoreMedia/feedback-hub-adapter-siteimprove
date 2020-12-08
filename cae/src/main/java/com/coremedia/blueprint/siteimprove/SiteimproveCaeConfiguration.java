package com.coremedia.blueprint.siteimprove;

import com.coremedia.blueprint.siteimprove.view.SiteImproveMetadataViewHookEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteimproveCaeConfiguration {
  @Bean
  public SiteImproveMetadataViewHookEventListener siteImproveMetadataViewHookEventListener() {
    return new SiteImproveMetadataViewHookEventListener();
  }
}
