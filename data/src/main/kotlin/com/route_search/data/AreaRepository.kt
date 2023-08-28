package com.routesearch.data

interface AreaRepository {

  suspend fun getArea(id: String): Result<Area>
}