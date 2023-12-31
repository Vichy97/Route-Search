package com.routesearch

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.routesearch.navigation.MainNavGraph
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.view.configuration
import com.routesearch.util.view.isInDarkMode

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.configureSystemBars()

    setContent {
      RouteSearchTheme {
        val navController = rememberNavController()

        MainNavGraph(
          modifier = Modifier.fillMaxSize(),
          navController = navController,
        )
      }
    }
  }

  private fun Window.configureSystemBars() {
    if (decorView.isInEditMode) return

    statusBarColor = Transparent.toArgb()
    navigationBarColor = Transparent.toArgb()

    WindowCompat.setDecorFitsSystemWindows(this, false)
    WindowCompat.getInsetsController(this, decorView).apply {
      isAppearanceLightStatusBars = !configuration.isInDarkMode
      isAppearanceLightNavigationBars = !configuration.isInDarkMode
    }
  }
}
