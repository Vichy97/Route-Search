package com.routesearch.data.local.area

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "areas")
data class Area(
  @PrimaryKey val id: String,
  val name: String,
  val description: String,
  val path: List<String>,
  val totalClimbs: Int,
)

@Entity(tableName = "children")
data class Child(
  @PrimaryKey val id: String,
  val areaId: String,
  val name: String,
  val totalClimbs: Int,
)

data class AreaWithChildren(
  @Embedded val area: Area,
  @Relation(
    parentColumn = "id",
    entityColumn = "areaId",
  )
  val children: List<Child>,
)
