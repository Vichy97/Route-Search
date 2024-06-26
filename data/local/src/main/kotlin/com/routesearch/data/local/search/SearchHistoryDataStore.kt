package com.routesearch.data.local.search

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import logcat.LogPriority
import logcat.asLog
import logcat.logcat
import java.io.IOException

private const val MaxHistoryLength = 5

internal class SearchHistoryDataStore(
  private val dataStore: DataStore<Preferences>,
) : SearchHistoryDataSource {

  private val searchHistoryKey = stringPreferencesKey("search-history")

  override fun searchHistory() = dataStore.data.map {
    it[searchHistoryKey]?.split(",")?.reversed() ?: emptyList()
  }

  override suspend fun addSearchQuery(query: String) = try {
    writeSearchQueryToPreferences(query)
  } catch (e: IOException) {
    /*
     * Datastore can throw an IOException while editing, but since search history is not all that important, we can
     * catch and log the exception without surfacing to the user.
     */
    logcat(LogPriority.ERROR) { e.asLog() }
  }

  private suspend fun writeSearchQueryToPreferences(query: String) {
    dataStore.edit {
      val currentHistory = it[searchHistoryKey]?.split(",")?.toMutableList() ?: mutableListOf()

      // Move query to top of list if it already exists
      currentHistory.remove(query)
      currentHistory.add(query)

      it[searchHistoryKey] = currentHistory
        .takeLast(MaxHistoryLength)
        .joinToString(",")
    }
  }
}
