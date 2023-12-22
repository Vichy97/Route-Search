package com.routesearch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.routesearch.features.area.AreaScreen
import com.routesearch.ui.common.util.screen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject

@Composable
fun MainNavGraph(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  navigator: Navigator = koinInject(),
) {
  LaunchedEffect("navigation") {
    navigator.navEvents.onEach {
      when (it) {
        is NavEvent.Navigate -> navController.navigate(
          route = it.destination,
          navOptions = it.navOptions,
        )
        is NavEvent.PopBackstack -> navController.popBackStack()
      }
    }.launchIn(this)
  }
  NavHost(
    navController = navController,
    startDestination = AreaScreen.route,
    modifier = modifier,
  ) {
    screen(AreaScreen)
  }
}
