package com.routesearch.data.remote.area.search

import com.routesearch.util.common.result.Result

interface AreaSearchDataSource {

  suspend fun searchForAreas(query: String): Result<List<AreaSearchResult>>
}
