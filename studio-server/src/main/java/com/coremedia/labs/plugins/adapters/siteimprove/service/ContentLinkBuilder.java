package com.coremedia.labs.plugins.adapters.siteimprove.service;

import com.coremedia.cap.content.Content;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public interface ContentLinkBuilder {
  @Nullable
  String buildLink(@NonNull Boolean isPreview, @NonNull Content content);
}
