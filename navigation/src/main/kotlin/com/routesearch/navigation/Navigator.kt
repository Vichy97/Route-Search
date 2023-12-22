package com.routesearch.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

  private val _navEvents = MutableSharedFlow<NavEvent>(extraBufferCapacity = 1)
  val navEvents = _navEvents.asSharedFlow()

  fun navigate(
    destination: String,
  ) {
    val navEvent = NavEvent.Navigate(
      destination = destination,
    )
    _navEvents.tryEmit(navEvent)
  }

  fun navigate(
    destination: String,
    builder: NavOptionsBuilder.() -> Unit,
  ) {
    val navEvent = NavEvent.Navigate(
      destination = destination,
      navOptions = navOptions(builder),
    )
    _navEvents.tryEmit(navEvent)
  }

  fun popBackStack() {
    _navEvents.tryEmit(NavEvent.PopBackstack)
  }
}
