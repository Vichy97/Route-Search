package com.routesearch.data.climb

data class Pitch(
  val pitchNumber: Int,
  val description: String,
  val length: Int?,
  val boltCount: Int?,
  val grades: Grades?,
  val type: Type?,
)
