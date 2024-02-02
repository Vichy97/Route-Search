package com.routesearch.features.common.intent

import android.content.Intent
import com.routesearch.data.location.Location

class GeoIntent(
  location: Location,
  name: String,
) : Intent(
  parseUri(
    "geo:0,0?q=${location.latitude},${location.longitude}($name)",
    URI_ALLOW_UNSAFE,
  ).addFlags(FLAG_ACTIVITY_NEW_TASK),
)
