package com.coremedia.blueprint.siteimprove.view;

import com.coremedia.blueprint.common.contentbeans.Page;
import com.coremedia.blueprint.siteimprove.SiteImproveMetadata;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.objectserver.view.RenderNode;
import com.coremedia.objectserver.view.events.ViewHookEvent;
import com.coremedia.objectserver.view.events.ViewHookEventListener;

import com.google.common.base.Strings;

import javax.inject.Named;

import static com.coremedia.blueprint.base.cae.web.taglib.ViewHookEventNames.VIEW_HOOK_HEAD;


@Named
public class SiteImproveMetadataViewHookEventListener implements ViewHookEventListener<Page> {

  @Override
  public RenderNode onViewHook(ViewHookEvent<Page> event) {
    String contentId = event.getBean().getContentId();
    if(!Strings.isNullOrEmpty(contentId)) {
      int id = IdHelper.parseContentId(contentId);
      SiteImproveMetadata tag = new SiteImproveMetadata(id);
      if (VIEW_HOOK_HEAD.equals(event.getId())) {
        return new RenderNode(tag, VIEW_HOOK_HEAD);
      }
    }

    return null;
  }

  @Override
  public int getOrder() {
    return DEFAULT_ORDER;
  }
}
