package com.routesearch.util.coroutines

import org.koin.core.qualifier.StringQualifier

val MainContext = StringQualifier("main-context")
val IoContext = StringQualifier("io-context")
val DefaultContext = StringQualifier("default-context")
val UnconfinedContext = StringQualifier("unconfined-context")
