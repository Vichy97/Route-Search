package com.routesearch.data.remote.climb.search

interface ClimbSearchDataSource {

  suspend fun searchForClimbs(query: String): Result<List<ClimbSearchResult>>
}
