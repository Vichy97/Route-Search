package com.routesearch.data.area

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import com.routesearch.data.remote.AreaQuery.Area as RemoteArea

internal fun RemoteArea.getGradeMap() = aggregate?.byGrade?.filterNotNull()
  ?.associate { (it.label ?: "") to (it.count ?: 0) }
  ?.toImmutableMap() ?: persistentMapOf()
