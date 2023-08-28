package com.routesearch

import android.app.Application
import com.routesearch.di.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RouteSearchApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    setUpKoin()
  }

  private fun setUpKoin() = startKoin {
    androidContext(this@RouteSearchApplication)
    if (BuildConfig.DEBUG) {
      androidLogger()
    }
    modules(appModule)
  }
}