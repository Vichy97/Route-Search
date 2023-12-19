package com.routesearch.data

import com.routesearch.data.area.AreaRepository
import com.routesearch.data.area.DefaultAreaRepository
import com.routesearch.data.local.localDataModule
import com.routesearch.data.remote.remoteDataModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

  includes(
    remoteDataModule,
    localDataModule,
  )

  singleOf(::DefaultAreaRepository) bind AreaRepository::class
}
