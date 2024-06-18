package com.coremedia.labs.plugins.adapters.siteimprove.service;


import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.AuthScheme;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;


class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory {

  private final HttpHost host;

  HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host) {
    this.host = host;
  }

  protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
    return createHttpContext();
  }

  private HttpContext createHttpContext() {
    AuthCache authCache = new BasicAuthCache();

    AuthScheme basicAuth = new BasicScheme();
    authCache.put(host, basicAuth);

    HttpContext localcontext = new BasicHttpContext();
    localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
    return localcontext;
  }

}
