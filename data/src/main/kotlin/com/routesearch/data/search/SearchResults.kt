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
)

data class AreaSearchResult(
  val id: String,
  val name: String,
  val pathTokens: List<String>,
  val totalClimbs: Int,
)
