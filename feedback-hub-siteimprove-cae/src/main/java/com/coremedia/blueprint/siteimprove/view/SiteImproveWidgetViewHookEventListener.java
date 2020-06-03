package com.coremedia.blueprint.siteimprove.view;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.blueprint.common.contentbeans.Page;
import com.coremedia.blueprint.siteimprove.SiteImproveWidget;
import com.coremedia.objectserver.view.RenderNode;
import com.coremedia.objectserver.view.events.ViewHookEvent;
import com.coremedia.objectserver.view.events.ViewHookEventListener;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static com.coremedia.blueprint.base.cae.web.taglib.ViewHookEventNames.VIEW_HOOK_HEAD;


@Named
public class SiteImproveWidgetViewHookEventListener implements ViewHookEventListener<Page> {

  private static final String SITE_IMPROVE = "siteImprove";

  @Inject
  private SettingsService settingsService;

  @Override
  public RenderNode onViewHook(ViewHookEvent<Page> event) {
    Map<String,Object> settings = settingsService.mergedSettingAsMap(SITE_IMPROVE, String.class, Object.class, event.getBean().getNavigation());
    SiteImproveWidget config = new SiteImproveWidget(settings);
    if (config.isEnabled() && VIEW_HOOK_HEAD.equals(event.getId())) {
      return new RenderNode(config, VIEW_HOOK_HEAD);
    }
    return null;
  }

  @Override
  public int getOrder() {
    return DEFAULT_ORDER;
  }
}
