package com.routesearch.features.search

import androidx.compose.runtime.Immutable
import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
sealed class SearchViewState {

  abstract val searchActive: Boolean
  abstract val searchQuery: String

  abstract fun copy(
    newSearchQuery: String = this.searchQuery,
    newSearchActive: Boolean = this.searchActive,
  ): SearchViewState

  @Immutable
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

  @Immutable
  data class ShowingHistory(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
    val searchHistory: ImmutableList<String> = persistentListOf(),
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): ShowingHistory = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }

  @Immutable
  data class ShowingResults(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
    val areaSearchResults: ImmutableList<AreaSearchResult> = persistentListOf(),
    val climbSearchResults: ImmutableList<ClimbSearchResult> = persistentListOf(),
    val areaFilterSelected: Boolean = true,
    val climbFilterSelected: Boolean = true,
  ) : SearchViewState() {

    val allFiltersSelected = areaFilterSelected && climbFilterSelected

    val noFiltersSelected = !areaFilterSelected && !climbFilterSelected

    val hasNoResults = areaSearchResults.isEmpty() && climbSearchResults.isEmpty()

    val allResultsFiltered = noFiltersSelected ||
      (!climbFilterSelected && areaSearchResults.isEmpty()) ||
      (!areaFilterSelected && climbSearchResults.isEmpty())

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): ShowingResults = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }

  @Immutable
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

  @Immutable
  data class UnknownError(
    override val searchActive: Boolean = false,
    override val searchQuery: String = "",
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchActive: Boolean,
    ): UnknownError = copy(
      searchQuery = newSearchQuery,
      searchActive = newSearchActive,
    )
  }
}
