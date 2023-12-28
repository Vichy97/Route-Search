package com.routesearch.features.area

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routesearch.data.area.Area
import com.routesearch.data.area.AreaRepository
import com.routesearch.features.R
import com.routesearch.features.area.AreaScreen.areaIdArg
import com.routesearch.ui.common.snackbar.SnackbarManager
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AreaViewModel(
  savedStateHandle: SavedStateHandle,
  private val areaRepository: AreaRepository,
  private val snackbarManager: SnackbarManager,
) : ViewModel() {

  private val _viewState = MutableStateFlow<AreaViewState>(AreaViewState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    val areaId: String = checkNotNull(savedStateHandle[areaIdArg.name])
    fetchArea(areaId)
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

  fun onDownloadClicked() {
    TODO("Not implemented")
  }

  fun onSharedClicked() {
    TODO("Not implemented")
  }
}
