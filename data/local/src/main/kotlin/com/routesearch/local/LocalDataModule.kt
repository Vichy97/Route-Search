package com.routesearch.local

import androidx.room.Room
import com.routesearch.local.area.AreaDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {

  single<RouteSearchDataBase> {
    Room.databaseBuilder(
      context = androidContext(),
      klass = RouteSearchDataBase::class.java,
      name = "route_search_database",
    ).build()
  }

  single<AreaDao> {
    get<RouteSearchDataBase>().areaDao
  }
}
