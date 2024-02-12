package com.routesearch.data.area

import androidx.compose.runtime.Immutable
import com.routesearch.data.climb.Grades
import com.routesearch.data.climb.Type
import com.routesearch.data.location.Location
import kotlinx.datetime.LocalDate

@Immutable
data class Area(
  val id: String,
  val metadata: Metadata,
  val name: String,
  val description: String,
  val path: List<String>,
  val ancestorIds: List<String>,
  val gradeMap: Map<String, Int>,
  val location: Location,
  val climbCount: ClimbCount,
  val children: List<Child>,
  val climbs: List<Climb>,
  val organizations: List<Organization>,
  val media: List<String>,
) {

  @Immutable
  data class ClimbCount(
    val total: Int,
    val sport: Int,
    val trad: Int,
    val tr: Int,
    val bouldering: Int,
  ) {
    val roped = sport + trad + tr
  }

  @Immutable
  data class Metadata(
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
    val isLeaf: Boolean,
  )

  @Immutable
  data class Child(
    val id: String,
    val name: String,
    val totalClimbs: Int,
    val numberOfChildren: Int,
  )

  @Immutable
  data class Climb(
    val id: String,
    val grades: Grades?,
    val name: String,
    val type: Type,
    val numberOfPitches: Int,
  )

  @Immutable
  data class Organization(
    val id: String,
    val name: String,
    val website: String?,
    val description: String?,
    val facebookUrl: String?,
    val instagramUrl: String?,
  )
}
