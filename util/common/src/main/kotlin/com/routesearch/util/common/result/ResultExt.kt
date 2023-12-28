package com.routesearch.util.common.result

import com.routesearch.util.common.error.Error
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun <R, T : R> Result<T>.getOrDefault(defaultValue: R): R = when (this) {
  is Result.Success -> value
  is Result.Failure -> defaultValue
}

fun <T> Result<T>.getOrNull(): T? = when (this) {
  is Result.Success -> value
  is Result.Failure -> null
}

fun <T> Result<T>.errorOrNull(): Error? = when (this) {
  is Result.Success -> null
  is Result.Failure -> error
}

inline fun <R, T> Result<T>.map(transform: (value: T) -> R): Result<R> = when (this) {
  is Result.Success -> Result.success(transform(value))
  is Result.Failure -> Result.failure(error)
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onFailure(action: (error: Error) -> Unit): Result<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }

  (this as? Result.Failure)?.let { action(error) }
  return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }

  (this as? Result.Success)?.let { action(value) }
  return this
}

inline fun <R, T : R> Result<T>.recover(transform: (error: Error) -> Result<R>): Result<R> = when (this) {
  is Result.Success -> Result.success(value)
  is Result.Failure -> transform(error)
}
