package com.routesearch.features.climb

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val climbModule = module {

  viewModelOf(::ClimbViewModel)
}
