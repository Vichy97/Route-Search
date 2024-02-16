package com.routesearch.features.climb

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.routesearch.features.destinations.ClimbScreenDestination
import com.routesearch.features.destinations.GalleryScreenDestination

internal object ClimbTransitions : DestinationStyle.Animated {

  private const val TRANSITION_TIME_MS = 300

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
    if (targetState.destination.route == ClimbScreenDestination.route) {
      slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }
}
