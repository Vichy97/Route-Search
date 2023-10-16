package com.routesearch.navigation

import androidx.navigation.NamedNavArgument

interface Destination {

  val route: String

  val arguments: List<NamedNavArgument>
    get() = emptyList()
}
