package com.routesearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.routesearch.navigation.MainNavGraph
import com.routesearch.ui.common.snackbar.SnackbarManager
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.view.configuration
import com.routesearch.util.view.isInDarkMode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {

  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.configureSystemBars()
    actionBar?.hide()

    setContent {
      KoinContext {
        RouteSearchTheme {
          val snackbarManager = get<SnackbarManager>()
          val snackbarHostState = remember { SnackbarHostState() }
          val context = LocalContext.current
          val navController = rememberNavController()

          LaunchedEffect("snackbar") {
            snackbarManager.snackbarEvents.onEach {
              val message = context.getString(it.message)
              snackbarHostState.showSnackbar(message = message)
            }.launchIn(this)
          }

          Scaffold(
            snackbarHost = {
              SnackbarHost(hostState = snackbarHostState)
            },
          ) {
            MainNavGraph(
              modifier = Modifier.fillMaxSize(),
              navController = navController,
            )
          }
        }
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
