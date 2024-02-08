package com.routesearch.data

import com.routesearch.data.area.AreaRepository
import com.routesearch.data.area.DefaultAreaRepository
import com.routesearch.data.climb.ClimbRepository
import com.routesearch.data.climb.DefaultClimbRepository
import com.routesearch.data.local.localDataModule
import com.routesearch.data.local.search.SearchHistoryDataSource
import com.routesearch.data.remote.area.AreaRemoteDataSource
import com.routesearch.data.remote.area.search.AreaSearchDataSource
import com.routesearch.data.remote.climb.ClimbRemoteDataSource
import com.routesearch.data.remote.climb.search.ClimbSearchDataSource
import com.routesearch.data.remote.remoteDataModule
import com.routesearch.data.search.DefaultSearchHistoryRepository
import com.routesearch.data.search.DefaultSearchService
import com.routesearch.data.search.SearchHistoryRepository
import com.routesearch.data.search.SearchService
import com.routesearch.util.coroutines.IoContext
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dataModule = module {

  includes(
    remoteDataModule,
    localDataModule,
  )

  single {
    DefaultAreaRepository(
      remoteDataSource = get<AreaRemoteDataSource>(),
      coroutineContext = get<CoroutineContext>(IoContext),
    )
  } bind AreaRepository::class

  single {
    DefaultSearchService(
      areaSearchDataSource = get<AreaSearchDataSource>(),
      climbSearchDataSource = get<ClimbSearchDataSource>(),
      coroutineContext = get<CoroutineContext>(IoContext),
    )
  } bind SearchService::class

  single {
    DefaultClimbRepository(
      remoteDataSource = get<ClimbRemoteDataSource>(),
      coroutineContext = get<CoroutineContext>(IoContext),
    )
  } bind ClimbRepository::class

  single {
    DefaultSearchHistoryRepository(
      dataSource = get<SearchHistoryDataSource>(),
      coroutineContext = get<CoroutineContext>(IoContext),
    )
  } bind SearchHistoryRepository::class
}
