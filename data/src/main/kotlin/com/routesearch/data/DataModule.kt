package com.routesearch.data

import com.routesearch.data.area.AreaRepository
import com.routesearch.data.area.DefaultAreaRepository
import com.routesearch.data.area.search.AreaSearchService
import com.routesearch.data.area.search.DefaultAreaSearchService
import com.routesearch.data.local.area.AreaLocalDataSource
import com.routesearch.data.local.localDataModule
import com.routesearch.data.remote.area.AreaRemoteDataSource
import com.routesearch.data.remote.area.search.AreaSearchDataSource
import com.routesearch.data.remote.remoteDataModule
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
      localDataSource = get<AreaLocalDataSource>(),
      coroutineContext = get<CoroutineContext>(IoContext),
    )
  } bind AreaRepository::class

  single {
    DefaultAreaSearchService(
      areasSearchDataSource = get<AreaSearchDataSource>(),
      coroutineContext = get<CoroutineContext>(IoContext),
    )
  } bind AreaSearchService::class
}
