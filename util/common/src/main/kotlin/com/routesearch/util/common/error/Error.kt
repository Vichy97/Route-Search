package com.routesearch.util.common.error

sealed interface Error {

  data object Network : Error

  sealed interface DbError : Error {

    data object DataNotFound : DbError

    data object WriteError : DbError

    data object ReadError : DbError
  }

  sealed interface ApiError : Error {

    data object DataNotFound : ApiError

    data object Unknown : ApiError
  }
}
