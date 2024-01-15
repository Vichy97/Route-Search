package com.routesearch.data.area

import com.routesearch.util.common.date.toLocalDate
import com.routesearch.data.local.area.Area as LocalArea
import com.routesearch.data.remote.AreaQuery.Area as RemoteArea

internal fun RemoteArea.getMetadata() = Area.Metadata(
  createdAt = (authorMetadata.authorMetadataFragment.createdAt as Long).toLocalDate(),
  updatedAt = (authorMetadata.authorMetadataFragment.updatedAt as Long).toLocalDate(),
)

internal fun LocalArea.MetaData.toMetaData() = Area.Metadata(
  createdAt = createdAt.toLocalDate(),
  updatedAt = updatedAt.toLocalDate(),
)
