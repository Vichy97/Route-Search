package com.routesearch.network

import com.apollographql.apollo3.ApolloClient
import com.routesearch.network.area.AreaApolloDataSource
import com.routesearch.network.area.AreaRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {

  single<ApolloClient> {
    ApolloClient.Builder()
      .serverUrl(BuildConfig.API_URL)
      .build()
  }
  singleOf(::AreaApolloDataSource) bind AreaRemoteDataSource::class
}