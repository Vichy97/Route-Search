package com.routesearch.features.common.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.vectorSource

private const val MapStyleUrl = "mapbox://styles/vichy97/clws0jwp8018e01rda8whfnv7"

/**
 * [MapboxMap] with a layer that displays nearby crags.
 */
@OptIn(MapboxExperimental::class)
@Composable
internal fun CragMap(
  modifier: Modifier,
  textColor: Color,
) {
  val mapViewportState = rememberMapViewportState()
  return MapboxMap(
    modifier = modifier,
    style = { MapStyle(MapStyleUrl) },
    scaleBar = { },
    compass = { },
    mapViewportState = mapViewportState,
  ) {
    SetMapStyle(
      textColor = textColor,
    )
  }
}

private const val MaxZoom = 15L
private const val Attribution = "©OpenBeta contributors"
private const val CragTilesetUrl = "https://maptiles.openbeta.io/crags/{z}/{x}/{y}.pbf"
private const val CragLayerId = "crags"
private const val CragsSourceId = "crags-source"
private const val AreaLabelTextSize = 14.0
private const val AreaLabelHaloWidth = 1.0
private const val AreaLabelHaloBlur = .25

@OptIn(MapboxExperimental::class)
@Composable
private fun SetMapStyle(textColor: Color) = MapEffect { mapView ->
  mapView.mapboxMap.getStyle { style ->
    style.addSource(getCragSource())
    style.addLayer(getCragLayer(textColor))
  }
}

private fun getCragSource() = vectorSource(CragsSourceId) {
  tiles(listOf(CragTilesetUrl))
  maxzoom(MaxZoom)
  attribution(Attribution)
}

private fun getCragLayer(textColor: Color) = symbolLayer(CragLayerId, CragsSourceId) {
  sourceLayer(CragLayerId)
  iconAnchor(IconAnchor.CENTER)
  textField(Expression.get("name"))
  textSize(AreaLabelTextSize)
  textColor(textColor.toArgb())
  textHaloWidth(AreaLabelHaloWidth)
  textHaloBlur(AreaLabelHaloBlur)
  textHaloColor(Color.White.toArgb())
}
