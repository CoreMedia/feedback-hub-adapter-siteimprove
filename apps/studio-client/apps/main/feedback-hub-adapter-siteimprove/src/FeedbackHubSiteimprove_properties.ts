import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";

/**
 * Interface values for ResourceBundle "FeedbackHubSiteimprove".
 * @see FeedbackHubSiteimprove_properties#INSTANCE
 */
interface FeedbackHubSiteimprove_properties {

  siteimprove_iconCls: string;
  siteimprove_scoreDetails: string;
  siteimprove_scoreDifferences: string;
  siteimprove_overallScore: string;
  siteimprove_digitalCertaintyIndex: string;
  siteimprove_preview_tab_title: string;
  siteimprove_comparison_tab_title: string;
  siteimprove_comparison_tab_tooltip: string;
/**
 *Title for siteimprove feedback item
 */
  siteimprove_points: string;
  siteimprove_title: string;
  siteimprove_content_quality: string;
  siteimprove_a11y: string;
  siteimprove_seo_issues: string;
  siteimprove_seo: string;
  siteimprove_quality: string;
  siteimprove_issue_link: string;
/**
 *Sites
 */
  siteimprove_preview_site: string;
  siteimprove_preview_site_link: string;
  siteimprove_live_site: string;
  siteimprove_live_site_link: string;
  siteimprove_gain_score: string;
  siteimprove_lose_score: string;
  siteimprove_last_crawl: string;
  siteimprove_unknown: string;
  siteimprove_issues_help: string;
  siteimprove_issue: string;
  siteimprove_category: string;
  siteimprove_points_to_gain: string;
/**
 *Issue Filter
 */
  siteimprove_filter: string;
  siteimprove_issues_all: string;
  siteimprove_issues_a11y: string;
  siteimprove_issues_a11y_count: string;
  siteimprove_issue_category_accessibility: string;
  siteimprove_issues_qa: string;
  siteimprove_issues_qa_count: string;
  siteimprove_issue_category_content: string;
  siteimprove_issues_seo: string;
  siteimprove_issue_category_seov2: string;
  siteimprove_issues_seo_count: string;
  siteimprove_issues_seo_content: string;
  siteimprove_issues_seo_technical: string;
/**
 *Siteimprove Error Messages
 */
  siteimprove_error_EMAIL_NOT_SET: string;
  siteimprove_error_API_KEY_NOT_SET: string;
  siteimprove_error_REST_ERROR: string;
  siteimprove_error_NO_CONTENT_METATAG_FOUND: string;
  siteimprove_error_NO_CONTENT_METATAG_WITH_CONTENT_ID_FOUND: string;
  siteimprove_error_NO_CONTENT_FOUND: string;
  siteimprove_error_live_NO_CONTENT_METATAG_FOUND: string;
  siteimprove_error_live_NO_CONTENT_METATAG_WITH_CONTENT_ID_FOUND: string;
  siteimprove_error_live_NO_CONTENT_FOUND: string;
  siteimprove_error_live_NOT_PUBLISHED: string;
  recrawl_preview_page_btn_text: string;
  recrawl_preview_page_btn_tooltip: string;
  recrawl_live_page_btn_text: string;
  recrawl_live_page_btn_tooltip: string;
  siteimprove_show_more: string;
}

/**
 * Singleton for the current user Locale's instance of ResourceBundle "FeedbackHubSiteimprove".
 * @see FeedbackHubSiteimprove_properties
 */
const FeedbackHubSiteimprove_properties: FeedbackHubSiteimprove_properties = {
  siteimprove_iconCls: CoreIcons_properties.siteimprove,
  siteimprove_scoreDetails: "Score Details",
  siteimprove_scoreDifferences: "Score Differences",
  siteimprove_overallScore: "Overall Score",
  siteimprove_digitalCertaintyIndex: "Digital Certainty Index",
  siteimprove_preview_tab_title: "Preview",
  siteimprove_comparison_tab_title: "Comparison",
  siteimprove_comparison_tab_tooltip: "Comparison between Preview and Live site",
  siteimprove_points: "points",
  siteimprove_title: "Siteimprove",
  siteimprove_content_quality: "Content Quality",
  siteimprove_a11y: "Accessibility",
  siteimprove_seo_issues: "SEO Score",
  siteimprove_seo: "SEO",
  siteimprove_quality: "Quality Assurance",
  siteimprove_issue_link: "Click here to see all issues in Siteimprove",
  siteimprove_preview_site: "Preview",
  siteimprove_preview_site_link: "Open Preview",
  siteimprove_live_site: "Live Site",
  siteimprove_live_site_link: "Open Live Site",
  siteimprove_gain_score: "You will <b>gain {0} points<\/b> with the current changes in the preview.",
  siteimprove_lose_score: "You will <b>lose {0} points<\/b> with the current changes in the preview.",
  siteimprove_last_crawl: "Last checked:",
  siteimprove_unknown: "unknown",
  siteimprove_issues_help: "Fixing these issues will improve the score.",
  siteimprove_issue: "Issue",
  siteimprove_category: "Category",
  siteimprove_points_to_gain: "Points you can gain",
  siteimprove_filter: "Filter:",
  siteimprove_issues_all: "All",
  siteimprove_issues_a11y: "Accessibility",
  siteimprove_issues_a11y_count: "Accessibility Issues ({0})",
  siteimprove_issue_category_accessibility: "Accessibility",
  siteimprove_issues_qa: "Quality Assurance",
  siteimprove_issues_qa_count: "Quality Assurance Issues ({0})",
  siteimprove_issue_category_content: "Quality Assurance",
  siteimprove_issues_seo: "SEO",
  siteimprove_issue_category_seov2: "SEO",
  siteimprove_issues_seo_count: "SEO Issues ({0}):",
  siteimprove_issues_seo_content: "Content",
  siteimprove_issues_seo_technical: "Technical",
  siteimprove_error_EMAIL_NOT_SET: "Please provide a valid email adress in the configuration for {0}.",
  siteimprove_error_API_KEY_NOT_SET: "Please provide a valid apiKey in the configuration for {0}.",
  siteimprove_error_REST_ERROR: "Siteimprove responded with a problem: {1}",
  siteimprove_error_NO_CONTENT_METATAG_FOUND: "Could not find information for this content. Make sure it is crawled by Siteimprove.",
  siteimprove_error_NO_CONTENT_METATAG_WITH_CONTENT_ID_FOUND: "Could not find information for this content. Make sure it is crawled by Siteimproe.",
  siteimprove_error_NO_CONTENT_FOUND: "Could not find information for this content. Make sure it is crawled by Siteimprove.",
  siteimprove_error_live_NO_CONTENT_METATAG_FOUND: "Could not find information for this content. Make sure it is crawled by Siteimprove.",
  siteimprove_error_live_NO_CONTENT_METATAG_WITH_CONTENT_ID_FOUND: "Could not find information for this content. Make sure it is crawled by Siteimprove.",
  siteimprove_error_live_NO_CONTENT_FOUND: "Could not find information for this content. Make sure it is crawled by Siteimprove.",
  siteimprove_error_live_NOT_PUBLISHED: "The content is not published. Publish the content to get the Siteimprove scores.",
  recrawl_preview_page_btn_text: "Recheck",
  recrawl_preview_page_btn_tooltip: "Let Siteimprove recrawl the page",
  recrawl_live_page_btn_text: "Recheck",
  recrawl_live_page_btn_tooltip: "Recheck the Live Site page.",
  siteimprove_show_more: "Show more",
};

export default FeedbackHubSiteimprove_properties;
