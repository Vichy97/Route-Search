package com.routesearch.data.climb

import com.routesearch.data.local.climb.Pitch as LocalPitch
import com.routesearch.data.remote.ClimbQuery.Pitch as RemotePitch

@JvmName("remotePitchesToPitches")
internal fun List<RemotePitch?>?.toPitches() = this?.mapNotNull {
  it?.toPitch()
} ?: emptyList()

internal fun RemotePitch.toPitch() = Pitch(
  pitchNumber = pitchNumber,
  description = description ?: "",
  length = length,
  boltCount = boltsCount,
  grades = grades?.gradesFragment?.toGrade(),
  type = type?.typeFragment?.toType(),
)

@JvmName("localPitchesToPitches")
internal fun List<LocalPitch>.toPitches() = map { it.toPitch() }

internal fun LocalPitch.toPitch() = Pitch(
  pitchNumber = pitchNumber,
  description = description,
  length = length,
  boltCount = boltCount,
  grades = grades?.toGrades(),
  type = type?.let { Type.valueOf(it) },
)
