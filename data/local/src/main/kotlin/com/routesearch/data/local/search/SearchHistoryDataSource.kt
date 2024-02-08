package com.routesearch.data.local.search

import kotlinx.coroutines.flow.Flow

/**
 * Data source for recent search queries.
 */
interface SearchHistoryDataSource {

  /**
   * @return Flow of the most recently saved queries in order of newest to oldest.
   */
  fun searchHistory(): Flow<List<String>>

  /**
   * Save a query to search history.
   *
   * @param [query] Query to save.
   */
  suspend fun addSearchQuery(query: String)
}
