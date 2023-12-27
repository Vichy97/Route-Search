package com.routesearch.data.remote.climb.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClimbSearchResult(
  @Json(name = "id")
  val id: String,
  @Json(name = "climbName")
  val name: String,
)
