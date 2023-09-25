package com.routesearch.data

import com.routesearch.data.local.area.AreaLocalDataSource
import com.routesearch.data.remote.area.AreaRemoteDataSource

internal class DefaultAreaRepository(
  private val remoteDataSource: AreaRemoteDataSource,
  private val localDataSource: AreaLocalDataSource,
) : AreaRepository {

  override suspend fun getArea(id: String) = localDataSource.getArea(id)
    .map { it.toArea() }
    .recoverCatching { getAreaFromRemote(id) }

  private suspend fun getAreaFromRemote(id: String) = remoteDataSource.getArea(id)
    .map { it.toArea() }
    .getOrThrow()
}
