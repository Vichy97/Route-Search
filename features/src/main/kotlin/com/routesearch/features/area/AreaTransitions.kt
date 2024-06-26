package com.routesearch.features.area

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.generated.destinations.GalleryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

internal object AreaTransitions : DestinationStyle.Animated() {

  private const val TRANSITION_TIME_MS = 300

  override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
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
  }

  override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    if (targetState.destination.route == GalleryScreenDestination.route) {
      slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }
  }

  override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    if (initialState.destination.route == GalleryScreenDestination.route) {
      slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }
  }

  override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    if (targetState.destination.route == SearchScreenDestination.route) {
      fadeOut(
        animationSpec = tween(TRANSITION_TIME_MS),
      )
    } else {
      null
    }
  }
}
