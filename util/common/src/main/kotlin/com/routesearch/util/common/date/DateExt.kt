package com.routesearch.util.common.date

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun Long.toLocalDate() = Instant.fromEpochMilliseconds(this)
  .toLocalDateTime(TimeZone.UTC).date

fun LocalDate.toLong() = this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
