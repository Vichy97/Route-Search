package com.routesearch.data.area

interface AreaRepository {

  suspend fun getArea(id: String): Result<Area>
}
