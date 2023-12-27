package com.routesearch.data.remote

import com.apollographql.apollo3.ApolloClient
import com.routesearch.data.remote.area.AreaApolloDataSource
import com.routesearch.data.remote.area.AreaRemoteDataSource
import com.routesearch.data.remote.area.search.AreaSearchDataSource
import com.routesearch.data.remote.area.search.AreaSearchTypeSenseDataSource
import com.routesearch.data.remote.climb.ClimbApolloDataSource
import com.routesearch.data.remote.climb.ClimbRemoteDataSource
import com.routesearch.data.remote.climb.search.ClimbSearchDataSource
import com.routesearch.data.remote.climb.search.ClimbSearchTypeSenseDataSource
import com.squareup.moshi.Moshi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration
import org.typesense.api.Client as TypeSenseClient
import org.typesense.api.Configuration as TypeSenseConfiguration
import org.typesense.resources.Node as TypeSenseNode

val remoteDataModule = module {

  single<Duration>(TypeSenseTimeout) { 5.seconds.toJavaDuration() }

  single<String>(TypeSensePort) { "443" }

  single<String>(TypeSenseProtocol) { "https" }

  single<String>(TypeSenseApiKey) { BuildConfig.TYPE_SENSE_API_KEY }

  single<String>(TypeSenseApiUrl) { BuildConfig.API_URL }

  single<String>(TypeSenseHost) { BuildConfig.TYPE_SENSE_HOST }

  single<Moshi> {
    Moshi.Builder()
      .build()
  }

  single<TypeSenseNode> {
    TypeSenseNode(
      get<String>(TypeSenseProtocol),
      get<String>(TypeSenseHost),
      get<String>(TypeSensePort),
    )
  }

  single<List<TypeSenseNode>>(TypeSenseNodes) {
    getAll<TypeSenseNode>()
  }

  single<TypeSenseConfiguration> {
    TypeSenseConfiguration(
      getAll<TypeSenseNode>(),
      get<Duration>(TypeSenseTimeout),
      get<String>(TypeSenseApiKey),
    )
  }

  singleOf(::TypeSenseClient)

  singleOf(::AreaSearchTypeSenseDataSource) bind AreaSearchDataSource::class

  single<ApolloClient> {
    ApolloClient.Builder()
      .serverUrl(get<String>(TypeSenseApiUrl))
      .build()
  }

  singleOf(::AreaApolloDataSource) bind AreaRemoteDataSource::class

  singleOf(::AreaSearchTypeSenseDataSource) bind AreaSearchDataSource::class

  singleOf(::ClimbSearchTypeSenseDataSource) bind ClimbSearchDataSource::class

  singleOf(::ClimbApolloDataSource) bind ClimbRemoteDataSource::class
}
