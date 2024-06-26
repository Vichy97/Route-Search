package com.routesearch.features.climb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.AreaScreenDestination
import com.ramcosta.composedestinations.generated.destinations.GalleryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ImageViewerScreenDestination
import com.routesearch.data.climb.Climb
import com.routesearch.data.climb.ClimbRepository
import com.routesearch.features.common.intent.GeoIntent
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.intent.IntentLauncher
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ClimbViewModel(
  args: ClimbScreenArgs,
  private val climbRepository: ClimbRepository,
  private val navigator: Navigator,
  private val intentLauncher: IntentLauncher,
) : ViewModel() {

  private val _viewState = MutableStateFlow<ClimbViewState>(
    ClimbViewState.Loading(
      name = args.name,
      path = args.path.toImmutableList(),
    ),
  )
  val viewState = _viewState.asStateFlow()

  init {
    fetchClimb(args.id)
  }

  private fun fetchClimb(climbId: String) = viewModelScope.launch {
    climbRepository.getClimb(climbId)
      .onSuccess(::onFetchClimbSuccess)
      .onFailure { onFetchClimbFailure() }
  }

  private fun onFetchClimbSuccess(climb: Climb) = _viewState.update {
    ClimbViewState.Content(climb)
  }

  private fun onFetchClimbFailure() {
    _viewState.update { ClimbViewState.Idle }
  }

  fun onBackClick() = navigator.popBackStack()

  fun onBookmarkClick() {
    TODO("Not implemented")
  }

  fun onShareClick() {
    TODO("Not implemented")
  }

  fun onPathSectionClick(pathSection: String) = (viewState.value as? ClimbViewState.Content)?.run {
    val pathIndex = climb.pathTokens.indexOf(pathSection)
    val ancestorId = climb.ancestorIds[pathIndex]
    val ancestorPath = climb.pathTokens.subList(0, pathIndex + 1)

    navigator.navigate(
      AreaScreenDestination(
        id = ancestorId,
        name = pathSection,
        path = ArrayList(ancestorPath),
      ),
    )
  }

  fun onLocationClick() = (viewState.value as? ClimbViewState.Content)?.run {
    val location = climb.location ?: return@run
    val intent = GeoIntent(
      location = location,
      name = climb.name,
    )
    intentLauncher.launchIntent(intent)
  }

  fun onShowAllImagesClick() = (viewState.value as? ClimbViewState.Content)?.run {
    navigator.navigate(GalleryScreenDestination(ArrayList(climb.media)))
  }

  fun onImageClick(index: Int) = (viewState.value as? ClimbViewState.Content)?.run {
    navigator.navigate(ImageViewerScreenDestination(ArrayList(climb.media), index))
  }
}
