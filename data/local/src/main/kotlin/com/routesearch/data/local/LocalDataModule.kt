package com.routesearch.data.local

import androidx.room.Room
import com.routesearch.data.local.area.AreaDao
import com.routesearch.data.local.area.AreaLocalDataSource
import com.routesearch.data.local.area.AreaRoomDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

  singleOf(::AreaRoomDataSource) bind AreaLocalDataSource::class
}
