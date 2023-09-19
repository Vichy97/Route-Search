package com.routesearch.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.routesearch.local.area.Area
import com.routesearch.local.area.AreaDao
import com.routesearch.local.area.Child

@Database(
  entities = [Area::class, Child::class],
  version = 0,
  exportSchema = false,
)
@TypeConverters(CommonTypeConverters::class)
internal abstract class RouteSearchDataBase : RoomDatabase() {

  abstract val areaDao: AreaDao
}
