package com.routesearch.features.climb

import androidx.compose.runtime.Immutable
import com.routesearch.data.climb.Climb
import kotlinx.collections.immutable.ImmutableList

internal sealed interface ClimbViewState {

  @Immutable
  data class Loading(
    val name: String,
    val path: ImmutableList<String>,
  ) : ClimbViewState

  data class Content(
    val climb: Climb,
  ) : ClimbViewState

  data object Idle : ClimbViewState
}
