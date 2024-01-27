package com.routesearch.data.local

import androidx.room.TypeConverter

internal class CommonTypeConverters {

  @TypeConverter
  fun listToString(list: List<String>): String = list.joinToString(",")

  @TypeConverter
  fun stringToList(string: String): List<String> = string.split(',')

  @TypeConverter
  fun stringIntMapToString(map: Map<String, Int>) = map.entries.joinToString(
    separator = ",",
  ) {
    "${it.key}=${it.value}"
  }

  @TypeConverter
  fun stringToIntStringMap(string: String) = string.split(",")
    .associate {
      val (left, right) = it.split("=")
      left to right.toInt()
    }
}
