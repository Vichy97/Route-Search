package com.route_search.data

import com.route_search.network.area.AreaRemoteDataSource

internal class DefaultAreaRepository(
  private val remoteDataSource: AreaRemoteDataSource,
): AreaRepository {

  override suspend fun getArea(id: String) = remoteDataSource.getArea(id)
    .map { it.toArea() }
}
