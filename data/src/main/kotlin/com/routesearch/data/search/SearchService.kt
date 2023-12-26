package com.routesearch.data.search

interface SearchService {

  suspend fun search(query: String): Result<SearchResults>
}
