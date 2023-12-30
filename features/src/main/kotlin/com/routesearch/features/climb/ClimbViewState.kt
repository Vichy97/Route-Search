package com.routesearch.features.climb

import com.routesearch.data.climb.Climb

internal sealed interface ClimbViewState {
  data object Loading : ClimbViewState

  data class Content(
    val climb: Climb,
  ) : ClimbViewState

  data object Idle : ClimbViewState
}
