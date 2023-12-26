package com.routesearch.data.climb.search

import com.routesearch.data.remote.climb.search.ClimbSearchResult as RemoteClimbSearchResult

internal fun List<RemoteClimbSearchResult>.toClimbSearchResults() = map {
  it.toClimbSearchResult()
}

internal fun RemoteClimbSearchResult.toClimbSearchResult() = ClimbSearchResult(
  id = id,
  name = name,
)
