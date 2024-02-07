package com.routesearch.ui.common.web

import com.routesearch.ui.common.intent.IntentLauncher
import com.routesearch.ui.common.intent.WebIntent

class WebLauncher(
  private val intentLauncher: IntentLauncher,
) {

  fun launchUrl(url: String) = intentLauncher.launchIntent(WebIntent(url))
}
