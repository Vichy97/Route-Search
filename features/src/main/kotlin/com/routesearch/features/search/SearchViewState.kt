package com.routesearch.features.search

import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult

internal data class SearchViewState(
  val searchActive: Boolean = false,
  val searchQuery: String = "",
  val areaFilterSelected: Boolean = true,
  val climbFilterSelected: Boolean = true,
  val areaSearchResults: List<AreaSearchResult> = emptyList(),
  val climbSearchResults: List<ClimbSearchResult> = emptyList(),
) {

  val allFiltersSelected = areaFilterSelected && climbFilterSelected

  val noFiltersSelected = !areaFilterSelected && !climbFilterSelected
}
