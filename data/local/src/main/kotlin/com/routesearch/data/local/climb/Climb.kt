package com.routesearch.data.local.climb

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "climbs")
data class Climb(
  @PrimaryKey
  val id: String,
  val areaId: String,
  val name: String,
  val type: String,
  @Embedded
  val grades: Grades?,
  val media: List<String>,
)

data class Grades(
  val yds: String?,
  val vScale: String?,
)
