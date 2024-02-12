package com.routesearch.features.area

import androidx.compose.runtime.Immutable
import com.routesearch.data.area.Area

internal sealed interface AreaViewState {

  @Immutable
  data object Loading : AreaViewState

  @Immutable
  data class Content(
    val area: Area,
  ) : AreaViewState

  @Immutable
  data object Idle : AreaViewState
}
