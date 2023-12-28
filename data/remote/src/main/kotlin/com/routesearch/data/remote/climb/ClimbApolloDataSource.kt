package com.routesearch.data.remote.climb

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.routesearch.data.remote.ClimbQuery
import com.routesearch.data.remote.util.toError
import com.routesearch.util.common.error.Error
import com.routesearch.util.common.result.Result
import logcat.LogPriority.ERROR
import logcat.asLog
import logcat.logcat

internal class ClimbApolloDataSource(
  private val apolloClient: ApolloClient,
) : ClimbRemoteDataSource {

  override suspend fun getClimb(id: String) = try {
    val query = ClimbQuery(Optional.present(id))
    val response = apolloClient.query(query).execute().dataAssertNoErrors

    requireNotNull(response.climb) {
      "No climb found with id $id"
    }.let { Result.success(it) }
  } catch (e: ApolloException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(e.toError())
  } catch (e: IllegalArgumentException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(Error.ApiError.DataNotFound)
  }
}
