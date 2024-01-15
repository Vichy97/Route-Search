package com.routesearch.ui.common.intent

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import logcat.LogPriority.WARN
import logcat.logcat

class IntentLauncher(
  private val context: Context,
  private val packageManager: PackageManager,
) {

  fun launchIntent(intent: Intent) {
    if (intent.resolveActivity(packageManager) == null) {
      logcat(WARN) { "No activity found to handle intent: $intent" }
    } else {
      context.startActivity(intent)
    }
  }
}
