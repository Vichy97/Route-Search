package com.routesearch.navigation

import androidx.navigation.NavOptions

sealed class NavEvent {

  data class Navigate(
    val destination: String,
    val navOptions: NavOptions? = null,
  ) : NavEvent()

  data object PopBackstack : NavEvent()
}
