package com.routesearch.di

import com.routesearch.data.dataModule
import com.routesearch.navigation.navigationModule
import com.routesearch.ui.common.commonUiModule
import org.koin.dsl.module

internal val appModule = module {

  includes(
    dataModule,
    commonUiModule,
    navigationModule,
  )
}
