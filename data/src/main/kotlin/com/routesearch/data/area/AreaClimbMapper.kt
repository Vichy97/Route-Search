package com.routesearch.data.area

import com.routesearch.data.climb.toGrade
import com.routesearch.data.climb.toTypes
import kotlinx.collections.immutable.toImmutableList
import com.routesearch.data.remote.AreaQuery as RemoteArea

@JvmName("remoteClimbsToClimbs")
internal fun List<RemoteArea.Climb?>.toClimbs() = filterNotNull()
  .map { it.toClimb() }
  .toImmutableList()

internal fun RemoteArea.Climb.toClimb() = Area.Climb(
  id = uuid,
  grade = grades?.gradesFragment?.toGrade(type.typeFragment.bouldering == true),
  name = name,
  types = type.typeFragment.toTypes(),
  numberOfPitches = pitches?.count() ?: 1,
)
