package com.routesearch.data.area

import com.routesearch.data.climb.Type
import com.routesearch.data.climb.toGrades
import com.routesearch.data.location.getLocation
import com.routesearch.data.location.toLocation
import com.routesearch.data.media.toMedia
import com.routesearch.data.local.area.Area.Climb as LocalClimb
import com.routesearch.data.local.area.AreaWithClimbsAndChildren as LocalArea
import com.routesearch.data.local.area.Child as LocalChild
import com.routesearch.data.remote.AreaQuery.Area as RemoteArea
import com.routesearch.data.remote.AreaQuery.Child as RemoteAreaChild

internal fun RemoteArea.toArea() = Area(
  id = uuid,
  metadata = getMetadata(),
  name = areaName,
  description = content?.description ?: "",
  path = pathTokens.filterNotNull(),
  ancestorIds = ancestors.filterNotNull(),
  gradeMap = getGradeMap(),
  location = metadata.getLocation(),
  children = children?.toChildren() ?: emptyList(),
  climbCount = getClimbCount(),
  climbs = climbs?.toClimbs() ?: emptyList(),
  organizations = organizations?.toOrganizations() ?: emptyList(),
  media = media?.mapNotNull { it?.mediaFragment?.toMedia() } ?: emptyList(),
)

@JvmName("remoteChildrenToChildren")
private fun List<RemoteAreaChild?>.toChildren() = filterNotNull()
  .map { it.toChild() }

private fun RemoteAreaChild.toChild() = Area.Child(
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
  gradeMap = area.gradeMap,
  location = area.location.toLocation(),
  children = children.toChildren(),
  climbCount = area.climbCount.toClimbCount(),
  climbs = climbs.toClimbs(),
  organizations = emptyList(), // Organizations aren't important to save for offline use.
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

@JvmName("localClimbsToClimbs")
private fun List<LocalClimb>.toClimbs() = map { it.toClimb() }

private fun LocalClimb.toClimb() = Area.Climb(
  id = id,
  name = name,
  grades = grades.toGrades(),
  type = Type.valueOf(type),
)
