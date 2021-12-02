import Container from "@jangaroo/ext-ts/container/Container";
import Config from "@jangaroo/runtime/Config";
import FeedbackHubSiteimprove_properties from "../FeedbackHubSiteimprove_properties";
import IssueEntry from "./IssueEntry";

interface IssueEntryBaseConfig extends Config<Container>, Partial<Pick<IssueEntryBase,
  "issue"
>> {
}

class IssueEntryBase extends Container {
  declare Config: IssueEntryBaseConfig;

  #issue: any = null;

  get issue(): any {
    return this.#issue;
  }

  set issue(value: any) {
    this.#issue = value;
  }

  constructor(config: Config<IssueEntry> = null) {
    super(config);
  }

  protected resolveLinkLabel(issue: any): string {
    let label = "---";
    if (issue.issue_name) {
      label = FeedbackHubSiteimprove_properties["issue_" + issue.issue_name];
      if (!label) {
        label = this.#formatKey(issue.issue_name);
      }
    }

    return label;
  }

  getIssueCategory(issue: any): string {
    return FeedbackHubSiteimprove_properties.siteimprove_issue_category_seov2;
  }

  #formatKey(key: string): string {
    while (key.indexOf("_") !== -1) {
      key = key.replace("_", " ");
    }

    return key.replace(/(?:^\w|[A-Z]|\b\w)/g, (letter: any, index: any): string =>
      letter.toUpperCase(),
    ).replace(/\s+/g, " ");
  }

  protected openIssue(): void {
    const url: string = this.issue._siteimprove.page_report.href;
    window.open(url, "_blank");
  }
}

export default IssueEntryBase;
