package com.routesearch.data.area

import com.routesearch.data.climb.Type
import com.routesearch.data.climb.toGrades
import com.routesearch.data.location.getLocation
import com.routesearch.data.location.toLocation
import com.routesearch.data.media.toMedia
import com.routesearch.data.local.area.Area.Climb as LocalClimb
import com.routesearch.data.local.area.AreaWithClimbsAndChildren as LocalArea
import com.routesearch.data.local.area.Child as LocalChild
import com.routesearch.data.remote.AreaQuery.Area as NetworkArea
import com.routesearch.data.remote.AreaQuery.Child as NetworkAreaChild

internal fun NetworkArea.toArea() = Area(
  id = uuid,
  metadata = getMetadata(),
  name = areaName,
  description = content?.description ?: "",
  path = pathTokens.filterNotNull(),
  ancestorIds = ancestors.filterNotNull(),
  location = metadata.getLocation(),
  children = children?.toChildren() ?: emptyList(),
  totalClimbs = totalClimbs,
  climbs = climbs?.toClimbs() ?: emptyList(),
  media = media?.mapNotNull { it?.mediaFragment?.toMedia() } ?: emptyList(),
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
  metadata = area.metadata.toMetaData(),
  name = area.name,
  description = area.description,
  path = area.path,
  ancestorIds = area.ancestorIds,
  location = area.location.toLocation(),
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

@JvmName("LocalClimbsToClimbs")
private fun List<LocalClimb>.toClimbs() = map { it.toClimb() }

private fun LocalClimb.toClimb() = Area.Climb(
  id = id,
  name = name,
  grades = grades.toGrades(),
  type = Type.valueOf(type),
)
