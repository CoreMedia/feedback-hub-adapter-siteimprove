package com.coremedia.blueprint.feedbackhub.siteimprove;

/**
 * List of SEO issues that are shown to the user.
 * You can find a list of all available issues in @see SeoIssuesTypes.
 * Change the list if you think that issues are relevant/irrelevant for the editor.
 */
public enum SeoIssueTypesEditorWhitelist {
  duplicate_description,
  duplicate_page_title,
  broken_links,
  broken_links_entry_pages,
  long_sentences,
  long_urls,
  many_internal_links,
  misspelling_entry_pages,
  misspellings,
  navigation_depth,
  page_size,
  pages_with_broken_links,
  pages_with_images_1mb,
  pages_with_misspellings,
  readability_pages_score,
  status_code_404,
  status_code_4xxs,
  urls_with_underscore,
  words_per_page
}
