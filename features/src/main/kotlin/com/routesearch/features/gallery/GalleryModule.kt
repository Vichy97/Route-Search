package com.routesearch.features.gallery

import androidx.lifecycle.SavedStateHandle
import com.ramcosta.composedestinations.generated.navArgs
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val galleryModule = module {

  viewModelOf(::GalleryViewModel)

  factory<GalleryScreenArgs> {
    get<SavedStateHandle>().navArgs<GalleryScreenArgs>()
  }
}
