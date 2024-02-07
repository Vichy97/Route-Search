package com.routesearch.data.area

import com.routesearch.data.climb.toGrade
import com.routesearch.data.climb.toType
import com.routesearch.data.remote.AreaQuery as RemoteArea

@JvmName("remoteClimbsToClimbs")
internal fun List<RemoteArea.Climb?>.toClimbs() = filterNotNull()
  .map { it.toClimb() }

internal fun RemoteArea.Climb.toClimb() = Area.Climb(
  id = uuid,
  grades = grades?.gradesFragment?.toGrade(),
  name = name,
  type = type.typeFragment.toType(),
  numberOfPitches = pitches?.count() ?: 1,
)
