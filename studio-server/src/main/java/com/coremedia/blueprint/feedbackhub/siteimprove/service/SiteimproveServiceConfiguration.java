package com.coremedia.blueprint.feedbackhub.siteimprove.service;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:/com/coremedia/blueprint/feedbackhub/siteimprove/siteimprove.properties")
@Configuration
public class SiteimproveServiceConfiguration {
  @Value(value = "${siteimprove.api.host}")
  private String apiHost;

  @Bean
  public SiteimproveService siteImproveService(SiteimproveRestConnector siteImproveRestConnector) {
    return new SiteimproveServiceImpl(siteImproveRestConnector);
  }

  @Bean
  public SiteimproveRestConnector siteimproveRestConnector(@Qualifier("siteimproveRestTemplate") RestTemplate restTemplate) {
    return new SiteimproveRestConnector(restTemplate);
  }

  @Bean(name = "siteimproveRestTemplate")
  public RestTemplate siteimproveRestTemplate() {
    HttpHost httpHost = new HttpHost(apiHost, -1, "https");
    return new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost));
  }
}
