package com.routesearch.data.media

import com.routesearch.data.remote.AreaQuery
import com.routesearch.data.remote.AreaQuery.Medium as RemoteClimbMedia
import com.routesearch.data.remote.AreaQuery.Medium1 as RemoteAreaMedia

@JvmName("remoteAreaMediaToMedia")
internal fun List<AreaQuery.Medium1?>.toMedia() = filterNotNull()
  .map { it.toMedia() }

internal fun RemoteAreaMedia.toMedia() = mediaUrl

@JvmName("remoteClimbMediaToMedia")
internal fun List<AreaQuery.Medium?>.toMedia() = filterNotNull()
  .map { it.toMedia() }

internal fun RemoteClimbMedia.toMedia() = mediaUrl
