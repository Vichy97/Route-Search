package com.routesearch.data.media

import com.routesearch.data.remote.AreaQuery
import com.routesearch.data.remote.AreaQuery.Medium as RemoteAreaMedia

@JvmName("remoteAreaMediaToMedia")
internal fun List<AreaQuery.Medium?>.toMedia() = filterNotNull()
  .map { it.toMedia() }

internal fun RemoteAreaMedia.toMedia() = mediaUrl
