package com.routesearch.features.about

import androidx.lifecycle.ViewModel
import com.routesearch.navigation.Navigator
import com.routesearch.util.appversion.AppVersionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AboutViewModel(
  private val navigator: Navigator,
  appVersionProvider: AppVersionProvider,
) : ViewModel() {

  private val _viewState = MutableStateFlow(AboutViewState(appVersionProvider.appVersion))
  val viewState = _viewState.asStateFlow()

  fun onBackClick() = navigator.popBackStack()
}
