package com.routesearch.features.imageviewer

import androidx.lifecycle.ViewModel
import com.routesearch.navigation.Navigator
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ImageViewerViewModel(
  args: ImageViewerScreenArgs,
  private val navigator: Navigator,
) : ViewModel() {

  private val _viewState = MutableStateFlow(
    ImageViewerViewState(
      urls = args.urls.toImmutableList(),
      index = args.index,
    ),
  )
  val viewState = _viewState.asStateFlow()

  fun onBackClick() = navigator.popBackStack()
}
