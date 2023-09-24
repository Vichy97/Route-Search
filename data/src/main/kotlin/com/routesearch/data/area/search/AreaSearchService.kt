package com.routesearch.data.area.search

interface AreaSearchService {

  suspend fun searchForAreas(query: String): Result<List<AreaSearchResult>>
}
