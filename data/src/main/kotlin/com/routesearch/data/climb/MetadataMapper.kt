package com.routesearch.data.climb

import com.routesearch.util.common.date.toLocalDate
import com.routesearch.data.local.climb.Climb as LocalClimb
import com.routesearch.data.remote.ClimbQuery.Climb as RemoteClimb

internal fun LocalClimb.MetaData.toMetaData() = Climb.Metadata(
  leftRightIndex = leftRightIndex,
  createdAt = createdAt.toLocalDate(),
  updatedAt = updatedAt.toLocalDate(),
)

internal fun RemoteClimb.getMetadata() = Climb.Metadata(
  leftRightIndex = metadata.leftRightIndex,
  createdAt = (authorMetadata.authorMetadataFragment.createdAt as Long).toLocalDate(),
  updatedAt = (authorMetadata.authorMetadataFragment.updatedAt as Long).toLocalDate(),
)
