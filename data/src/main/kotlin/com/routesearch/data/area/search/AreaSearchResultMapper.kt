package com.routesearch.data.area.search

import com.routesearch.network.area.search.AreaSearchResult as RemoteAreaSearchResult

internal fun List<RemoteAreaSearchResult>.toAreaSearchResults() = map {
  it.toAreaSearchResult()
}

internal fun RemoteAreaSearchResult.toAreaSearchResult() = AreaSearchResult(
  id = id,
  name = name,
  pathTokens = pathTokens,
  totalClimbs = totalClimbs,
)
