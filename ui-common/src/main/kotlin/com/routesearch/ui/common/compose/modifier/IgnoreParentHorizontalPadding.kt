package com.routesearch.ui.common.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

fun Modifier.ignoreHorizontalParentPadding(horizontal: Dp) = this.layout { measurable, constraints ->
  val overriddenWidth = constraints.maxWidth + 2 * horizontal.roundToPx()
  val placeable = measurable.measure(constraints.copy(maxWidth = overriddenWidth))
  layout(placeable.width, placeable.height) {
    placeable.place(0, 0)
  }
}
