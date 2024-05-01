package com.routesearch.data.search

import kotlinx.coroutines.flow.StateFlow

/**
 * Repository for recent search queries.
 */
interface SearchHistoryRepository {

  val searchHistory: StateFlow<List<String>>

  suspend fun addSearchQuery(query: String)
}
