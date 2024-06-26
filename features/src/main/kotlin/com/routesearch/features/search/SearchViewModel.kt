package com.routesearch.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.AreaScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ClimbScreenDestination
import com.routesearch.data.search.SearchHistoryRepository
import com.routesearch.data.search.SearchResults
import com.routesearch.data.search.SearchService
import com.routesearch.features.search.SearchViewState.NetworkError
import com.routesearch.features.search.SearchViewState.ShowingHistory
import com.routesearch.features.search.SearchViewState.ShowingResults
import com.routesearch.features.search.SearchViewState.UnknownError
import com.routesearch.navigation.Navigator
import com.routesearch.util.common.error.Error
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SEARCH_DEBOUNCE_MS = 200L

@OptIn(FlowPreview::class)
internal class SearchViewModel(
  private val searchService: SearchService,
  private val searchHistoryRepository: SearchHistoryRepository,
  private val navigator: Navigator,
) : ViewModel() {

  private val currentSearchHistory: List<String>
    get() = searchHistoryRepository.searchHistory.value

  private val _viewState = MutableStateFlow<SearchViewState>(
    ShowingHistory(
      searchHistory = currentSearchHistory.toImmutableList(),
    ),
  )
  val viewState = _viewState.asStateFlow()

  private var searchQuery = MutableStateFlow("")
  private var searchJob: Job? = null

  init {
    viewModelScope.launch {
      searchQuery.debounce(SEARCH_DEBOUNCE_MS)
        .filter { it.isNotBlank() }
        .collect { search(it) }
    }

    collectSearchHistory()
  }

  private fun collectSearchHistory() = viewModelScope.launch {
    searchHistoryRepository.searchHistory
      .collect { onSearchHistoryChange(it) }
  }

  private fun onSearchHistoryChange(searchHistory: List<String>) = _viewState.update {
    (it as? ShowingHistory)?.copy(
      searchHistory = searchHistory.toImmutableList(),
    ) ?: it
  }

  fun onSearchQueryChange(query: String) {
    searchQuery.value = query

    if (query.isBlank()) {
      cancelOngoingSearch()
      clearSearchResults()
      return
    }

    _viewState.update {
      it.copy(
        newSearchQuery = query,
      )
    }
  }

  fun onSearch(query: String) {
    search(query)
    saveSearchQuery(query)
  }

  private fun search(query: String) {
    cancelOngoingSearch()
    searchJob = viewModelScope.launch {
      searchService.search(query)
        .onSuccess(::onSearchSuccess)
        .onFailure(::onSearchFailure)
    }
  }

  private fun saveSearchQuery(query: String) = viewModelScope.launch {
    searchHistoryRepository.addSearchQuery(query)
  }

  private fun cancelOngoingSearch() = searchJob?.cancel()

  private fun clearSearchResults() = _viewState.update {
    ShowingHistory(
      searchExpanded = it.searchExpanded,
      searchQuery = "",
      searchHistory = currentSearchHistory.toImmutableList(),
    )
  }

  private fun onSearchSuccess(searchResults: SearchResults) = _viewState.update {
    ShowingResults(
      searchExpanded = it.searchExpanded,
      searchQuery = it.searchQuery,
      areaSearchResults = searchResults.areaSearchResults,
      climbSearchResults = searchResults.climbSearchResults,
    )
  }

  private fun onSearchFailure(error: Error) = _viewState.update {
    if (error is Error.Network) {
      NetworkError(
        searchExpanded = it.searchExpanded,
        searchQuery = it.searchQuery,
      )
    } else {
      UnknownError(
        searchExpanded = it.searchExpanded,
        searchQuery = it.searchQuery,
      )
    }
  }

  fun onSearchExpandedChange(expanded: Boolean) {
    _viewState.update {
      it.copy(
        newSearchQuery = "",
        newSearchExpanded = expanded,
      )
    }
    searchQuery.value = ""
  }

  fun onBackClick() {
    _viewState.update {
      ShowingHistory(
        searchExpanded = false,
        searchQuery = "",
        searchHistory = currentSearchHistory.toImmutableList(),
      )
    }
    searchQuery.value = ""
  }

  fun onClearClick() {
    clearSearchResults()
    searchQuery.value = ""
  }

  fun onAreaFilterClick() = _viewState.update {
    (it as ShowingResults).copy(
      areaFilterSelected = !it.areaFilterSelected,
    )
  }

  fun onClimbFilterClick() = _viewState.update {
    (it as ShowingResults).copy(
      climbFilterSelected = !it.climbFilterSelected,
    )
  }

  fun onAreaSearchResultClick(id: String) {
    saveSearchQuery(viewState.value.searchQuery)

    val result = (viewState.value as ShowingResults).areaSearchResults
      .first { it.id == id }
    navigator.navigate(
      AreaScreenDestination(
        id = id,
        name = result.name,
        path = ArrayList(result.pathTokens),
      ),
    )
  }

  fun onClimbSearchResultClick(id: String) {
    saveSearchQuery(viewState.value.searchQuery)

    val result = (viewState.value as ShowingResults).climbSearchResults
      .first { it.id == id }
    navigator.navigate(
      ClimbScreenDestination(
        id = id,
        name = result.name,
        path = ArrayList(result.pathTokens),
      ),
    )
  }

  fun onSearchHistoryEntryClick(entry: String) {
    _viewState.update {
      SearchViewState.Loading(
        searchExpanded = it.searchExpanded,
        searchQuery = entry,
      )
    }
    searchQuery.value = entry
  }

  fun onRetryClick() {
    _viewState.update {
      SearchViewState.Loading(
        searchExpanded = it.searchExpanded,
        searchQuery = it.searchQuery,
      )
    }
    search(query = viewState.value.searchQuery)
  }
}
