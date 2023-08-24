package com.route_search.data

import com.route_search.network.AreaQuery.Area as NetworkArea
import com.route_search.network.AreaQuery.Child as NetworkAreaChild

internal fun NetworkArea.toArea() = Area(
  id = id,
  name = areaName,
  description = content?.description ?: "",
  path = pathTokens.filterNotNull(),
  children = children?.toChildren() ?: emptyList(),
  totalClimbs = totalClimbs,
)

private fun List<NetworkAreaChild?>.toChildren() = filterNotNull()
  .map { it.toChild() }

private fun NetworkAreaChild.toChild() = Area.Child(
  id = uuid,
  name = areaName,
  totalClimbs = totalClimbs,
)