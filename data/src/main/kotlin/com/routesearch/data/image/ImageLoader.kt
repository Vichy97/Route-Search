package com.routesearch.data.image

interface ImageLoader {
  suspend fun load(url: String)
}
