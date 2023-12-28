package com.routesearch.data.climb

import com.routesearch.data.location.getLocation
import com.routesearch.data.location.toLocation
import com.routesearch.data.media.toMedia
import com.routesearch.data.local.climb.ClimbWithPitches as LocalClimbWithPitches
import com.routesearch.data.remote.ClimbQuery.Climb as RemoteClimb

internal fun RemoteClimb.toClimb() = Climb(
  id = uuid,
  metadata = getMetadata(),
  name = name,
  pathTokens = pathTokens,
  location = metadata.getLocation(),
  ancestorIds = ancestors,
  description = getDescription(),
  length = length,
  boltCount = boltsCount,
  fa = fa ?: "",
  type = type.typeFragment.toType(),
  grades = grades?.gradesFragment?.toGrade(),
  pitches = pitches.toPitches(),
  media = media?.mapNotNull { it?.mediaFragment?.toMedia() } ?: emptyList(),
)

internal fun LocalClimbWithPitches.toClimb() = Climb(
  id = climb.id,
  metadata = climb.metadata.toMetaData(),
  name = climb.name,
  pathTokens = climb.pathTokens,
  location = climb.location?.toLocation(),
  ancestorIds = climb.ancestorIds,
  description = climb.description.toDescription(),
  length = climb.length,
  boltCount = climb.boltCount,
  fa = climb.fa,
  type = climb.type.toType(),
  grades = climb.grades?.toGrades(),
  pitches = pitches.toPitches(),
  media = climb.media,
)
