package com.routesearch.data.remote.area

import com.routesearch.data.remote.AreaQuery

interface AreaRemoteDataSource {

  suspend fun getArea(id: String): Result<AreaQuery.Area>
}
