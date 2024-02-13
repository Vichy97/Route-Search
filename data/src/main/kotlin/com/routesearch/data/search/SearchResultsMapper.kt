package com.routesearch.data.search

import kotlinx.collections.immutable.toImmutableList
import com.routesearch.data.remote.area.search.AreaSearchResult as RemoteAreaSearchResult
import com.routesearch.data.remote.climb.search.ClimbSearchResult as RemoteClimbSearchResult

internal fun List<RemoteClimbSearchResult>.toClimbSearchResults() = map {
  it.toClimbSearchResult()
}.toImmutableList()

internal fun RemoteClimbSearchResult.toClimbSearchResult() = ClimbSearchResult(
  id = id,
  name = name,
  pathTokens = pathTokens.toImmutableList(),
  grade = grade,
  type = types.firstOrNull() ?: "",
)

internal fun List<RemoteAreaSearchResult>.toAreaSearchResults() = map {
  it.toAreaSearchResult()
}.toImmutableList()

internal fun RemoteAreaSearchResult.toAreaSearchResult() = AreaSearchResult(
  id = id,
  name = name,
  pathTokens = pathTokens.toImmutableList(),
  totalClimbs = totalClimbs,
)
