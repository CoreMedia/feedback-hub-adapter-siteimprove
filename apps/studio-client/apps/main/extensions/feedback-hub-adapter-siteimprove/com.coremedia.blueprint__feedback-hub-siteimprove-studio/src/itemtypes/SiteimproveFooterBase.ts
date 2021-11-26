import Config from "@jangaroo/runtime/Config";
import { asConfig } from "@jangaroo/runtime";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import SiteimproveFooter from "./SiteimproveFooter";
import GenericRemoteJob from "@coremedia/studio-client.cap-rest-client-impl/common/impl/GenericRemoteJob";
import JobExecutionError from "@coremedia/studio-client.cap-rest-client/common/JobExecutionError";
import jobService from "@coremedia/studio-client.cap-rest-client/common/jobService";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import LoadMaskSkin from "@coremedia/studio-client.ext.ui-components/skins/LoadMaskSkin";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import FeedbackHelper from "@coremedia/studio-client.main.feedback-hub-editor-components/util/FeedbackHelper";
import LoadMask from "@jangaroo/ext-ts/LoadMask";
import Button from "@jangaroo/ext-ts/button/Button";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
import trace from "@jangaroo/runtime/trace";
interface SiteimproveFooterBaseConfig extends Config<FeedbackItemPanel> {
}



class SiteimproveFooterBase extends FeedbackItemPanel {
  declare Config: SiteimproveFooterBaseConfig;

  static readonly RECRAWL_BUTTON_ITEM_ID:string = "recrawlButton";
  static readonly RELOAD_BUTTON_ITEM_ID:string = "reloadButton";

  //they match the collection id of the feedback items
  static readonly PREVEW_ITEM_ID:string = "preview";
  static readonly LIVE_ITEM_ID:string = "comparison";

  #loadMask:LoadMask = null;

  constructor(config:Config<SiteimproveFooter> = null) {
    super(config);
  }

  getLastPreviewCrawlDateExpression(config:Config<SiteimproveFooter>):ValueExpression {
    return ValueExpressionFactory.createFromFunction(():string => {
      var label = FeedbackHubSiteimprove_properties.siteimprove_preview_site;
      var time:number = config.feedbackItem["lastPreviewUpdate"];
      if (time > 0) {
        var date = new Date(time);
        label = label + ": " + FeedbackHelper.getDateDiff(date);
      }

      return label;
    });
  }

  getLastLiveCrawlDateExpression(config:Config<SiteimproveFooter>):ValueExpression {
    return ValueExpressionFactory.createFromFunction(():string => {
      var label = FeedbackHubSiteimprove_properties.siteimprove_live_site;
      var time:number = config.feedbackItem["lastLiveUpdate"];
      if (time > 0) {
        var date = new Date(time);
        label = label + ": " + FeedbackHelper.getDateDiff(date);
      }

      return label;
    });
  }

  protected recrawlPage(b:Button):void {
    var JOB_TYPE = "recrawlPage";
    var pageId:string = this.feedbackItem["previewPageId"];
    var preview:boolean = asConfig(this.getFeedbackGroupPanel().getSubTabPanel().getActiveTab()).itemId === SiteimproveFooterBase.PREVEW_ITEM_ID;
    if(!preview) {
      pageId = this.feedbackItem["livePageId"];
    }

    b.setDisabled(true);
    jobService._.executeJob(
            new GenericRemoteJob(JOB_TYPE, {
              content: this.contentExpression.getValue(),
              preview: preview,
              pageId: pageId,
              checkStatusOnly: false
            }),
            //on success
            (result:any):void => {

              if (this.#loadMask && !this.#loadMask.destroyed) {
                this.#loadMask.destroy();
              }

              b.setDisabled(false);
              this.reload();
            },
            //on error
            (error:JobExecutionError):void => {
              trace("[ERROR]", "Error loading Feedback : " + error);
              this.#loadMask.hide();
              this.setDisabled(false);
            }
    );

    if(!this.#loadMask) {
      var loadMaskConfig = Config(LoadMask);
      loadMaskConfig.ui = LoadMaskSkin.TRANSPARENT.getSkin();
      loadMaskConfig.msg = "";
      loadMaskConfig.target = b;
      this.#loadMask = new LoadMask(loadMaskConfig);
    }

    this.#loadMask.show();
  }


  protected override onDestroy():void {
    super.onDestroy();
    if(this.#loadMask) {
      this.#loadMask.destroy();
    }
  }
}
export default SiteimproveFooterBase;
