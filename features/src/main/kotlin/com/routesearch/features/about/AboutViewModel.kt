package com.routesearch.features.about

import androidx.lifecycle.ViewModel
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.web.WebLauncher
import com.routesearch.util.appversion.AppVersionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal const val GITHUB_LINK = "https://github.com/Vichy97/Route-Search"
internal const val OPEN_BETA_LINK = "https://openbeta.io/"

private const val PRIVACY_POLICY_LINK = ""
private const val PLAY_STORE_LINK = ""

internal class AboutViewModel(
  private val navigator: Navigator,
  private val webLauncher: WebLauncher,
  appVersionProvider: AppVersionProvider,
) : ViewModel() {

  private val _viewState = MutableStateFlow(AboutViewState(appVersionProvider.appVersion))
  val viewState = _viewState.asStateFlow()

  fun onBackClick() = navigator.popBackStack()

  fun onGithubClick() = webLauncher.launchUrl(GITHUB_LINK)

  fun onOpenBetaClick() = webLauncher.launchUrl(OPEN_BETA_LINK)

  fun onPrivacyPolicyClick() = webLauncher.launchUrl(PRIVACY_POLICY_LINK)

  fun onPlayStoreClick() = webLauncher.launchUrl(PLAY_STORE_LINK)
}
