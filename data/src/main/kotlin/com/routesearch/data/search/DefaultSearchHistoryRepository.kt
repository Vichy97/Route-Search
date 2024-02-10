package com.routesearch.data.search

import com.routesearch.data.local.search.SearchHistoryDataSource
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultSearchHistoryRepository(
  private val dataSource: SearchHistoryDataSource,
  private val coroutineContext: CoroutineContext,
) : SearchHistoryRepository {

  override fun searchHistory() = dataSource.searchHistory()
    .flowOn(coroutineContext)

  override suspend fun addSearchQuery(query: String) = withContext(coroutineContext) {
    dataSource.addSearchQuery(query)
  }
}
