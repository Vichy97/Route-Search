package com.routesearch.ui.common.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = Purple80,
  secondary = PurpleGrey80,
  tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
  primary = Purple40,
  secondary = PurpleGrey40,
  tertiary = Pink40,
)

private val useDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@Composable
fun RouteSearchTheme(content: @Composable () -> Unit) {
  val darkTheme = isSystemInDarkTheme()
  val colorScheme = getColorScheme(darkTheme)

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content,
  )
}

@Composable
private fun getColorScheme(darkTheme: Boolean) = when {
  useDynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
  useDynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
  darkTheme -> DarkColorScheme
  else -> LightColorScheme
}
