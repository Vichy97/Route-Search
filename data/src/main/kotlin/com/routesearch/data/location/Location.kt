package com.routesearch.data.location

import com.routesearch.util.common.numbers.format

private const val FormatPattern = "####.00000"

data class Location(
  val latitude: Double,
  val longitude: Double,
) {

  val formattedLatitude = latitude.format(FormatPattern)

  val formattedLongitude = longitude.format(FormatPattern)

  val displayString = "$formattedLatitude, $formattedLongitude"
}
