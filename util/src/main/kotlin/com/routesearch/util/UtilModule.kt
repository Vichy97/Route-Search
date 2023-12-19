package com.routesearch.util

import com.routesearch.util.coroutines.coroutinesModule
import org.koin.dsl.module

val utilModule = module {

  includes(coroutinesModule)
}
