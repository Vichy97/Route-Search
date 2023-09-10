package com.routesearch.data

import com.routesearch.network.area.AreaRemoteDataSource

internal class DefaultAreaRepository(
  private val remoteDataSource: AreaRemoteDataSource,
) : AreaRepository {

  override suspend fun getArea(id: String) = remoteDataSource.getArea(id)
    .map { it.toArea() }
}
