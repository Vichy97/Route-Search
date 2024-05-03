package com.routesearch.features.gallery

import androidx.lifecycle.ViewModel
import com.routesearch.features.destinations.ImageViewerScreenDestination
import com.routesearch.navigation.Navigator
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class GalleryViewModel(
  args: GalleryScreenArgs,
  private val navigator: Navigator,
) : ViewModel() {

  private val _viewState = MutableStateFlow(GalleryViewState(args.urls.toImmutableList()))
  val viewState = _viewState.asStateFlow()

  fun onBackClick() = navigator.popBackStack()

  fun onImageClick(index: Int) {
    navigator.navigate(ImageViewerScreenDestination(ArrayList(_viewState.value.urls), index))
  }
}
