package com.coremedia.labs.plugins.adapters.siteimprove.service;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;


class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory {

  private HttpHost host;

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
