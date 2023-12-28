package com.routesearch.data.climb

import com.routesearch.data.local.climb.Climb as LocalClimb
import com.routesearch.data.remote.ClimbQuery.Climb as RemoteClimb

internal fun RemoteClimb.getDescription() = Climb.Description(
  general = content.description ?: "",
  location = content.location ?: "",
  protection = content.protection ?: "",
)

internal fun LocalClimb.Description.toDescription() = Climb.Description(
  general = general,
  location = location,
  protection = protection,
)
