/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  extName: "com.coremedia.blueprint__feedback-hub-siteimprove-studio",
  extNamespace: "com.coremedia.blueprint.studio.feedbackhub.siteimprove",
  sencha: {
    studioPlugins: [
      {
        mainClass: "com.coremedia.blueprint.studio.feedbackhub.siteimprove.SiteimproveFeedbackHubStudioPlugin",
        name: "FeedbackHub for Siteimprove",
      },
    ],
  },
  command: {
    build: {
      ignoreTypeErrors: true
    },
  },
};
