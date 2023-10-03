package com.routesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.routesearch.navigation.MainNavGraph
import com.routesearch.ui.common.snackbar.SnackbarManager
import com.routesearch.ui.common.theme.RouteSearchTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val snackbarManager = get<SnackbarManager>()

    setContent {
      RouteSearchTheme {
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect("snackbar") {
          snackbarManager.snackbarEvents.onEach {
            val message = context.getString(it.message)
            snackbarHostState.showSnackbar(message)
          }.launchIn(this)
        }

        MainScaffolding(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
private fun MainScaffolding(
  modifier: Modifier,
) = Scaffold(
  modifier = modifier,
  topBar = { TopBar() },
) { padding ->
  val navController = rememberNavController()
  MainNavGraph(
    modifier = Modifier
      .fillMaxSize()
      .padding(padding),
    navController = navController,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() = TopAppBar(
  title = {
    Text(stringResource(R.string.app_name))
  },
)

@Preview
@Composable
private fun MainScaffoldingPreview() = RouteSearchTheme {
  MainScaffolding(modifier = Modifier.fillMaxSize())
}
