package com.routesearch.data.climb

data class Climb(
  val id: String,
  val areaId: String,
  val grades: Grades?,
  val name: String,
  val type: Type,
  val media: List<String>,
)
