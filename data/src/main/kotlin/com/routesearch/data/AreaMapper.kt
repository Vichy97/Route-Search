package com.routesearch.data

import com.routesearch.data.local.area.AreaWithChildren
import com.routesearch.data.local.area.Child as DbChild
import com.routesearch.data.remote.AreaQuery.Area as NetworkArea
import com.routesearch.data.remote.AreaQuery.Child as NetworkAreaChild

internal fun NetworkArea.toArea() = Area(
  id = id,
  name = areaName,
  description = content?.description ?: "",
  path = pathTokens.filterNotNull(),
  children = children?.toChildren() ?: emptyList(),
  totalClimbs = totalClimbs,
)

@JvmName("networkChildrenToChildren")
private fun List<NetworkAreaChild?>.toChildren() = filterNotNull()
  .map { it.toChild() }

private fun NetworkAreaChild.toChild() = Area.Child(
  id = uuid,
  name = areaName,
  totalClimbs = totalClimbs,
)

internal fun AreaWithChildren.toArea() = Area(
  id = area.id,
  name = area.name,
  description = area.description,
  path = area.path,
  children = children.toChildren(),
  totalClimbs = area.totalClimbs,
)

@JvmName("dbChildrenToChildren")
private fun List<DbChild>.toChildren() = map { it.toChild() }

private fun DbChild.toChild() = Area.Child(
  id = id,
  name = name,
  totalClimbs = totalClimbs,
)
