package com.routesearch.features.climb

import androidx.lifecycle.SavedStateHandle
import com.ramcosta.composedestinations.generated.navArgs
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val climbModule = module {

  viewModelOf(::ClimbViewModel)

  factory<ClimbScreenArgs> {
    get<SavedStateHandle>().navArgs<ClimbScreenArgs>()
  }
}
