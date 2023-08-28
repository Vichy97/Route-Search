package com.routesearch.network.area

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.routesearch.network.AreaQuery

internal class AreaApolloDataSource(
  private val apolloClient: ApolloClient,
) : AreaRemoteDataSource {

  override suspend fun getArea(id: String) = try {
    val query = AreaQuery(Optional.present(id))
    val response = apolloClient.query(query).execute().dataAssertNoErrors

    Result.success(response.area!!)
  } catch (e: Exception) {
    Result.failure(e)
  }
}
