package com.routesearch.data.remote.climb.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClimbSearchResult(
  @Json(name = "climbUUID")
  val id: String,
  @Json(name = "climbName")
  val name: String,
  @Json(name = "areaNames")
  val pathTokens: List<String>,
  @Json(name = "grade")
  val grade: String,
  @Json(name = "disciplines")
  val types: List<String>,
)
