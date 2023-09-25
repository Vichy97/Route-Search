package com.routesearch.data.area

data class Area(
  val id: String,
  val name: String,
  val description: String,
  val path: List<String>,
  val totalClimbs: Int,
  val children: List<Child>,
) {

  data class Child(
    val id: String,
    val name: String,
    val totalClimbs: Int,
  )
}
