package com.routesearch.data.search

import kotlinx.coroutines.flow.Flow

/**
 * Repository for recent search queries.
 */
interface SearchHistoryRepository {

  fun searchHistory(): Flow<List<String>>

  suspend fun addSearchQuery(query: String)
}
