package com.routesearch.data.local.climb

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pitches")
data class Pitch(
  @PrimaryKey(autoGenerate = true)
  val id: String,
  val climbId: String,
  val pitchNumber: Int,
  val description: String,
  val length: Int?,
  val boltCount: Int?,
  @Embedded
  val grades: Grades?,
  val type: List<String>?,
)
