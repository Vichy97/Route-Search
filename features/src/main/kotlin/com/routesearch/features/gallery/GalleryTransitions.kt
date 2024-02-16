package com.routesearch.features.gallery

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

internal object GalleryTransitions : DestinationStyle.Animated {

  private const val TRANSITION_TIME_MS = 300

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() =
    slideInHorizontally(
      initialOffsetX = { fullWidth -> fullWidth },
      animationSpec = tween(TRANSITION_TIME_MS),
    )

  override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() = slideOutHorizontally(
    targetOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(TRANSITION_TIME_MS),
  )
}
