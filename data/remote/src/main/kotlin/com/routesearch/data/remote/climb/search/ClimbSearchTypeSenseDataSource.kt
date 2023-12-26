package com.routesearch.data.remote.climb.search

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.typesense.api.Client
import org.typesense.api.exceptions.TypesenseError
import org.typesense.model.SearchParameters
import org.typesense.model.SearchResultHit
import java.net.SocketTimeoutException

private const val ClimbCollectionName = "climbs"

internal class ClimbSearchTypeSenseDataSource(
  private val typeSenseClient: Client,
  private val moshi: Moshi,
) : ClimbSearchDataSource {

  private val searchParameters = SearchParameters()
    .queryBy("climbName")
    .sortBy("_text_match:desc")

  override suspend fun searchForClimbs(query: String) = try {
    typeSenseClient.collections(ClimbCollectionName)
      .documents()
      .search(searchParameters.q(query))
      .hits
      .toSearchResults()
      .let { Result.success(it) }
  } catch (e: TypesenseError) {
    Result.failure(e)
  } catch (e: SocketTimeoutException) {
    Result.failure(e)
  }

  private fun List<SearchResultHit>.toSearchResults() = mapNotNull { it.toSearchResult() }

  @OptIn(ExperimentalStdlibApi::class)
  private fun SearchResultHit.toSearchResult() = moshi.adapter<ClimbSearchResult>()
    .fromJsonValue(document)
}
