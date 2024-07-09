package com.routesearch.features.climb

import androidx.compose.runtime.Immutable
import com.routesearch.data.climb.Climb
import kotlinx.collections.immutable.ImmutableList

internal sealed interface ClimbViewState {

  val name: String
  val path: ImmutableList<String>

  @Immutable
  data class Loading(
    override val name: String,
    override val path: ImmutableList<String>,
  ) : ClimbViewState

  data class Content(
    val climb: Climb,
    override val name: String = climb.name,
    override val path: ImmutableList<String> = climb.pathTokens,
  ) : ClimbViewState

  @Immutable
  data class NetworkError(
    override val name: String,
    override val path: ImmutableList<String>,
  ) : ClimbViewState

  @Immutable
  data class UnknownError(
    override val name: String,
    override val path: ImmutableList<String>,
  ) : ClimbViewState
}
