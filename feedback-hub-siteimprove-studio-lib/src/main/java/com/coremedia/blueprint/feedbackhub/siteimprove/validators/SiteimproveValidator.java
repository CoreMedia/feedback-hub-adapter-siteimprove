package com.coremedia.blueprint.feedbackhub.siteimprove.validators;

import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveFeedbackItem;
import com.coremedia.blueprint.feedbackhub.siteimprove.SiteimproveSettings;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.SiteimproveService;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckIssuesDocument;
import com.coremedia.blueprint.feedbackhub.siteimprove.service.documents.ContentCheckResultDocument;
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
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
    String text = extractText(content);
    if(text.length() == 0 ||true ) {
      return;
    }

    List<ContentIssue> results = runSiteimproveAnalysis(content, text);
    List<IssueHighlight> highlights = filterHighlights(content, results);

    //sort issues by starting position
    highlights.sort(Comparator.comparingInt(o -> o.hightlight.getOffset().getStart()));

    for (IssueHighlight h : highlights) {
      Offset offset = h.hightlight.getOffset();
      String value = text.substring(offset.getStart(), offset.getStart() + offset.getLength());
      Object[] params = {value};
      issues.addIssue(Severity.WARN, propertyName, h.issue.getName(), params);
    }
  }

  /**
   * For markup we are only interested in analysis results that have a highlight information.
   * Only this allows us to point the editor to the text passage that has issues.
   *
   * @param content the content to analyze
   * @param issues  the list of issues found by Siteimprove
   */
  private List<IssueHighlight> filterHighlights(Content content, List<ContentIssue> issues) {
    List<IssueHighlight> result = new ArrayList<>();
    for (ContentIssue contentIssue : issues) {
      if (!contentIssue.getMatches().isEmpty()) {
        List<Match> matches = contentIssue.getMatches();
        for (Match match : matches) {
          if (match.getOccurrence() != null && !match.getOccurrence().getOccurrences().isEmpty()) {
            List<Highlight> highlights = match.getOccurrence().getOccurrences().get(0).getHighlights();
            if (highlights != null && !highlights.isEmpty()) {
              Highlight highlight = highlights.get(0);
              result.add(new IssueHighlight(contentIssue, highlight));
            }
          }
          else {
            LOG.info("Issue {} does not have any matching information", content.getName());
          }
        }
      }
    }
    return result;
  }

  /**
   * Converts the markup property value to plain text
   * and replaces all newlines
   *
   * @param content the content to extract the markup from
   * @return plain text to be send to Siteimprove
   */
  private String extractText(Content content) {
    String text = MarkupUtil.asPlainText(content.getMarkup(propertyName));
    if(text == null) {
      text = "";
    }
    return text.replaceAll("\\R", " ").trim();
  }

  /**
   * Executes the Siteimprove content analysis for the configured property of the given document
   *
   * @param text the text to analyze
   * @return the content issues for the given content
   */
  @NonNull
  private List<ContentIssue> runSiteimproveAnalysis(@NonNull Content content, @NonNull String text) {
    List<ContentIssue> results = new ArrayList<>();
    SiteimproveSettings settings = findSettings(content);
    if (settings == null) {
      LOG.warn("No matching Siteimprove settings found for {}", content.getPath());
      return results;
    }

    if (text.trim().length() == 0) {
      return results;
    }

    ContentCheckResultDocument contentCheckResultDocument = siteimproveService.contentCheck(settings, text);
    String contentId = contentCheckResultDocument.getContentId();
    ContentCheckIssuesDocument result = siteimproveService.getContentCheckIssues(settings, contentId);

    int count = 0;
    while (result == null) {
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        //ignore
      }
      LOG.info("Waiting on Siteimprove content issues for {}...", contentId);
      result = siteimproveService.getContentCheckIssues(settings, contentId);
      count++;
      if (count == 15) {
        break;
      }
    }

    //We wait additional 2 seconds here to let Siteimprove give time to update additional data on their site.
    //Otherwise it may happen that requesting details are wrong since the data has not been updates completely yet.
    try {
//      Thread.sleep(2000);
//      result = siteimproveService.getContentCheckIssues(settings, contentId);
    } catch (Exception e) {
      //ignore
    }

    if (result != null && result.getQualityAssurance() != null) {
      results.addAll(result.getQualityAssurance());
      LOG.info("Finished Siteimprove analysis, found {} issues.", results.size());
    }

    if (result != null && result.getSeo() != null && !result.getSeo().isEmpty()) {
      List<ContentIssue> seo = result.getSeo();
      LOG.info("Sitemprove validator found also {} SEO issues that are ignored for the editor: {}", seo.size(), seo.stream().map(ContentIssue::getName).collect(Collectors.toList()));
    }

    if (result != null && result.getPolicy() != null && !result.getPolicy().isEmpty()) {
      List<ContentIssue> policy = result.getPolicy();
      LOG.info("Sitemprove validator found also {} policy issues that are ignored for the editor: {}", policy.size(), policy.stream().map(ContentIssue::getName).collect(Collectors.toList()));
    }
    return results;
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
        if (coreMediaSiteId == null) {
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

  /**
   * Just a helper to store the highlight's issue
   */
  class IssueHighlight {
    ContentIssue issue;
    Highlight hightlight;

    IssueHighlight(ContentIssue issue, Highlight hightlight) {
      this.issue = issue;
      this.hightlight = hightlight;
    }

  }
}
