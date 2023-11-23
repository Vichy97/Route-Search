package com.routesearch.data.local.area

interface AreaLocalDataSource {
  suspend fun getArea(id: String): Result<AreaWithClimbsAndChildren>

  suspend fun putArea(area: Area): Result<Unit>
}
