package com.routesearch.features

import com.routesearch.features.area.areaModule
import com.routesearch.features.search.searchModule
import org.koin.dsl.module

val featuresModule = module {

  includes(
    areaModule,
    searchModule,
  )
}
