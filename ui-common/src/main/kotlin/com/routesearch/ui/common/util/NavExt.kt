package com.routesearch.ui.common.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.routesearch.ui.common.Screen

fun NavGraphBuilder.screen(
  screen: Screen,
  enterTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
  )? = null,
  exitTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
  )? = null,
  popEnterTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
  )? = enterTransition,
  popExitTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
  )? = exitTransition,
) = composable(
  route = screen.route,
  arguments = screen.arguments,
  deepLinks = screen.deepLinks,
  enterTransition = enterTransition,
  exitTransition = exitTransition,
  popEnterTransition = popEnterTransition,
  popExitTransition = popExitTransition,
) { screen.Content() }

fun NavGraphBuilder.dialog(
  screen: Screen,
  dialogProperties: DialogProperties = DialogProperties(),
) = dialog(
  route = screen.route,
  arguments = screen.arguments,
  deepLinks = screen.deepLinks,
  dialogProperties = dialogProperties,
) {
  screen.Content()
}
