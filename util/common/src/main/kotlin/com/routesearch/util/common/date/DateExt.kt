package com.routesearch.util.common.date

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale

fun Long.toLocalDate() = Instant.fromEpochMilliseconds(this)
  .toLocalDateTime(TimeZone.UTC).date

fun LocalDate.toLong() = this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()

fun LocalDate.monthYearFormat() = "${month.shortDisplayName} $year"

val Month.shortDisplayName: String
  get() = getDisplayName(TextStyle.SHORT, Locale.getDefault())
