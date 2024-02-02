package com.routesearch.data.area

import com.routesearch.data.local.area.Area as LocalArea
import com.routesearch.data.remote.AreaQuery.Area as RemoteArea

internal fun RemoteArea.getClimbCount() = Area.ClimbCount(
  total = totalClimbs,
  sport = aggregate?.byDiscipline?.sport?.total ?: 0,
  trad = aggregate?.byDiscipline?.trad?.total ?: 0,
  tr = aggregate?.byDiscipline?.tr?.total ?: 0,
  bouldering = aggregate?.byDiscipline?.bouldering?.total ?: 0,
)

internal fun LocalArea.ClimbCount.toClimbCount() = Area.ClimbCount(
  total = total,
  sport = sport,
  trad = trad,
  tr = tr,
  bouldering = bouldering,
)
