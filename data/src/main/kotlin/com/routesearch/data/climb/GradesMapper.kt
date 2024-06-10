package com.routesearch.data.climb

import com.routesearch.data.local.climb.Grades as LocalGrades
import com.routesearch.data.remote.fragment.GradesFragment as RemoteGrades

internal fun RemoteGrades.toGrade(
  isBoulder: Boolean,
) = vscale.takeIf { isBoulder } ?: yds

internal fun LocalGrades.toGrade(
  isBoulder: Boolean,
) = vScale.takeIf { isBoulder } ?: yds
