package com.routesearch.data.search

import com.routesearch.util.common.result.Result

interface SearchService {

  suspend fun search(query: String): Result<SearchResults>
}
