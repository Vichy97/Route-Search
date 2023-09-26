package com.routesearch.data.area.search

import com.routesearch.data.remote.area.search.AreaSearchDataSource

internal class DefaultAreaSearchService(
  private val areasSearchDataSource: AreaSearchDataSource,
) : AreaSearchService {

  override suspend fun searchForAreas(query: String) = areasSearchDataSource.searchForAreas(query)
    .map { it.toAreaSearchResults() }
}
