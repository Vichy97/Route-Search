package com.routesearch.navigation

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val navigationModule = module {

  singleOf(::Navigator)
}
