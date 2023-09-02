package com.routesearch.data

import com.routesearch.network.networkModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = networkModule + module {

  singleOf(::DefaultAreaRepository) bind AreaRepository::class
}