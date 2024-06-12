package com.routesearch.data.media

import com.routesearch.data.remote.fragment.MediaFragment as RemoteMedia

private const val BaseUrl = "https://media.openbeta.io"

internal fun RemoteMedia.toMedia() = "$BaseUrl$mediaUrl"
