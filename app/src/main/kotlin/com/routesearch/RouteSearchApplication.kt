package com.routesearch

import android.app.Application
import com.routesearch.di.appModule
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RouteSearchApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    setUpLogcat()
    setUpKoin()
  }

  private fun setUpKoin() = startKoin {
    androidContext(this@RouteSearchApplication)
    if (BuildConfig.DEBUG) {
      androidLogger()
    }
    modules(appModule)
  }

  private fun setUpLogcat() = AndroidLogcatLogger.installOnDebuggableApp(
    application = this,
    minPriority = LogPriority.VERBOSE,
  )
}
