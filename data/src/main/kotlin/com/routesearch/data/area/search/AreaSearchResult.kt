package com.routesearch.data.area.search

data class AreaSearchResult(
  val id: String,
  val name: String,
  val pathTokens: List<String>,
  val totalClimbs: Int,
)
