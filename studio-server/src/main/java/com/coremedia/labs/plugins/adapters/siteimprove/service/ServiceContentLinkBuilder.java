package com.coremedia.labs.plugins.adapters.siteimprove.service;

import com.coremedia.labs.plugins.adapters.siteimprove.SiteimproveSettings;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ServiceContentLinkBuilder implements ContentLinkBuilder{
  private static final Logger LOG = LoggerFactory.getLogger(ServiceContentLinkBuilder.class);
  private static final String PREVIEW_URL_SERVICE_URL = "internal/preview/previewurl";
  private static final String LIVE_URL_SERVICE_URL = "internal/service/url";
  private final SiteimproveSettings config;

  public ServiceContentLinkBuilder(SiteimproveSettings config) {
    this.config = config;
  }
  @Override
  @Nullable
  public String buildLink(@NonNull Boolean isPreview, @NonNull Content content){
    String baseUrl = isPreview ? config.getPreviewCaeBaseUrl() : config.getLiveCaeBaseUrl();
    if (isPreview) {
      return buildPreviewLink(baseUrl, content.getId());
    } else {
      return buildLiveLink(baseUrl, content.getId());
    }
  }



  @Nullable
  private String buildPreviewLink(String baseUrl, String id) {
    String url = baseUrl;
    if (!url.endsWith("/")) {
      url = url + "/";
    }
    String serviceUrl = url + PREVIEW_URL_SERVICE_URL;

    UriComponents uriComponents = UriComponentsBuilder.fromUriString(serviceUrl)
            .queryParam("id", IdHelper.parseContentId(id)).build();

    String link = getLink(uriComponents.toUriString());
    if (link != null && !link.startsWith("http")) {
      link = uriComponents.getScheme() + ":" + link;
    }

    return link;
  }

  @Nullable
  private String buildLiveLink(String baseUrl, String id) {
    List<String> urls = buildLiveLink(baseUrl, Collections.singletonList(id));
    if(!urls.isEmpty()) {
      return urls.get(0);
    }

    return null;
  }

  private List<String> buildLiveLink(String baseUrl, Iterable<String> ids) {
    String fullUrl = baseUrl;
    if (!fullUrl.endsWith("/")) {
      fullUrl = fullUrl + "/";
    }
    String serviceUrl = fullUrl + LIVE_URL_SERVICE_URL;
    UriComponents uriComponents = UriComponentsBuilder.fromUriString(serviceUrl).build();

    Collection<UrlServiceRequestParams> params = new ArrayList<>();
    for (String id : ids) {
      int i = IdHelper.parseContentId(id);
      params.add(new UrlServiceRequestParams(String.valueOf(i)));
    }

    Gson gson = new Gson();
    String body = gson.toJson(params);
    String result = postLinks(serviceUrl, body);

    List<UrlServiceResponseParams> urls = gson.fromJson(result, new TypeToken<List<UrlServiceResponseParams>>() {
    }.getType());

    List<String> links = new ArrayList<>();
    for (UrlServiceResponseParams url : urls) {
      if(url.getUrl() == null) {
        continue;
      }

      links.add(uriComponents.getScheme() + ":" + url.getUrl());
    }

    return links;
  }

  private String getLink(String serviceUrl) {
    try {
      URL url = new URL(serviceUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setDoInput(true);
      connection.setUseCaches(false);
      return IOUtils.toString(connection.getInputStream(), "utf-8");
    } catch (Exception e) {
      LOG.error("Failed to get links to CAE: {}", e.getMessage(), e);
    }

    return null;
  }

  private String postLinks(String serviceUrl, String body) {
    try {
      URL url = new URL(serviceUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setUseCaches(false);
      connection.setRequestProperty("Content-Type", "application/json");
      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
      writer.write(body);
      writer.flush();

      String result = IOUtils.toString(connection.getInputStream(), "utf-8");
      writer.close();
      return result;
    } catch (Exception e) {
      LOG.error("Failed to post links to CAE: {}", e.getMessage(), e);
    }

    return null;
  }

  static class UrlServiceRequestParams {
    private String id;

    private UrlServiceRequestParams(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }
  }

  static class UrlServiceResponseParams {
    private String url;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }
}
