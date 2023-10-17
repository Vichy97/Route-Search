package com.routesearch.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface Screen {

  val route: String

  val arguments: List<NamedNavArgument>
    get() = emptyList()

  val deepLinks: List<NavDeepLink>
    get() = emptyList()

  @Composable
  fun Content()
}
