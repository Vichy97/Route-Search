package com.routesearch.features.search

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

internal object SearchScreenTransitions : DestinationStyle.Animated() {

  private const val TRANSITION_TIME_MS = 300

  override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    fadeOut(
      animationSpec = tween(TRANSITION_TIME_MS),
    )
  }

  override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    fadeIn(
      animationSpec = tween(TRANSITION_TIME_MS),
    )
  }
}
