package com.routesearch.data.search

import com.routesearch.data.local.search.SearchHistoryDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultSearchHistoryRepository(
  private val dataSource: SearchHistoryDataSource,
  private val coroutineContext: CoroutineContext,
) : SearchHistoryRepository {

  // This repository is a singleton that lives for the duration of the app so there is no need to cancel this scope.
  private val coroutineScope = CoroutineScope(coroutineContext)

  override val searchHistory = dataSource.searchHistory()
    .flowOn(coroutineContext)
    .stateIn(
      scope = coroutineScope,
      started = Eagerly,
      initialValue = emptyList(),
    )

  override suspend fun addSearchQuery(query: String) = withContext(coroutineContext) {
    dataSource.addSearchQuery(query)
  }
}
