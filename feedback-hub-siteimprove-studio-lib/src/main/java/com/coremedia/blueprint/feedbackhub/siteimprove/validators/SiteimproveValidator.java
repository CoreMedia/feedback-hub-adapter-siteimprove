package com.coremedia.blueprint.feedbackhub.siteimprove.validators;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettingsImpl;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckResultDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentIssue;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Highlight;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Match;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Offset;
import com.coremedia.cap.content.Content;
import com.coremedia.rest.cap.validation.ContentTypeValidatorBase;
import com.coremedia.rest.validation.Issues;
import com.coremedia.rest.validation.Severity;
import com.coremedia.xml.MarkupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SiteimproveValidator extends ContentTypeValidatorBase {
  private static final Logger LOG = LoggerFactory.getLogger(SiteimproveValidator.class);

  private SiteimproveService siteimproveService;

  @Override
  public void validate(Content content, Issues issues) {
    SiteimproveSettingsImpl settings = new SiteimproveSettingsImpl();

    //TODO validation is currently blocking + all the other stuff
    if(settings != null) {
      return;
    }


    LOG.info("Validating " + content.getPath());
    String title = content.getString("title");
    String text = MarkupUtil.asPlainText(content.getMarkup("detailText"));

    text = text.replaceAll("\\\\n", "");
    text = text.replaceAll("\\\\r", "");
    //String text = content.getMarkup("detailText").asXml();
    LOG.info("Pushing title value '" + text + "'");
    ContentCheckStatusDocument contentCheckStatusDocument = siteimproveService.contentCheck(settings, text);
    String contentId = contentCheckStatusDocument.getContentId();

    ContentCheckResultDocument result = siteimproveService.getContentCheckResult(settings, contentId);
    int count = 0;
    while(result == null) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      LOG.info("Waiting on Siteimprove content issues for " + contentId);
      result = siteimproveService.getContentCheckResult(settings, contentId);
      count++;
      if(count == 15) {
        break;
      }
    }

    try {
      Thread.sleep(2000);
      result = siteimproveService.getContentCheckResult(settings, contentId);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    List<ContentIssue> results = new ArrayList<>();
    //results.addAll(result.getSeo());
    results.addAll(result.getQualityAssurance());
    LOG.info("Finished Siteimprove analysis, found " + results.size() + " issues.");
    for (ContentIssue contentIssue : results) {
      String value = "";
      if(!contentIssue.getMatches().isEmpty()) {

        List<Match> matches = contentIssue.getMatches();
        for (Match match : matches) {
          if(match.getOccurrence() != null && !match.getOccurrence().getOccurrences().isEmpty()) {
            List<Highlight> highlights = match.getOccurrence().getOccurrences().get(0).getHighlights();
            if(highlights != null && !highlights.isEmpty()) {
              Highlight highlight = highlights.get(0);
              Offset offset = highlight.getOffset();
              value = text.substring(offset.getStart(), offset.getStart() + offset.getLength());
              Object[] params  ={value};
              issues.addIssue(Severity.WARN, "detailText", contentIssue.getName(), params);
            }
          }
        }
      }
    }
  }

  public void setSiteimproveService(SiteimproveService siteimproveService) {
    this.siteimproveService = siteimproveService;
  }
}
