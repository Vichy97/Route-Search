package com.routesearch.data.remote.area.search

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.typesense.api.Client
import org.typesense.api.exceptions.TypesenseError
import org.typesense.model.SearchParameters
import java.net.SocketTimeoutException

private const val AreaCollectionName = "areas"

internal class AreaSearchTypeSenseDataSource(
  private val typeSenseClient: Client,
  private val moshi: Moshi,
) : AreaSearchDataSource {

  private val searchParameters = SearchParameters()
    .queryBy("name,pathTokens")
    .sortBy("_text_match:desc,totalClimbs:desc")

  @OptIn(ExperimentalStdlibApi::class)
  override suspend fun searchForAreas(query: String) = try {
    typeSenseClient.collections(AreaCollectionName)
      .documents()
      .search(searchParameters.q(query))
      .hits
      .mapNotNull {
        moshi.adapter<AreaSearchResult>()
          .fromJsonValue(it.document)
      }.let { Result.success(it) }
  } catch (e: TypesenseError) {
    Result.failure(e)
  } catch (e: SocketTimeoutException) {
    Result.failure(e)
  }
}
