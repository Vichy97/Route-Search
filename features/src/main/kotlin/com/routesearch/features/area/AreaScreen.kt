package com.routesearch.features.area

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.routesearch.ui.common.Screen
import org.koin.androidx.compose.koinViewModel

object AreaScreen : Screen {

  internal val areaIdArg = navArgument("areaId") {
    nullable = false
    type = NavType.StringType
    defaultValue = "acd5e4e7-2972-5435-a237-4cea3dffe6cb"
  }

  override val route = "area?${areaIdArg.name}={${areaIdArg.name}}"

  override val arguments = listOf(areaIdArg)

  @Composable
  override fun Content() {
    val viewModel = koinViewModel<AreaViewModel>()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when (val currentViewState = viewState) {
      is AreaViewState.Content -> Content()
      is AreaViewState.Loading -> Loading()
      AreaViewState.Idle -> Unit
    }
  }
}

@Composable
private fun Loading() = Box(
  modifier = Modifier.fillMaxSize(),
  contentAlignment = Alignment.Center,
) { CircularProgressIndicator() }

@Composable
private fun Content() = ConstraintLayout(
  modifier = Modifier.fillMaxSize(),
) { }
