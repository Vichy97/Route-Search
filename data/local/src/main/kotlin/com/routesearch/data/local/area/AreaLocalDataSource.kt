package com.routesearch.data.local.area

import com.routesearch.util.common.result.Result

interface AreaLocalDataSource {

  suspend fun getArea(id: String): Result<AreaWithClimbsAndChildren>

  suspend fun putArea(area: Area): Result<Unit>
}
