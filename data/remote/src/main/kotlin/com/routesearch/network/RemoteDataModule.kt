package com.routesearch.network

import com.apollographql.apollo3.ApolloClient
import com.routesearch.network.area.AreaApolloDataSource
import com.routesearch.network.area.AreaRemoteDataSource
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

  single<ApolloClient> {
    ApolloClient.Builder()
      .serverUrl(BuildConfig.API_URL)
      .build()
  }

  singleOf(::AreaApolloDataSource) bind AreaRemoteDataSource::class
}
