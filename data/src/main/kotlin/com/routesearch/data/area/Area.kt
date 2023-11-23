package com.routesearch.data.area

import com.routesearch.data.climb.Climb

data class Area(
  val id: String,
  val name: String,
  val description: String,
  val path: List<String>,
  val totalClimbs: Int,
  val children: List<Child>,
  val climbs: List<Climb>,
  val media: List<String>,
) {

  data class Child(
    val id: String,
    val name: String,
    val totalClimbs: Int,
    val numberOfChildren: Int,
  )
}
