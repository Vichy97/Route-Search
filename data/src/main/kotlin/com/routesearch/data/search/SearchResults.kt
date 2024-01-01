package com.routesearch.data.search

data class SearchResults(
  val areaSearchResults: List<AreaSearchResult>,
  val climbSearchResults: List<ClimbSearchResult>,
)

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

data class AreaSearchResult(
  val id: String,
  val name: String,
  val pathTokens: List<String>,
  val totalClimbs: Int,
) {

  val pathText = pathTokens.drop(1).joinToString(" / ")
}
