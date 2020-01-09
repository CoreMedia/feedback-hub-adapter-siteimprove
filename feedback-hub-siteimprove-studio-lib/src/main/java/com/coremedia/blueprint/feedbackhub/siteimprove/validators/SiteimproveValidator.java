package com.coremedia.blueprint.feedbackhub.siteimprove.validators;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveFeedbackItem;
import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckResultDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckStatusDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentIssue;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Highlight;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Match;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.Offset;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.Binding;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.rest.cap.validation.ContentTypeValidatorBase;
import com.coremedia.rest.validation.Issues;
import com.coremedia.rest.validation.Severity;
import com.coremedia.xml.MarkupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class SiteimproveValidator extends ContentTypeValidatorBase {
  private static final Logger LOG = LoggerFactory.getLogger(SiteimproveValidator.class);

  private FeedbackService feedbackService;
  private SitesService sitesService;
  private SiteimproveService siteimproveService;
  private String propertyName;


  @Override
  public void validate(Content content, Issues issues) {
    SiteimproveSettings settings = findSettings(content);
    if (settings == null) {
      LOG.warn("No matching Siteimprove settings found for {}", content.getPath());
      return;
    }

    LOG.info("Validating {}", content.getPath());
    String text = MarkupUtil.asPlainText(content.getMarkup(propertyName));

    text = text.replaceAll("\\R", " ").trim();
    System.out.println(text);
    ContentCheckStatusDocument contentCheckStatusDocument = siteimproveService.contentCheck(settings, text);
    String contentId = contentCheckStatusDocument.getContentId();

    ContentCheckResultDocument result = siteimproveService.getContentCheckResult(settings, contentId);
    int count = 0;
    while (result == null) {
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        //ignore
      }
      LOG.info("Waiting on Siteimprove content issues for {}...", contentId);
      result = siteimproveService.getContentCheckResult(settings, contentId);
      count++;
      if (count == 15) {
        break;
      }
    }

    //We wait additional 2 seconds here to let Siteimprove give time to update additional data on their site.
    //Otherwise it may happen that requesting details are wrong since the data has not been updates completely yet.
    try {
      Thread.sleep(2000);
      result = siteimproveService.getContentCheckResult(settings, contentId);
    } catch (Exception e) {
      //ignore
    }

    List<ContentIssue> results = new ArrayList<>();
    if (result != null && result.getQualityAssurance() != null) {
      results.addAll(result.getQualityAssurance());
    }
    LOG.info("Finished Siteimprove analysis, found {} issues.", results.size());
    for (ContentIssue contentIssue : results) {
      String value = "";
      //check if there are text issues which are described by matches
      if (!contentIssue.getMatches().isEmpty()) {
        LOG.info("Found {} issues of property {} for {}", contentIssue.getMatches().size(), this.propertyName, contentIssue.getName());

        List<Match> matches = contentIssue.getMatches();
        for (Match match : matches) {
          if (match.getOccurrence() != null && !match.getOccurrence().getOccurrences().isEmpty()) {
            List<Highlight> highlights = match.getOccurrence().getOccurrences().get(0).getHighlights();
            if (highlights != null && !highlights.isEmpty()) {
              Highlight highlight = highlights.get(0);
              Offset offset = highlight.getOffset();
//              value = extractText(text, offset.getStart(), offset.getLength());
              value = text.substring(offset.getStart(), offset.getStart() + offset.getLength());
              Object[] params = {value};
              issues.addIssue(Severity.WARN, propertyName, contentIssue.getName(), params);
            }
          }
          else {
            LOG.info("Issue {} does not have any matching information", content.getName());
          }
        }
      }
    }

    if (result != null && result.getSeo() != null && !result.getSeo().isEmpty()) {
      List<ContentIssue> seo = result.getSeo();
      LOG.info("Sitemprove validator found also {} SEO issues that are ignored for the editor: {}", seo.size(), seo.stream().map(ContentIssue::getName).collect(Collectors.toList()));
    }

    if (result != null && result.getPolicy() != null && !result.getPolicy().isEmpty()) {
      List<ContentIssue> policy = result.getPolicy();
      LOG.info("Sitemprove validator found also {} policy issues that are ignored for the editor: {}", policy.size(), policy.stream().map(ContentIssue::getName).collect(Collectors.toList()));
    }
  }

  /**
   * We do not know the exact position of the text since we converted the markup XML to a string.
   * This may have let to offsets matches that do not fit to the Siteimprove.
   * We soften this behaviour by appending and prepending some more text to give the issue more context.
   *
   * @param text   the text that has been analyzed
   * @param start  the Siteimprove issue start index
   * @param offset the Siteimprove issue offset index
   * @return the issued text with some additional text
   */
  private String extractText(String text, int start, int offset) {
    String result = text.substring(start, start + offset);

    //append more text at the end
    int endOffset = offset;
    while (!result.endsWith(".") && !result.endsWith(",") && !result.endsWith(" ")) {
      endOffset++;
      if (text.length() > start + endOffset) {
        result = text.substring(start, start + endOffset);
      }
      else {
        break;
      }
    }

    //prepend more text at start
    int newStart = start;
    while (!result.startsWith(" ")) {
      newStart--;
      if (newStart >= 0) {
        result = text.substring(newStart, start + endOffset);
      }
      else {
        break;
      }
    }

    return result.trim();
  }

  /**
   * Tries to find the matching FeedbackHubSettings of Siteimprove for the given content.
   *
   * @param content the content to find the settings for
   */
  private SiteimproveSettings findSettings(Content content) {
    Site contentSite = sitesService.getContentSiteAspect(content).getSite();
    if (contentSite == null) {
      return null;
    }

    Map<Site, Collection<Binding>> siteLocalBindings = feedbackService.getSiteLocalBindings();
    Set<Map.Entry<Site, Collection<Binding>>> entries = siteLocalBindings.entrySet();
    for (Map.Entry<Site, Collection<Binding>> entry : entries) {
      Collection<Binding> value = entry.getValue();
      for (Binding binding : value) {
        if (binding.getFactoryId().equals(SiteimproveFeedbackItem.TYPE)) {
          Site site = entry.getKey();
          if (contentSite.equals(site)) {
            return binding.getSettings(SiteimproveSettings.class);
          }
        }
      }
    }

    //so we did not find a site specific configuration, let's try the global ones anyway
    Collection<Binding> globalBindings = feedbackService.getGlobalBindings();
    for (Binding globalBinding : globalBindings) {
      if (globalBinding.getFactoryId().equals(SiteimproveFeedbackItem.TYPE)) {
        SiteimproveSettings settings = globalBinding.getSettings(SiteimproveSettings.class);
        String coreMediaSiteId = settings.getCoreMediaSiteId();
        if(coreMediaSiteId == null) {
          LOG.warn("If you use global Siteimprove Feedback Hub settings you have to configure the CoreMedia site id that should be used for the corresponding Siteimprove site id.");
          break;
        }

        if (coreMediaSiteId.equals(contentSite.getId())) {
          LOG.warn("Found a global Siteimprove configuration for content of site {}, this should be moved to the site specific settings folder.", contentSite.getName());
          return settings;
        }
      }
    }

    return null;
  }

  public void setSiteimproveService(SiteimproveService siteimproveService) {
    this.siteimproveService = siteimproveService;
  }

  public void setMarkupProperty(String propertyName) {
    this.propertyName = propertyName;
  }

  public void setFeedbackService(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  public void setSitesService(SitesService sitesService) {
    this.sitesService = sitesService;
  }
}
