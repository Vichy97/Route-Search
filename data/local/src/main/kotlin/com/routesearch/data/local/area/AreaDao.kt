package com.routesearch.data.local.area

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
internal interface AreaDao {

  @Transaction
  @Query("SELECT * FROM areas")
  suspend fun getAll(): List<AreaWithChildren?>

  @Transaction
  @Query("SELECT * FROM areas WHERE id = :areaId")
  suspend fun getAreaById(areaId: String): AreaWithChildren?

  @Insert
  suspend fun insertAllAreas(area: List<Area>)

  @Insert
  suspend fun insertOneArea(area: Area)

  @Insert
  suspend fun insertChildren(children: List<Child>)
}
