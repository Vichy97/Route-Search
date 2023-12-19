package com.routesearch.util.coroutines

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val coroutinesModule = module {

  single<CoroutineContext>(MainContext) { Dispatchers.Main }

  single<CoroutineContext>(IoContext) { Dispatchers.IO }

  single<CoroutineContext>(DefaultContext) { Dispatchers.Default }

  single<CoroutineContext>(UnconfinedContext) { Dispatchers.Unconfined }
}
