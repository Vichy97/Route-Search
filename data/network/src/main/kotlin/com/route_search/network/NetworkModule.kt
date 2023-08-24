package com.route_search.network

import com.apollographql.apollo3.ApolloClient
import com.route_search.network.area.AreaApolloDataSource
import com.route_search.network.area.AreaRemoteDataSource
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
