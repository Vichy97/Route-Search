package com.route_search.network.area

import com.route_search.network.AreaQuery

interface AreaRemoteDataSource {

  suspend fun getArea(id: String): Result<AreaQuery.Area>
}
