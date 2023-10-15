package com.routesearch.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

  private val _navEvents = MutableSharedFlow<NavEvent>(extraBufferCapacity = 1)
  val navEvents = _navEvents.asSharedFlow()

  fun navigate(
    destination: Destination,
  ) {
    val navEvent = NavEvent.Navigate(
      destination = destination.route,
    )
    _navEvents.tryEmit(navEvent)
  }

  fun navigate(
    destination: Destination,
    builder: NavOptionsBuilder.() -> Unit,
  ) {
    val navEvent = NavEvent.Navigate(
      destination = destination.route,
      navOptions = navOptions(builder),
    )
    _navEvents.tryEmit(navEvent)
  }

  fun popBackStack() {
    _navEvents.tryEmit(NavEvent.PopBackstack)
  }
}
