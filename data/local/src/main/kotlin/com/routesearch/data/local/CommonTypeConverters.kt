package com.routesearch.data.local

import androidx.room.TypeConverter

internal class CommonTypeConverters {

  @TypeConverter
  fun listToString(list: List<String>): String = list.joinToString(",")

  @TypeConverter
  fun stringToList(string: String): List<String> = string.split(',')
}
