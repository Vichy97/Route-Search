package com.routesearch.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routesearch.data.search.SearchHistoryRepository
import com.routesearch.data.search.SearchResults
import com.routesearch.data.search.SearchService
import com.routesearch.features.R
import com.routesearch.features.destinations.AreaScreenDestination
import com.routesearch.features.destinations.ClimbScreenDestination
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.snackbar.SnackbarManager
import com.routesearch.util.common.result.onFailure
import com.routesearch.util.common.result.onSuccess
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
  private val snackbarManager: SnackbarManager,
  private val navigator: Navigator,
) : ViewModel() {

  private val _viewState = MutableStateFlow(SearchViewState())
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
    searchHistoryRepository.searchHistory()
      .collect { onSearchHistoryChange(it) }
  }

  private fun onSearchHistoryChange(searchHistory: List<String>) = _viewState.update {
    it.copy(
      searchHistory = searchHistory,
    )
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
        searchQuery = query,
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
        .onFailure { onSearchFailure() }
    }
  }

  private fun saveSearchQuery(query: String) = viewModelScope.launch {
    searchHistoryRepository.addSearchQuery(query)
  }

  private fun cancelOngoingSearch() = searchJob?.cancel()

  private fun clearSearchResults() = _viewState.update {
    it.copy(
      areaSearchResults = emptyList(),
      climbSearchResults = emptyList(),
      searchQuery = "",
    )
  }

  private fun onSearchSuccess(searchResults: SearchResults) = _viewState.update {
    it.copy(
      areaSearchResults = searchResults.areaSearchResults,
      climbSearchResults = searchResults.climbSearchResults,
    )
  }

  private fun onSearchFailure() {
    snackbarManager.showSnackbar(R.string.search_screen_search_error_message)
  }

  fun onSearchActiveChange(searchActive: Boolean) {
    _viewState.update {
      it.copy(
        searchQuery = "",
        searchActive = searchActive,
      )
    }
    searchQuery.value = ""
  }

  fun onBackClick() {
    _viewState.update {
      it.copy(
        searchActive = false,
        searchQuery = "",
      )
    }
    searchQuery.value = ""
  }

  fun onClearClick() {
    clearSearchResults()
    searchQuery.value = ""
  }

  fun onAreaFilterClick() = _viewState.update {
    it.copy(
      areaFilterSelected = !it.areaFilterSelected,
    )
  }

  fun onClimbFilterClick() = _viewState.update {
    it.copy(
      climbFilterSelected = !it.climbFilterSelected,
    )
  }

  fun onAreaSearchResultClick(id: String) {
    saveSearchQuery(viewState.value.searchQuery)

    navigator.navigate(AreaScreenDestination(id))
  }

  fun onClimbSearchResultClick(id: String) {
    saveSearchQuery(viewState.value.searchQuery)

    navigator.navigate(ClimbScreenDestination(id))
  }

  fun onSearchHistoryEntryClick(entry: String) {
    searchQuery.value = entry

    _viewState.update {
      it.copy(
        searchQuery = entry,
      )
    }
  }
}
