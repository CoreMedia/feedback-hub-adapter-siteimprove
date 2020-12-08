package com.coremedia.blueprint.feedbackhub.siteimprove.service.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteimproveWebappDocument {

  @JsonProperty("webapp")
  private WebappDocument webapp;

  @JsonProperty("page_report")
  private WebappDocument pageReport;

  public WebappDocument getWebapp() {
    return webapp;
  }

  public void setWebapp(WebappDocument webapp) {
    this.webapp = webapp;
  }

  public WebappDocument getPageReport() {
    return pageReport;
  }

  public void setPageReport(WebappDocument pageReport) {
    this.pageReport = pageReport;
  }
}
