package com.routesearch.di

import com.routesearch.appversion.AppVersion
import com.routesearch.data.dataModule
import com.routesearch.features.featuresModule
import com.routesearch.navigation.navigationModule
import com.routesearch.ui.common.commonUiModule
import com.routesearch.util.appversion.AppVersionProvider
import com.routesearch.util.utilModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val appModule = module {

  includes(
    dataModule,
    commonUiModule,
    featuresModule,
    navigationModule,
    utilModule,
  )

  singleOf(::AppVersion) bind AppVersionProvider::class
}
