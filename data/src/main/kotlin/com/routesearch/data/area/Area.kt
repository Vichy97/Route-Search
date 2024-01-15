package com.routesearch.data.area

import com.routesearch.data.climb.Grades
import com.routesearch.data.climb.Type
import com.routesearch.data.location.Location
import kotlinx.datetime.LocalDate

data class Area(
  val id: String,
  val metadata: Metadata,
  val name: String,
  val description: String,
  val path: List<String>,
  val ancestorIds: List<String>,
  val location: Location,
  val totalClimbs: Int,
  val children: List<Child>,
  val climbs: List<Climb>,
  val media: List<String>,
) {

  data class Metadata(
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
  )

  data class Child(
    val id: String,
    val name: String,
    val totalClimbs: Int,
    val numberOfChildren: Int,
  )

  data class Climb(
    val id: String,
    val grades: Grades?,
    val name: String,
    val type: Type,
  )
}
