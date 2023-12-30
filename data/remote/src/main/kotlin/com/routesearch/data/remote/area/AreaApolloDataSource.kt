package com.routesearch.data.remote.area

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.routesearch.data.remote.AreaQuery
import com.routesearch.data.remote.util.toError
import com.routesearch.util.common.error.Error
import com.routesearch.util.common.result.Result
import logcat.LogPriority.ERROR
import logcat.asLog
import logcat.logcat

internal class AreaApolloDataSource(
  private val apolloClient: ApolloClient,
) : AreaRemoteDataSource {

  override suspend fun getArea(id: String) = try {
    val query = AreaQuery(Optional.present(id))
    val response = apolloClient.query(query).execute().dataAssertNoErrors

    requireNotNull(response.area) {
      "No area found with id $id"
    }.let { Result.success(it) }
  } catch (e: ApolloException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(e.toError())
  } catch (e: IllegalArgumentException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(Error.ApiError.DataNotFound)
  }
}
