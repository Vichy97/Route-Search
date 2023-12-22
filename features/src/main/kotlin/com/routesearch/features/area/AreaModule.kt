package com.routesearch.features.area

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val areaModule = module {

  viewModelOf(::AreaViewModel)
}
