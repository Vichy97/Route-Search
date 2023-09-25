package com.routesearch.data

import com.routesearch.data.remote.remoteDataModule
import com.routesearch.local.localDataModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = remoteDataModule + localDataModule + module {

  singleOf(::DefaultAreaRepository) bind AreaRepository::class
}
