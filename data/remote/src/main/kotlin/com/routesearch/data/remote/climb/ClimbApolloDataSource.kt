package com.routesearch.data.remote.climb

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.routesearch.data.remote.ClimbQuery

internal class ClimbApolloDataSource(
  private val apolloClient: ApolloClient,
) : ClimbRemoteDataSource {

  override suspend fun getClimb(id: String) = try {
    val query = ClimbQuery(Optional.present(id))
    val response = apolloClient.query(query).execute().dataAssertNoErrors

    response.climb?.let {
      Result.success(it)
    } ?: throw IllegalArgumentException("No climb found with id $id")
  } catch (e: ApolloException) {
    Result.failure(e)
  } catch (e: IllegalArgumentException) {
    Result.failure(e)
  }
}
