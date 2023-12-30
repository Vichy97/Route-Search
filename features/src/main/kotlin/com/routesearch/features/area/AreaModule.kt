package com.routesearch.features.area

import androidx.lifecycle.SavedStateHandle
import com.routesearch.features.navArgs
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val areaModule = module {

  viewModelOf(::AreaViewModel)

  factory<AreaScreenArgs> {
    get<SavedStateHandle>().navArgs<AreaScreenArgs>()
  }
}
