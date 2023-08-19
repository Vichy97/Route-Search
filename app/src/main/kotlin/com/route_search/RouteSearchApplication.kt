package com.route_search

import android.app.Application
import com.route_search.di.appModule
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
