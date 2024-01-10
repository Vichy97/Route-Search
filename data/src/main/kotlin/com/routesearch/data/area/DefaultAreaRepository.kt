package com.routesearch.data.area

import com.routesearch.data.remote.area.AreaRemoteDataSource
import com.routesearch.util.common.result.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultAreaRepository(
  private val remoteDataSource: AreaRemoteDataSource,
  private val coroutineContext: CoroutineContext,
) : AreaRepository {

  override suspend fun getArea(id: String) = withContext(coroutineContext) {
    remoteDataSource.getArea(id)
      .map { it.toArea() }
  }
}
