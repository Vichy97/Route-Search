package com.routesearch.util.common.result

import com.routesearch.util.common.error.Error

sealed interface Result<T> {

  companion object {

    fun <T> success(value: T) = Success(value)

    fun <T> failure(error: Error) = Failure<T>(error)
  }

  val isSuccess: Boolean
    get() = this !is Failure

  val isFailure: Boolean
    get() = this is Failure

  data class Success<T>(val value: T) : Result<T>

  data class Failure<T>(val error: Error) : Result<T>
}
