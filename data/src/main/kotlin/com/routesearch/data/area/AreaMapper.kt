package com.routesearch.data.area

import com.routesearch.data.climb.toClimbs
import com.routesearch.data.media.toMedia
import com.routesearch.data.local.area.AreaWithClimbsAndChildren as LocalArea
import com.routesearch.data.local.area.Child as LocalChild
import com.routesearch.data.remote.AreaQuery.Area as NetworkArea
import com.routesearch.data.remote.AreaQuery.Child as NetworkAreaChild

internal fun NetworkArea.toArea() = Area(
  id = id,
  name = areaName,
  description = content?.description ?: "",
  path = pathTokens.filterNotNull(),
  children = children?.toChildren() ?: emptyList(),
  totalClimbs = totalClimbs,
  climbs = climbs?.toClimbs() ?: emptyList(),
  media = media?.toMedia() ?: emptyList(),
)

@JvmName("remoteChildrenToChildren")
private fun List<NetworkAreaChild?>.toChildren() = filterNotNull()
  .map { it.toChild() }

private fun NetworkAreaChild.toChild() = Area.Child(
  id = uuid,
  name = areaName,
  totalClimbs = totalClimbs,
  numberOfChildren = children?.filterNotNull()?.count() ?: 0,
)

internal fun LocalArea.toArea() = Area(
  id = area.id,
  name = area.name,
  description = area.description,
  path = area.path,
  children = children.toChildren(),
  totalClimbs = area.totalClimbs,
  climbs = climbs.toClimbs(),
  media = area.media,
)

@JvmName("localChildrenToChildren")
private fun List<LocalChild>.toChildren() = map { it.toChild() }

private fun LocalChild.toChild() = Area.Child(
  id = id,
  name = name,
  totalClimbs = totalClimbs,
  numberOfChildren = numberOfChildren,
)
