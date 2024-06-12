package com.routesearch.features.search

import androidx.compose.runtime.Immutable
import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal sealed class SearchViewState {

  abstract val searchExpanded: Boolean
  abstract val searchQuery: String

  abstract fun copy(
    newSearchQuery: String = this.searchQuery,
    newSearchExpanded: Boolean = this.searchExpanded,
  ): SearchViewState

  @Immutable
  data class Loading(
    override val searchExpanded: Boolean = false,
    override val searchQuery: String = "",
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchExpanded: Boolean,
    ): Loading = copy(
      searchQuery = newSearchQuery,
      searchExpanded = newSearchExpanded,
    )
  }

  @Immutable
  data class ShowingHistory(
    override val searchExpanded: Boolean = false,
    override val searchQuery: String = "",
    val searchHistory: ImmutableList<String> = persistentListOf(),
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchExpanded: Boolean,
    ): ShowingHistory = copy(
      searchQuery = newSearchQuery,
      searchExpanded = newSearchExpanded,
    )
  }

  @Immutable
  data class ShowingResults(
    override val searchExpanded: Boolean = false,
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
      newSearchExpanded: Boolean,
    ): ShowingResults = copy(
      searchQuery = newSearchQuery,
      searchExpanded = newSearchExpanded,
    )
  }

  @Immutable
  data class NetworkError(
    override val searchExpanded: Boolean = false,
    override val searchQuery: String = "",
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchExpanded: Boolean,
    ): NetworkError = copy(
      searchQuery = newSearchQuery,
      searchExpanded = newSearchExpanded,
    )
  }

  @Immutable
  data class UnknownError(
    override val searchExpanded: Boolean = false,
    override val searchQuery: String = "",
  ) : SearchViewState() {

    override fun copy(
      newSearchQuery: String,
      newSearchExpanded: Boolean,
    ): UnknownError = copy(
      searchQuery = newSearchQuery,
      searchExpanded = newSearchExpanded,
    )
  }
}
