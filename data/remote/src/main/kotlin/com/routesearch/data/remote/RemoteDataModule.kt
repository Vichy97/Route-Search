package com.routesearch.data.remote

import com.apollographql.apollo3.ApolloClient
import com.routesearch.data.remote.area.AreaApolloDataSource
import com.routesearch.data.remote.area.AreaRemoteDataSource
import com.routesearch.data.remote.area.search.AreaSearchDataSource
import com.routesearch.data.remote.area.search.AreaSearchTypeSenseDataSource
import com.squareup.moshi.Moshi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration
import org.typesense.api.Client as TypeSenseClient
import org.typesense.api.Configuration as TypeSenseConfiguration
import org.typesense.resources.Node as TypeSenseNode

private const val TypeSenseProtocol = "https"
private const val TypeSensePort = "443"
private val TypeSenseConnectionTimeout = 5.seconds

val remoteDataModule = module {

  single<Moshi> {
    Moshi.Builder()
      .build()
  }

  single<List<TypeSenseNode>> {
    listOf(
      TypeSenseNode(
        TypeSenseProtocol,
        BuildConfig.TYPE_SENSE_HOST,
        TypeSensePort,
      ),
    )
  }

  single<TypeSenseConfiguration> {
    TypeSenseConfiguration(
      get<List<TypeSenseNode>>(),
      TypeSenseConnectionTimeout.toJavaDuration(),
      BuildConfig.TYPE_SENSE_API_KEY,
    )
  }

  singleOf(::TypeSenseClient)

  singleOf(::AreaSearchTypeSenseDataSource) bind AreaSearchDataSource::class

  single<ApolloClient> {
    ApolloClient.Builder()
      .serverUrl(BuildConfig.API_URL)
      .build()
  }

  singleOf(::AreaApolloDataSource) bind AreaRemoteDataSource::class
}
