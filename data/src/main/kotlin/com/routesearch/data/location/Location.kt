package com.routesearch.data.location

import androidx.compose.runtime.Immutable
import com.routesearch.util.common.numbers.format

private const val FormatPattern = "####.00000"

@Immutable
data class Location(
  val latitude: Double,
  val longitude: Double,
) {

  val formattedLatitude = latitude.format(FormatPattern)

  val formattedLongitude = longitude.format(FormatPattern)

  val displayString = "$formattedLatitude, $formattedLongitude"
}
