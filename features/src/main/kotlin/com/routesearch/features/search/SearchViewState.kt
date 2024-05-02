package com.routesearch.features.search

import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult

sealed class SearchViewState {

  abstract val searchActive: Boolean
  abstract val searchQuery: String

  abstract fun copy(
    newSearchQuery: String = this.searchQuery,
    newSearchActive: Boolean = this.searchActive,
  ): SearchViewState

  data class Loading(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): Loading = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }

  data class ShowingHistory(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
    val searchHistory: List<String>,
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): ShowingHistory = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }

  data class ShowingResults(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
    val areaSearchResults: List<AreaSearchResult> = emptyList(),
    val climbSearchResults: List<ClimbSearchResult> = emptyList(),
    val areaFilterSelected: Boolean = true,
    val climbFilterSelected: Boolean = true,
  ) : SearchViewState() {

    val allFiltersSelected = areaFilterSelected && climbFilterSelected

    val noFiltersSelected = !areaFilterSelected && !climbFilterSelected

    val hasNoResults = areaSearchResults.isEmpty() && climbSearchResults.isEmpty()

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): ShowingResults = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }

  data class NetworkError(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): NetworkError = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }
}
