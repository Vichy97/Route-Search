package com.routesearch.features.area

import androidx.compose.runtime.Immutable
import com.routesearch.data.area.Area
import kotlinx.collections.immutable.ImmutableList

internal sealed interface AreaViewState {

  val name: String
  val path: ImmutableList<String>

  @Immutable
  data class Loading(
    override val name: String,
    override val path: ImmutableList<String>,
  ) : AreaViewState

  @Immutable
  data class Content(
    val area: Area,
    override val name: String = area.name,
    override val path: ImmutableList<String> = area.path,
  ) : AreaViewState

  @Immutable
  data class NetworkError(
    override val name: String,
    override val path: ImmutableList<String>,
  ) : AreaViewState
}
