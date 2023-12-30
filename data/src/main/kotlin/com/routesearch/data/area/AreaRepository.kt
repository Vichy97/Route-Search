package com.routesearch.data.area

import com.routesearch.util.common.result.Result

interface AreaRepository {

  suspend fun getArea(id: String): Result<Area>
}
