package com.routesearch.data.remote.climb.search

import com.routesearch.util.common.result.Result

interface ClimbSearchDataSource {

  suspend fun searchForClimbs(query: String): Result<List<ClimbSearchResult>>
}
