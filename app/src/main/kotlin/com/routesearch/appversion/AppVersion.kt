package com.routesearch.appversion

import com.routesearch.BuildConfig
import com.routesearch.util.appversion.AppVersionProvider

class AppVersion : AppVersionProvider {

  override val appVersion = BuildConfig.VERSION_NAME
}
