package com.route_search.data

interface AreaRepository {

  suspend fun getArea(id: String): Result<Area>
}