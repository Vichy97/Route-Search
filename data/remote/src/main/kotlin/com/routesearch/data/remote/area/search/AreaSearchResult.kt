package com.routesearch.data.remote.area.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AreaSearchResult(
  @Json(name = "areaUUID")
  val id: String,
  @Json(name = "name")
  val name: String,
  @Json(name = "pathTokens")
  val pathTokens: List<String>,
  @Json(name = "totalClimbs")
  val totalClimbs: Int,
)
