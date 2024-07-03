package com.routesearch.features.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.AreaScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ClimbScreenDestination
import com.ramcosta.composedestinations.generated.destinations.GalleryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ImageViewerScreenDestination
import com.ramcosta.composedestinations.generated.destinations.OrganizationDialogDestination
import com.routesearch.data.area.Area
import com.routesearch.data.area.AreaRepository
import com.routesearch.data.image.ImageLoader
import com.routesearch.features.common.intent.GeoIntent
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.intent.IntentLauncher
import com.routesearch.util.common.error.Error
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val IMAGE_CACHE_COUNT = 10

internal class AreaViewModel(
  private val args: AreaScreenArgs,
  private val areaRepository: AreaRepository,
  private val navigator: Navigator,
  private val intentLauncher: IntentLauncher,
  private val imageLoader: ImageLoader,
) : ViewModel() {

  private val _viewState = MutableStateFlow<AreaViewState>(
    AreaViewState.Loading(
      name = args.name,
      path = args.path.toImmutableList(),
    ),
  )
  val viewState = _viewState.asStateFlow()

  init {
    fetchArea(args.id)
  }

  private fun fetchArea(areaId: String) = viewModelScope.launch {
    areaRepository.getArea(areaId)
      .onSuccess(::onFetchAreaSuccess)
      .onFailure(::onFetchAreaFailure)
  }

  private fun onFetchAreaSuccess(area: Area) {
    _viewState.update {
      AreaViewState.Content(area)
    }

    area.media.take(IMAGE_CACHE_COUNT).forEach {
      viewModelScope.launch {
        imageLoader.load(it)
      }
    }
  }

  private fun onFetchAreaFailure(error: Error) = _viewState.update {
    if (error is Error.Network) {
      AreaViewState.NetworkError(
        name = it.name,
        path = it.path,
      )
    } else {
      AreaViewState.UnknownError(
        name = it.name,
        path = it.path,
      )
    }
  }

  fun onBackClick() = navigator.popBackStack()

  fun onHomeClick() {
    TODO("Not implemented")
  }

  fun onBookmarkClick() {
    TODO("Not implemented")
  }

  fun onDownloadClick() {
    TODO("Not implemented")
  }

  fun onShareClick() {
    TODO("Not implemented")
  }

  fun onPathSectionClick(pathSection: String) = (viewState.value as? AreaViewState.Content)?.run {
    val pathIndex = area.path.indexOf(pathSection)
    val ancestorId = area.ancestorIds[pathIndex]
    val ancestorPath = area.path.subList(0, pathIndex + 1)

    navigator.navigate(
      AreaScreenDestination(
        id = ancestorId,
        name = pathSection,
        path = ArrayList(ancestorPath),
      ),
    )
  }

  fun onLocationClick() = (viewState.value as? AreaViewState.Content)?.run {
    val intent = GeoIntent(
      location = area.location,
      name = area.name,
    )
    intentLauncher.launchIntent(intent)
  }

  fun onOrganizationClick(organization: Area.Organization) = navigator.navigate(
    OrganizationDialogDestination(
      name = organization.name,
      websiteUrl = organization.website,
      description = organization.description,
      facebookUrl = organization.facebookUrl,
      instagramUrl = organization.instagramUrl,
    ),
  )

  fun onFilterClimbsClick() {
    TODO("Not implemented")
  }

  fun onClimbClick(id: String) = (viewState.value as? AreaViewState.Content)?.run {
    val climb = area.climbs
      .first { it.id == id }

    navigator.navigate(
      ClimbScreenDestination(
        id = id,
        name = climb.name,
        path = ArrayList(area.path),
      ),
    )
  }

  fun onAreaClick(id: String) = (viewState.value as? AreaViewState.Content)?.run {
    val child = area.children
      .first { it.id == id }
    navigator.navigate(
      AreaScreenDestination(
        id = id,
        name = child.name,
        path = ArrayList(area.path + child.name),
      ),
    )
  }

  fun onShowAllImagesClick() = (viewState.value as? AreaViewState.Content)?.run {
    navigator.navigate(GalleryScreenDestination(ArrayList(area.media)))
  }

  fun onImageClick(index: Int) = (viewState.value as? AreaViewState.Content)?.run {
    navigator.navigate(ImageViewerScreenDestination(ArrayList(area.media), index))
  }

  fun onRetryClick() {
    _viewState.update {
      AreaViewState.Loading(
        name = it.name,
        path = it.path,
      )
    }
    fetchArea(args.id)
  }
}
