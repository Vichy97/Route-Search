package com.routesearch.data.area

import com.routesearch.data.remote.AreaQuery.Area as RemoteArea

internal fun RemoteArea.getGradeMap() = aggregate?.byGrade?.filterNotNull()
  ?.associate { (it.label ?: "") to (it.count ?: 0) } ?: emptyMap()
