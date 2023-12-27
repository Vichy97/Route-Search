package com.routesearch.data.climb

import com.routesearch.data.local.climb.Climb as LocalClimb
import com.routesearch.data.remote.ClimbQuery.Climb as RemoteClimb

internal fun LocalClimb.MetaData.toMetaData() = Climb.Metadata(
  leftRightIndex = leftRightIndex,
  createdAt = createdAt,
  updatedAt = updatedAt,
)

internal fun RemoteClimb.getMetadata() = Climb.Metadata(
  leftRightIndex = metadata.leftRightIndex,
  createdAt = authorMetadata.authorMetadataFragment.createdAt.toString(),
  updatedAt = authorMetadata.authorMetadataFragment.updatedAt.toString(),
)
