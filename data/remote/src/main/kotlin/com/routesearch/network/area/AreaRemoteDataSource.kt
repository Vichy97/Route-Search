package com.routesearch.network.area

import com.routesearch.network.AreaQuery

interface AreaRemoteDataSource {

  suspend fun getArea(id: String): Result<AreaQuery.Area>
}
