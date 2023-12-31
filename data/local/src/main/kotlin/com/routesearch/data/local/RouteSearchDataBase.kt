package com.routesearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.routesearch.data.local.area.Area
import com.routesearch.data.local.area.AreaDao
import com.routesearch.data.local.area.Child
import com.routesearch.data.local.climb.Climb

@Database(
  entities = [
    Area::class,
    Child::class,
    Climb::class,
  ],
  version = 1,
  exportSchema = false,
)
@TypeConverters(CommonTypeConverters::class)
internal abstract class RouteSearchDataBase : RoomDatabase() {

  abstract val areaDao: AreaDao
}
