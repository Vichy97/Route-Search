package com.routesearch.data.search

import com.routesearch.data.remote.area.search.AreaSearchDataSource
import com.routesearch.data.remote.climb.search.ClimbSearchDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultSearchService(
  private val coroutineContext: CoroutineContext,
  private val areaSearchDataSource: AreaSearchDataSource,
  private val climbSearchDataSource: ClimbSearchDataSource,
) : SearchService {

  // TODO: Better error handling
  @Suppress("TooGenericExceptionCaught")
  override suspend fun search(query: String): Result<SearchResults> = withContext(coroutineContext) {
    val areas = async { areaSearchDataSource.searchForAreas(query) }
    val climbs = async { climbSearchDataSource.searchForClimbs(query) }

    return@withContext try {
      SearchResults(
        areaSearchResults = areas.await()
          .map { it.toAreaSearchResults() }
          .getOrThrow(),
        climbSearchResults = climbs.await()
          .map { it.toClimbSearchResults() }
          .getOrThrow(),
      ).let { Result.success(it) }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
