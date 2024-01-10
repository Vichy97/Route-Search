@file:Suppress("MatchingDeclarationName")

package com.routesearch.ui.common.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer

enum class Edge {
  Top,
  Bottom,
  Start,
  End,
  None,
  All,
}

fun Modifier.fadingEdges(
  vararg edges: Edge,
): Modifier {
  val horizontalGradients = Brush.horizontalGradients(edges.toList())
  val verticalGradients = Brush.verticalGradients(edges.toList())

  return this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
      drawContent()
      verticalGradients?.let {
        drawRect(
          brush = it,
          blendMode = BlendMode.DstIn,
        )
      }
      horizontalGradients?.let {
        drawRect(
          brush = it,
          blendMode = BlendMode.DstIn,
        )
      }
    }
}

private typealias Edges = List<Edge>

private val Edges.colorStops
  get() = flatMap { it.colorStops }

// In this case, the hardcoded White color doesn't matter. It will always blend from Transparent to the content color
// because of the blending we do in the fadingEdge function.
private val Edge.colorStops
  get() = when (this) {
    Edge.Top, Edge.Start -> listOf(0f to Color.Transparent, 0.25f to Color.White)
    Edge.Bottom, Edge.End -> listOf(.75f to Color.White, 1f to Color.Transparent)
    Edge.All -> listOf(.75f to Color.White, 1f to Color.Transparent, 0f to Color.Transparent, 0.25f to Color.White)
    Edge.None -> emptyList()
  }

private val Edges.horizontal
  get() = filter { it == Edge.End || it == Edge.Start }

private val Edges.vertical
  get() = filter { it == Edge.Top || it == Edge.Bottom }

@Suppress("SpreadOperator")
private fun Brush.Companion.verticalGradients(edges: Edges): Brush? {
  val colorStops = edges.vertical.colorStops

  return if (colorStops.isNotEmpty()) {
    verticalGradient(*colorStops.toTypedArray())
  } else {
    null
  }
}

@Suppress("SpreadOperator")
private fun Brush.Companion.horizontalGradients(edges: Edges): Brush? {
  val colorStops = edges.horizontal.colorStops

  return if (colorStops.isNotEmpty()) {
    horizontalGradient(*colorStops.toTypedArray())
  } else {
    null
  }
}
