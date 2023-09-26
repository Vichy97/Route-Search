package com.routesearch.data.remote.area.search

interface AreaSearchDataSource {

  suspend fun searchForAreas(query: String): Result<List<AreaSearchResult>>
}
