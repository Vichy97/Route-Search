package com.routesearch.data.climb

import com.routesearch.data.area.Area
import com.routesearch.data.local.climb.Climb as LocalClimb
import com.routesearch.data.local.climb.Grades as LocalGrades
import com.routesearch.data.remote.fragment.GradesFragment as RemoteGrade
import com.routesearch.data.remote.fragment.TypeFragment as RemoteType

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

internal fun Grades.toLocalGrades() = LocalGrades(
  yds = yds,
  vScale = vScale,
)

@JvmName("localClimbToClimb")
internal fun List<LocalClimb>.toClimbs() = filterNotNull()
  .map { it.toClimb() }

internal fun LocalClimb.toClimb() = Area.Climb(
  id = id,
  grades = grades?.toGrades(),
  name = name,
  type = Type.valueOf(type),
)

internal fun LocalGrades.toGrades() = Grades(
  yds = yds,
  vScale = vScale,
)
