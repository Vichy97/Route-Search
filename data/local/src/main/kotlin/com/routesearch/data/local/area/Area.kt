package com.routesearch.data.local.area

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.routesearch.data.local.climb.Climb
import com.routesearch.data.local.climb.Grades
import com.routesearch.data.local.climb.Location

@Entity(tableName = "areas")
data class Area(
  @PrimaryKey val id: String,
  @Embedded
  val metadata: MetaData,
  val name: String,
  val description: String,
  val path: List<String>,
  val ancestorIds: List<String>,
  @Embedded
  val location: Location,
  val totalClimbs: Int,
  val media: List<String>,
) {

  data class MetaData(
    val createdAt: Long,
    val updatedAt: Long,
  )

  data class Climb(
    val id: String,
    val name: String,
    @Embedded
    val grades: Grades,
    val type: String,
  )
}

@Entity(tableName = "area_children")
data class Child(
  @PrimaryKey val id: String,
  val areaId: String,
  val name: String,
  val totalClimbs: Int,
  val numberOfChildren: Int,
)

data class AreaWithClimbsAndChildren(
  @Embedded
  val area: Area,
  @Relation(
    parentColumn = "id",
    entityColumn = "areaId",
  )
  val children: List<Child>,
  @Relation(
    entity = Climb::class,
    parentColumn = "id",
    entityColumn = "areaId",
  )
  val climbs: List<Area.Climb>,
)
