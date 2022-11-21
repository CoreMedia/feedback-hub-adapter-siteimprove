import CopyResourceBundleProperties from "@coremedia/studio-client.main.editor-components/configuration/CopyResourceBundleProperties";
import StudioPlugin from "@coremedia/studio-client.main.editor-components/configuration/StudioPlugin";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import feedbackService from "@coremedia/studio-client.main.feedback-hub-editor-components/feedbackService";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
import FeedbackHubSiteimprove_properties from "./FeedbackHubSiteimprove_properties";
import ComparingGaugeItemPanel from "./itemtypes/ComparingGaugeItemPanel";
import IssueListItemPanel from "./itemtypes/IssueListItemPanel";
import SiteimproveFooter from "./itemtypes/SiteimproveFooter";

interface SiteimproveFeedbackHubStudioPluginConfig extends Config<StudioPlugin> {
}

class SiteimproveFeedbackHubStudioPlugin extends StudioPlugin {
  declare Config: SiteimproveFeedbackHubStudioPluginConfig;

  static readonly xtype: string = "com.coremedia.blueprint.studio.feedbackhub.siteimprove.config.siteimproveFeedbackHubStudioPlugin";

  constructor(config: Config<SiteimproveFeedbackHubStudioPlugin> = null) {
    super((()=>{
      this.#__initialize__(config);
      return ConfigUtils.apply(Config(SiteimproveFeedbackHubStudioPlugin, {

        configuration: [
          new CopyResourceBundleProperties({
            destination: resourceManager.getResourceBundle(null, FeedbackHub_properties),
            source: resourceManager.getResourceBundle(null, FeedbackHubSiteimprove_properties),
          }),
        ],

      }), config);
    })());
  }

  #__initialize__(config: Config<SiteimproveFeedbackHubStudioPlugin>): void {
    feedbackService._.registerFeedbackItemPanel("siteimproveFooter", Config(SiteimproveFooter));
    feedbackService._.registerFeedbackItemPanel("siteimproveIssueList", Config(IssueListItemPanel));
    feedbackService._.registerFeedbackItemPanel("siteimproveComparingGauge", Config(ComparingGaugeItemPanel));
  }
}

export default SiteimproveFeedbackHubStudioPlugin;
