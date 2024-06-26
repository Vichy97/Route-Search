package com.routesearch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.navgraphs.RootNavGraph
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
      val isResumed = navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
      if (!isResumed) {
        return@onEach
      }
      when (it) {
        is NavEvent.Navigate -> navController.navigate(
          route = it.destination,
          navOptions = it.navOptions,
        )
        is NavEvent.PopBackstack -> navController.popBackStack()
      }
    }.launchIn(this)
  }
  DestinationsNavHost(
    modifier = modifier,
    navGraph = RootNavGraph,
    navController = navController,
  )
}
