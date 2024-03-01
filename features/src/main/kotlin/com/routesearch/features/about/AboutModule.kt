package com.routesearch.features.about

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val aboutModule = module {

  viewModelOf(::AboutViewModel)
}
