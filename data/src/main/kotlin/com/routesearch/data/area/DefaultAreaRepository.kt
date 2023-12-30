package com.routesearch.data.area

import com.routesearch.data.local.area.AreaLocalDataSource
import com.routesearch.data.remote.area.AreaRemoteDataSource
import com.routesearch.util.common.result.map
import com.routesearch.util.common.result.recover
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultAreaRepository(
  private val remoteDataSource: AreaRemoteDataSource,
  private val localDataSource: AreaLocalDataSource,
  private val coroutineContext: CoroutineContext,
) : AreaRepository {

  override suspend fun getArea(id: String) = withContext(coroutineContext) {
    localDataSource.getArea(id)
      .map { it.toArea() }
      .recover { getAreaFromRemote(id) }
  }

  private suspend fun getAreaFromRemote(id: String) = remoteDataSource.getArea(id)
    .map { it.toArea() }
}
