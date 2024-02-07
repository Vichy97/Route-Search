package com.routesearch.ui.common.intent

import android.content.Intent

internal class WebIntent(url: String) : Intent(
  parseUri(url, URI_INTENT_SCHEME)
    .addFlags(FLAG_ACTIVITY_NEW_TASK),
)
