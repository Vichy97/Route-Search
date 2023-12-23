package com.routesearch.data.area.search

import com.routesearch.data.remote.area.search.AreaSearchDataSource
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultAreaSearchService(
  private val areasSearchDataSource: AreaSearchDataSource,
  private val coroutineContext: CoroutineContext,
) : AreaSearchService {

  override suspend fun searchForAreas(query: String) = withContext(coroutineContext) {
    areasSearchDataSource.searchForAreas(query)
      .map { it.toAreaSearchResults() }
  }
}
