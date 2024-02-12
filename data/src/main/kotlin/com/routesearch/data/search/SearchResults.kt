package com.routesearch.data.search

import androidx.compose.runtime.Immutable

@Immutable
data class SearchResults(
  val areaSearchResults: List<AreaSearchResult>,
  val climbSearchResults: List<ClimbSearchResult>,
)

@Immutable
data class ClimbSearchResult(
  val id: String,
  val name: String,
  val pathTokens: List<String>,
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
  val pathTokens: List<String>,
  val totalClimbs: Int,
) {

  val pathText = pathTokens.drop(1).joinToString(" / ")
}
