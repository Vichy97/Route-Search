package com.routesearch.features.gallery

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class GalleryViewState(
  val urls: ImmutableList<String>,
)
