package com.routesearch.data.local.area

import android.database.SQLException
import com.routesearch.util.common.error.Error
import com.routesearch.util.common.result.Result
import logcat.LogPriority.ERROR
import logcat.asLog
import logcat.logcat

internal class AreaRoomDataSource(
  private val areaDao: AreaDao,
) : AreaLocalDataSource {

  override suspend fun getArea(id: String) = try {
    requireNotNull(areaDao.getAreaById(id)) {
      "No area found for id $id"
    }.let { Result.success(it) }
  } catch (e: IllegalArgumentException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(Error.DbError.DataNotFound)
  } catch (e: SQLException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(Error.DbError.ReadError)
  }

  override suspend fun putArea(area: Area) = try {
    areaDao.insertOneArea(area)

    Result.success(Unit)
  } catch (e: SQLException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(Error.DbError.WriteError)
  }
}
