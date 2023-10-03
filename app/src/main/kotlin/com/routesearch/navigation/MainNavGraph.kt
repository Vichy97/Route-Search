package com.routesearch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavGraph(
  modifier: Modifier = Modifier,
  navController: NavHostController,
) = NavHost(
  navController = navController,
  startDestination = "home",
  modifier = modifier,
) {
  composable("home") {
    TODO("Not implemented")
  }
}
