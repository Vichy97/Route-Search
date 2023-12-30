package com.routesearch.data.remote.util

import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.MissingValueException
import com.routesearch.util.common.error.Error
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal fun ApolloException.toError() = when (this) {
  is ApolloNetworkException -> Error.Network
  is MissingValueException -> Error.ApiError.DataNotFound
  else -> Error.ApiError.Unknown
}

internal fun IOException.toError() = when (this) {
  is UnknownHostException, is ConnectException, is SocketTimeoutException -> Error.Network
  else -> throw this
}
