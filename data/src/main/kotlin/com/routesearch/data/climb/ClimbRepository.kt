package com.routesearch.data.climb

interface ClimbRepository {

  suspend fun getClimb(id: String): Result<Climb>
}
