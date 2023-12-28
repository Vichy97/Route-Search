package com.routesearch.data.remote.climb

import com.routesearch.data.remote.ClimbQuery
import com.routesearch.util.common.result.Result

interface ClimbRemoteDataSource {

  suspend fun getClimb(id: String): Result<ClimbQuery.Climb>
}
