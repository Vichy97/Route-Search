package com.routesearch.data.climb

import com.routesearch.data.remote.climb.ClimbRemoteDataSource
import com.routesearch.util.common.result.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultClimbRepository(
  private val remoteDataSource: ClimbRemoteDataSource,
  private val coroutineContext: CoroutineContext,
) : ClimbRepository {

  override suspend fun getClimb(id: String) = withContext(coroutineContext) {
    remoteDataSource.getClimb(id)
      .map { it.toClimb() }
  }
}
