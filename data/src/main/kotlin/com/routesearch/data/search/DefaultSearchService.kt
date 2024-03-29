package com.routesearch.data.search

import com.routesearch.data.remote.area.search.AreaSearchDataSource
import com.routesearch.data.remote.climb.search.ClimbSearchDataSource
import com.routesearch.util.common.result.Result
import com.routesearch.util.common.result.combine
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultSearchService(
  private val coroutineContext: CoroutineContext,
  private val areaSearchDataSource: AreaSearchDataSource,
  private val climbSearchDataSource: ClimbSearchDataSource,
) : SearchService {

  override suspend fun search(query: String): Result<SearchResults> = withContext(coroutineContext) {
    val areas = async { areaSearchDataSource.searchForAreas(query) }.await()
    val climbs = async { climbSearchDataSource.searchForClimbs(query) }.await()

    return@withContext areas.combine(climbs) { areasSearchResults, climbSearchResults ->
      SearchResults(
        areaSearchResults = areasSearchResults.toAreaSearchResults(),
        climbSearchResults = climbSearchResults.toClimbSearchResults(),
      )
    }
  }
}
