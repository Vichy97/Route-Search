package com.routesearch.features.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routesearch.data.area.Area
import com.routesearch.data.area.AreaRepository
import com.routesearch.features.R
import com.routesearch.features.common.intent.GeoIntent
import com.routesearch.features.destinations.AreaScreenDestination
import com.routesearch.features.destinations.ClimbScreenDestination
import com.routesearch.features.destinations.GalleryScreenDestination
import com.routesearch.features.destinations.OrganizationDialogDestination
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.intent.IntentLauncher
import com.routesearch.ui.common.snackbar.SnackbarManager
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AreaViewModel(
  args: AreaScreenArgs,
  private val areaRepository: AreaRepository,
  private val snackbarManager: SnackbarManager,
  private val navigator: Navigator,
  private val intentLauncher: IntentLauncher,
) : ViewModel() {

  private val _viewState = MutableStateFlow<AreaViewState>(AreaViewState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    fetchArea(args.id)
  }

  private fun fetchArea(areaId: String) = viewModelScope.launch {
    areaRepository.getArea(areaId)
      .onSuccess(::onFetchAreaSuccess)
      .onFailure { onFetchAreaFailure() }
  }

  private fun onFetchAreaSuccess(area: Area) = _viewState.update {
    AreaViewState.Content(area)
  }

  private fun onFetchAreaFailure() {
    _viewState.update { AreaViewState.Idle }

    snackbarManager.showSnackbar(
      message = R.string.area_screen_loading_error_message,
    )
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
    navigator.navigate(AreaScreenDestination(ancestorId))
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

  fun onClimbClick(id: String) = navigator.navigate(ClimbScreenDestination(id))

  fun onAreaClick(id: String) = navigator.navigate(AreaScreenDestination(id))

  fun onShowAllImagesClick() = (viewState.value as? AreaViewState.Content)?.run {
    navigator.navigate(GalleryScreenDestination(ArrayList(area.media)))
  }
}
