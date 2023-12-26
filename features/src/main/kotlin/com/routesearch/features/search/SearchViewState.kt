package com.routesearch.features.search

import com.routesearch.data.area.search.AreaSearchResult
import com.routesearch.data.climb.search.ClimbSearchResult

internal data class SearchViewState(
  val areaSearchResults: List<AreaSearchResult> = emptyList(),
  val climbSearchResults: List<ClimbSearchResult> = emptyList(),
)
