package com.routesearch.data.location

import com.routesearch.data.local.climb.Location as LocalLocation
import com.routesearch.data.remote.AreaQuery.Metadata as RemoteAreaMetadata
import com.routesearch.data.remote.ClimbQuery.Metadata as RemoteClimbMetadata

internal fun RemoteClimbMetadata.getLocation(): Location? {
  if (lat == null || lng == null) return null

  return Location(
    latitude = lat!!,
    longitude = lng!!,
  )
}

internal fun RemoteAreaMetadata.getLocation() = Location(
  latitude = lat,
  longitude = lng,
)

internal fun LocalLocation.toLocation() = Location(
  latitude = latitude,
  longitude = longitude,
)
