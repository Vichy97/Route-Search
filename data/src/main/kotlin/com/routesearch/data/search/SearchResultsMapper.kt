package com.routesearch.data.search

import com.routesearch.data.remote.area.search.AreaSearchResult as RemoteAreaSearchResult
import com.routesearch.data.remote.climb.search.ClimbSearchResult as RemoteClimbSearchResult

internal fun List<RemoteClimbSearchResult>.toClimbSearchResults() = map {
  it.toClimbSearchResult()
}

internal fun RemoteClimbSearchResult.toClimbSearchResult() = ClimbSearchResult(
  id = id,
  name = name,
)

internal fun List<RemoteAreaSearchResult>.toAreaSearchResults() = map {
  it.toAreaSearchResult()
}

internal fun RemoteAreaSearchResult.toAreaSearchResult() = AreaSearchResult(
  id = id,
  name = name,
  pathTokens = pathTokens,
  totalClimbs = totalClimbs,
)
