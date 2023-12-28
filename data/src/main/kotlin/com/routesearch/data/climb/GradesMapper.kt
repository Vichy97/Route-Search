package com.routesearch.data.climb

import com.routesearch.data.local.climb.Grades as LocalGrades
import com.routesearch.data.remote.fragment.GradesFragment as RemoteGrades

internal fun RemoteGrades.toGrade() = Grades(
  yds = yds,
  vScale = vscale,
)

internal fun LocalGrades.toGrades() = Grades(
  yds = yds,
  vScale = vScale,
)
