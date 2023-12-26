package com.routesearch.data.search

import com.routesearch.data.area.search.AreaSearchResult
import com.routesearch.data.climb.search.ClimbSearchResult

data class SearchResults(
  val areaSearchResults: List<AreaSearchResult>,
  val climbSearchResults: List<ClimbSearchResult>,
)
