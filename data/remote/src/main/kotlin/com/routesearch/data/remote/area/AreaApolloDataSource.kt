package com.routesearch.data.remote.area

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.routesearch.data.remote.AreaQuery

internal class AreaApolloDataSource(
  private val apolloClient: ApolloClient,
) : AreaRemoteDataSource {

  override suspend fun getArea(id: String) = try {
    val query = AreaQuery(Optional.present(id))
    val response = apolloClient.query(query).execute().dataAssertNoErrors

    response.area?.let {
      Result.success(it)
    } ?: throw IllegalArgumentException("No area found with id $id")
  } catch (e: ApolloException) {
    Result.failure(e)
  } catch (e: IllegalArgumentException) {
    Result.failure(e)
  }
}
