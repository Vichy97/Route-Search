package com.routesearch.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routesearch.data.area.search.AreaSearchResult
import com.routesearch.data.area.search.AreaSearchService
import com.routesearch.features.R
import com.routesearch.features.area.AreaScreen
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.snackbar.SnackbarManager
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
  private val areaSearchService: AreaSearchService,
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
  }

  fun onSearchQueryChange(query: String) {
    if (query.isBlank()) {
      cancelOngoingSearch()
      clearSearchResults()
    }

    searchQuery.value = query
  }

  fun onSearch(query: String) = search(query)

  private fun search(query: String) {
    cancelOngoingSearch()
    searchJob = viewModelScope.launch {
      areaSearchService.searchForAreas(query)
        .onSuccess(::onSearchSuccess)
        .onFailure { onSearchFailure() }
    }
  }

  private fun cancelOngoingSearch() = searchJob?.cancel()

  private fun clearSearchResults() = _viewState.update {
    it.copy(areaSearchResults = emptyList())
  }

  private fun onSearchSuccess(areaSearchResults: List<AreaSearchResult>) = _viewState.update {
    it.copy(
      areaSearchResults = areaSearchResults,
    )
  }

  private fun onSearchFailure() {
    snackbarManager.showSnackbar(R.string.search_screen_search_error_message)
  }

  fun onAreaClicked(id: String) {
    navigator.navigate(AreaScreen.getDestination(id))
  }
}
