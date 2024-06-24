package com.coremedia.labs.plugins.adapters.siteimprove.service;

import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:/com/coremedia/labs/plugins/adapters/siteimprove/siteimprove.properties")
@Configuration
public class SiteimproveServiceConfiguration {

  @Value(value = "${siteimprove.api.protocol:https}")
  private String apiProtocol;

  @Value(value = "${siteimprove.api.host:api.siteimprove.com}")
  private String apiHost;

  @Value(value = "${siteimprove.api.version:v2}")
  private String apiVersion;

  @Bean
  public SiteimproveService siteImproveService(SiteimproveRestConnector siteImproveRestConnector) {
    return new SiteimproveServiceImpl(siteImproveRestConnector);
  }

  @Bean
  public SiteimproveRestConnector siteimproveRestConnector(@Qualifier("siteimproveRestTemplate") RestTemplate restTemplate) {
    return new SiteimproveRestConnector(apiProtocol, apiHost, apiVersion, restTemplate);
  }

  @Bean(name = "siteimproveRestTemplate")
  public RestTemplate siteimproveRestTemplate() {
    HttpHost httpHost = new HttpHost(apiProtocol,apiHost + "/" + apiVersion,-1);
    return new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost));
  }
}
