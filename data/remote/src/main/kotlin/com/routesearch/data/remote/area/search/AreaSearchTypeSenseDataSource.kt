package com.routesearch.data.remote.area.search

import com.routesearch.data.remote.util.toError
import com.routesearch.util.common.error.Error
import com.routesearch.util.common.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import logcat.LogPriority.ERROR
import logcat.asLog
import logcat.logcat
import org.typesense.api.Client
import org.typesense.api.exceptions.TypesenseError
import org.typesense.model.SearchParameters
import org.typesense.model.SearchResultHit
import java.io.IOException

private const val AreaCollectionName = "areas"

internal class AreaSearchTypeSenseDataSource(
  private val typeSenseClient: Client,
  private val moshi: Moshi,
) : AreaSearchDataSource {

  private val searchParameters = SearchParameters()
    .queryBy("name")
    .sortBy("_text_match:desc,totalClimbs:desc")

  override suspend fun searchForAreas(query: String) = try {
    typeSenseClient.collections(AreaCollectionName)
      .documents()
      .search(searchParameters.q(query))
      .hits
      .mapNotNull { it.toSearchResult() }
      .let { Result.success(it) }
  } catch (e: TypesenseError) {
    logcat(ERROR) { e.asLog() }

    Result.failure(Error.ApiError.Unknown)
  } catch (e: IOException) {
    logcat(ERROR) { e.asLog() }

    Result.failure(e.toError())
  }

  @OptIn(ExperimentalStdlibApi::class)
  private fun SearchResultHit.toSearchResult() = moshi.adapter<AreaSearchResult>()
    .fromJsonValue(document)
}
