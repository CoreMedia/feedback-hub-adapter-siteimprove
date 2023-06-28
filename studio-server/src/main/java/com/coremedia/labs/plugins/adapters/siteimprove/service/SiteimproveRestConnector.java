package com.coremedia.labs.plugins.adapters.siteimprove.service;

import com.coremedia.labs.plugins.adapters.siteimprove.SiteimproveFeedbackHubErrorCode;
import com.coremedia.labs.plugins.adapters.siteimprove.SiteimproveSettings;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


/**
 * Rest connector for Site improve
 */
class SiteimproveRestConnector {
  private static final Logger LOG = LoggerFactory.getLogger(SiteimproveRestConnector.class);
  private String protocol;
  private String host;
  private String version;
  private RestTemplate restTemplate;

  SiteimproveRestConnector(@NonNull String apiProtocol, @NonNull String apiHost, @NonNull String apiVersion, @NonNull RestTemplate restTemplate) {
    this.protocol = apiProtocol;
    this.host = apiHost;
    this.version = apiVersion;
    this.restTemplate = restTemplate;
  }

  @Nullable
  <T> T performGet(@NonNull SiteimproveSettings config, String resourcePath, Class<T> responseType, MultiValueMap<String, String> queryParams) {
    return perform(config, resourcePath, responseType, HttpMethod.GET, queryParams);
  }

  @Nullable
  public <T> T performGet(@NonNull SiteimproveSettings config, Class<T> responseType, MultiValueMap<String, String> queryParams) {
    return perform(config, null, responseType, HttpMethod.GET, queryParams);
  }

  @Nullable
  public <T> T performPost(@NonNull SiteimproveSettings config, String resourcePath, Class<T> responseType, MultiValueMap<String, String> queryParams) {
    return perform(config, resourcePath, responseType, HttpMethod.POST, queryParams);
  }

  @Nullable
  public <T> T performPost(@NonNull SiteimproveSettings config, String resourcePath, Class<T> responseType, MultiValueMap<String, String> queryParams, String body) {
    return perform(config, resourcePath, responseType, HttpMethod.POST, queryParams, body);
  }

  @Nullable
  private <T> T perform(SiteimproveSettings config, String resourcePath, Class<T> responseType, HttpMethod method, MultiValueMap<String, String> queryParams) {
    return perform(config, resourcePath, responseType, method, queryParams, null);
  }

  @Nullable
  private <T> T perform(SiteimproveSettings config, String resourcePath, Class<T> responseType, HttpMethod method, MultiValueMap<String, String> queryParams, String body) {
    String url = getUrl(resourcePath, queryParams);
    try {
      LOG.info("Siteimprove request: {}", url);

      HttpEntity<String> requestEntity = null;
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Basic " + resolveToken(config));
      headers.add("X-Siteimprove-Api-Caller", "CoreMedia");

      if (body == null) {
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        requestEntity = new HttpEntity<>(headers);
      } else {
        headers.add("Content-Type", "text/plain; charset=utf8");
        requestEntity = new HttpEntity<>(body, headers);
      }

      ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
      HttpStatus statusCode = responseEntity.getStatusCode();
      if (!statusCode.is2xxSuccessful()) {
        LOG.error("Failed to execute Siteimprove REST call {}: {}", url, statusCode.getReasonPhrase());
      }
      return responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      String responseBodyAsString = e.getResponseBodyAsString();
      String msg = "Failed to execute Siteimprove REST call " + url + ": " + responseBodyAsString;
      LOG.error(msg);
      throw new FeedbackHubException(msg, e, SiteimproveFeedbackHubErrorCode.REST_ERROR, Arrays.asList(e.getStatusCode().toString()));
    }
  }

  private String getUrl(String resourcePath, MultiValueMap<String, String> queryParams) {
    UriComponentsBuilder uriComponentsBuilder;
    //resourcePath can be absolute when it is from extracted from a json of a previous request.
    if (resourcePath.startsWith(protocol)) {
      uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(resourcePath);
    } else {
      uriComponentsBuilder = UriComponentsBuilder.newInstance()
              .scheme(protocol)
              .host(host)
              .path(version)
              .path(resourcePath);
    }

    if (queryParams != null) {
      uriComponentsBuilder.queryParams(queryParams);
    }

    UriComponents uriComponents = uriComponentsBuilder.build();
    return uriComponents.toString();
  }

  @NonNull
  private String resolveToken(@NonNull SiteimproveSettings config) {
    String userName = config.getEmail();
    String apiKey = config.getApiKey();
    return Base64Utils.encodeToString((userName + ":" + apiKey).getBytes(StandardCharsets.UTF_8));
  }

}

