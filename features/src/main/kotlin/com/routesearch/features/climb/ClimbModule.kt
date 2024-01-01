package com.routesearch.features.climb

import androidx.lifecycle.SavedStateHandle
import com.routesearch.features.navArgs
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val climbModule = module {

  viewModelOf(::ClimbViewModel)

  factory<ClimbScreenArgs> {
    get<SavedStateHandle>().navArgs<ClimbScreenArgs>()
  }
}
