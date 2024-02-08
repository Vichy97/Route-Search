package com.routesearch.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.routesearch.data.local.area.AreaDao
import com.routesearch.data.local.area.AreaLocalDataSource
import com.routesearch.data.local.area.AreaRoomDataSource
import com.routesearch.data.local.search.SearchHistoryDataSource
import com.routesearch.data.local.search.SearchHistoryDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

val localDataModule = module {

  single<String>(DatabaseName) { "route_search_database" }

  single<RouteSearchDataBase> {
    Room.databaseBuilder(
      context = androidContext(),
      klass = RouteSearchDataBase::class.java,
      name = get<String>(DatabaseName),
    ).build()
  }

  single<AreaDao> { get<RouteSearchDataBase>().areaDao }

  singleOf(::AreaRoomDataSource) bind AreaLocalDataSource::class

  single<String>(PreferencesName) { "route_search_preferences" }

  single<File>(PreferencesFile) {
    get<Context>().preferencesDataStoreFile(get<String>(PreferencesName))
  }

  single<DataStore<Preferences>> {
    PreferenceDataStoreFactory.create { get<File>(PreferencesFile) }
  }

  singleOf(::SearchHistoryDataStore) bind SearchHistoryDataSource::class
}
