package com.routesearch.features.area

import androidx.compose.runtime.Immutable
import com.routesearch.data.area.Area
import kotlinx.collections.immutable.ImmutableList

internal sealed interface AreaViewState {

  @Immutable
  data class Loading(
    val name: String,
    val path: ImmutableList<String>,
  ) : AreaViewState

  @Immutable
  data class Content(
    val area: Area,
  ) : AreaViewState

  @Immutable
  data object Idle : AreaViewState
}
