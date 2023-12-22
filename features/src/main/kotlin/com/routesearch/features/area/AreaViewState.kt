package com.routesearch.features.area

import com.routesearch.data.area.Area

internal sealed interface AreaViewState {

  data object Loading : AreaViewState

  data class Content(
    val area: Area,
  ) : AreaViewState

  data object Idle : AreaViewState
}
