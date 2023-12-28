package com.routesearch.data.remote.util

import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.MissingValueException
import com.routesearch.util.common.error.Error

internal fun ApolloException.toError() = when (this) {
  is ApolloNetworkException -> Error.Network
  is MissingValueException -> Error.ApiError.DataNotFound
  else -> Error.ApiError.Unknown
}
