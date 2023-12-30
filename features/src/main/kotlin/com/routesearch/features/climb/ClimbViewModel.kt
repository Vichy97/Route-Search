package com.routesearch.features.climb

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routesearch.data.climb.Climb
import com.routesearch.data.climb.ClimbRepository
import com.routesearch.features.climb.ClimbScreen.climbIdArg
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ClimbViewModel(
  savedStateHandle: SavedStateHandle,
  private val climbRepository: ClimbRepository,
) : ViewModel() {

  private val _viewState = MutableStateFlow<ClimbViewState>(ClimbViewState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    val climbId: String = checkNotNull(savedStateHandle[climbIdArg.name])
    fetchClimb(climbId)
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
}
