package com.routesearch.features.search

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val searchModule = module {

  viewModelOf(::SearchViewModel)
}
