package com.routesearch.features.area

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.routesearch.features.destinations.GalleryScreenDestination
import com.routesearch.features.destinations.SearchScreenDestination

internal object AreaTransitions : DestinationStyle.Animated {

  private const val TRANSITION_TIME_MS = 300

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() =
    if (initialState.destination.route == SearchScreenDestination.route) {
      fadeIn(
        animationSpec = tween(TRANSITION_TIME_MS),
      ) + scaleIn(
        initialScale = .5f,
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() =
    if (targetState.destination.route == GalleryScreenDestination.route) {
      slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition() =
    if (initialState.destination.route == GalleryScreenDestination.route) {
      slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition() =
    if (targetState.destination.route == SearchScreenDestination.route) {
      fadeOut(
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }
}
