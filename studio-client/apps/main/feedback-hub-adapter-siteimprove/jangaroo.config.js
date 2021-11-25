const { jangarooConfig } = require("@jangaroo/core");

module.exports = jangarooConfig({
  type: "code",
  sencha: {
    name: "com.coremedia.blueprint__feedback-hub-siteimprove-studio",
    namespace: "com.coremedia.blueprint.studio.feedbackhub.siteimprove",
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
});
