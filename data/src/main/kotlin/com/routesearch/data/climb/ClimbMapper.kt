package com.routesearch.data.climb

import com.routesearch.data.location.getLocation
import com.routesearch.data.location.toLocation
import com.routesearch.data.media.toMedia
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.routesearch.data.local.climb.ClimbWithPitches as LocalClimbWithPitches
import com.routesearch.data.remote.ClimbQuery.Climb as RemoteClimb

internal fun RemoteClimb.toClimb() = Climb(
  id = uuid,
  metadata = getMetadata(),
  name = name,
  pathTokens = pathTokens.toImmutableList(),
  location = metadata.getLocation(),
  ancestorIds = ancestors.toImmutableList(),
  description = getDescription(),
  length = length,
  boltCount = boltsCount,
  fa = fa ?: "",
  type = type.typeFragment.toType(),
  grades = grades?.gradesFragment?.toGrade(),
  pitches = pitches.toPitches(),
  media = media?.mapNotNull { it?.mediaFragment?.toMedia() }?.toImmutableList() ?: persistentListOf(),
)

internal fun LocalClimbWithPitches.toClimb() = Climb(
  id = climb.id,
  metadata = climb.metadata.toMetaData(),
  name = climb.name,
  pathTokens = climb.pathTokens.toImmutableList(),
  location = climb.location?.toLocation(),
  ancestorIds = climb.ancestorIds.toImmutableList(),
  description = climb.description.toDescription(),
  length = climb.length,
  boltCount = climb.boltCount,
  fa = climb.fa,
  type = climb.type.toType(),
  grades = climb.grades?.toGrades(),
  pitches = pitches.toPitches(),
  media = climb.media.toImmutableList(),
)
