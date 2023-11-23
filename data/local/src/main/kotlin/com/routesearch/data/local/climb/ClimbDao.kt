package com.routesearch.data.local.climb

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

internal interface ClimbDao {

  @Transaction
  @Query("SELECT * FROM climbs")
  suspend fun getAll(): List<Climb?>

  @Transaction
  @Query("SELECT * FROM climbs WHERE id = :id")
  suspend fun getClimbById(id: String): Climb?

  @Insert
  suspend fun insertAllClimbs(climb: List<Climb>)

  @Insert
  suspend fun insertOneClimb(climb: Climb)
}
