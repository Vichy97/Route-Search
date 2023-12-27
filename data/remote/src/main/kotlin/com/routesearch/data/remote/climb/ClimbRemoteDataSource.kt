package com.routesearch.data.remote.climb

import com.routesearch.data.remote.ClimbQuery

interface ClimbRemoteDataSource {

  suspend fun getClimb(id: String): Result<ClimbQuery.Climb>
}
