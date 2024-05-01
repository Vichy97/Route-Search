package com.routesearch.features.imageviewer

import androidx.lifecycle.SavedStateHandle
import com.routesearch.features.navArgs
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val imageViewerModule = module {

  viewModelOf(::ImageViewerViewModel)

  factory<ImageViewerScreenArgs> {
    get<SavedStateHandle>().navArgs<ImageViewerScreenArgs>()
  }
}
