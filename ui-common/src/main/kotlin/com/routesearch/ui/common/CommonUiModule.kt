package com.routesearch.ui.common

import android.content.Context
import android.content.pm.PackageManager
import com.routesearch.ui.common.intent.IntentLauncher
import com.routesearch.ui.common.snackbar.SnackbarManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonUiModule = module {

  singleOf(::SnackbarManager)

  singleOf(::IntentLauncher)

  single<PackageManager> {
    get<Context>().packageManager
  }
}
