package com.routesearch.data.remote.area

import com.routesearch.data.remote.AreaQuery
import com.routesearch.util.common.result.Result

interface AreaRemoteDataSource {

  suspend fun getArea(id: String): Result<AreaQuery.Area>
}
