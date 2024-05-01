package com.routesearch.features.imageviewer

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ImageViewerViewState(
  val urls: ImmutableList<String>,
  val index: Int,
)
