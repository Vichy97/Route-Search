package com.routesearch.data.climb

import com.routesearch.util.common.result.Result

interface ClimbRepository {

  suspend fun getClimb(id: String): Result<Climb>
}
