package com.routesearch.data.climb

import com.routesearch.data.media.toMedia
import com.routesearch.data.local.climb.Climb as LocalClimb
import com.routesearch.data.local.climb.Grades as LocalGrades
import com.routesearch.data.remote.AreaQuery.Climb as RemoteClimb
import com.routesearch.data.remote.AreaQuery.Grades as RemoteGrade
import com.routesearch.data.remote.AreaQuery.Type as RemoteType

@JvmName("remoteClimbToClimb")
internal fun List<RemoteClimb?>.toClimbs() = filterNotNull()
  .map { it.toClimb(it.id) }

internal fun RemoteClimb.toClimb(areaId: String) = Climb(
  id = id,
  areaId = areaId,
  grades = grades?.toGrade(),
  name = name,
  type = type.toType(),
  media = media?.toMedia() ?: emptyList(),
)

internal fun RemoteGrade.toGrade() = Grades(
  yds = yds,
  vScale = vscale,
)

internal fun RemoteType.toType() = when {
  aid == true -> Type.AID
  bouldering == true -> Type.BOULDERING
  alpine == true -> Type.ALPINE
  deepwatersolo == true -> Type.DEEP_WATER_SOLO
  ice == true -> Type.ICE
  mixed == true -> Type.MIXED
  sport == true -> Type.SPORT
  snow == true -> Type.SNOW
  tr == true -> Type.TR
  trad == true -> Type.TRAD
  else -> throw IllegalArgumentException("Invalid type $this")
}

internal fun Climb.toLocalClimb() = LocalClimb(
  id = id,
  areaId = areaId,
  name = name,
  type = type.name,
  grades = grades?.toLocalGrades(),
  media = media,
)

internal fun Grades.toLocalGrades() = LocalGrades(
  yds = yds,
  vScale = vScale,
)

@JvmName("localClimbToClimb")
internal fun List<LocalClimb>.toClimbs() = filterNotNull()
  .map { it.toClimb() }

internal fun LocalClimb.toClimb() = Climb(
  id = id,
  areaId = areaId,
  grades = grades?.toGrades(),
  name = name,
  type = Type.valueOf(type),
  media = media,
)

internal fun LocalGrades.toGrades() = Grades(
  yds = yds,
  vScale = vScale,
)
