package com.routesearch.data.local.climb

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "climbs")
data class Climb(
  @PrimaryKey
  val id: String,
  val areaId: String,
  @Embedded
  val metadata: MetaData,
  val name: String,
  val pathTokens: List<String>,
  @Embedded
  val location: Location?,
  val ancestorIds: List<String>,
  @Embedded
  val description: Description,
  val length: Int,
  val boltCount: Int?,
  val fa: String,
  val type: String,
  @Embedded
  val grades: Grades?,
  val media: List<String>,
) {

  data class MetaData(
    val leftRightIndex: Int?,
    val createdAt: String,
    val updatedAt: String,
  )

  data class Description(
    val general: String,
    val location: String,
    val protection: String,
  )
}

data class Grades(
  val yds: String?,
  val vScale: String?,
)

data class Location(
  val latitude: Double,
  val longitude: Double,
)

data class ClimbWithPitches(
  @Embedded
  val climb: Climb,
  @Relation(
    entity = Pitch::class,
    parentColumn = "id",
    entityColumn = "climbId",
  )
  val pitches: List<Pitch>,
)
