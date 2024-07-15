package com.routesearch.features.about

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

internal object AboutTransitions : DestinationStyle.Animated {
  private const val TRANSITION_TIME_MS = 300

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() = fadeOut(
    animationSpec = tween(TRANSITION_TIME_MS),
  )

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() = fadeIn(
    animationSpec = tween(TRANSITION_TIME_MS),
  ) + scaleIn(
    initialScale = .5f,
    animationSpec = tween(TRANSITION_TIME_MS),
  )
}
