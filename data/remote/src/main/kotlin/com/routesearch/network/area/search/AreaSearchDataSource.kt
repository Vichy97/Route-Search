package com.routesearch.network.area.search

interface AreaSearchDataSource {

  suspend fun searchForAreas(query: String): Result<List<AreaSearchResult>>
}
