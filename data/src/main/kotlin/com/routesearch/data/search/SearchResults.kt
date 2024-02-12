package com.routesearch.data.search

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SearchResults(
  val areaSearchResults: ImmutableList<AreaSearchResult>,
  val climbSearchResults: ImmutableList<ClimbSearchResult>,
)

@Immutable
data class ClimbSearchResult(
  val id: String,
  val name: String,
  val pathTokens: ImmutableList<String>,
  val grade: String,
  val type: String,
) {

  val pathText = pathTokens.drop(1).joinToString(" / ")

  val subtitle = listOf(type, grade)
    .filter { it.isNotEmpty() }
    .joinToString(" - ")
}

@Immutable
data class AreaSearchResult(
  val id: String,
  val name: String,
  val pathTokens: ImmutableList<String>,
  val totalClimbs: Int,
) {

  val pathText = pathTokens.drop(1).joinToString(" / ")
}
