package com.routesearch.features.about

import androidx.lifecycle.ViewModel
import com.routesearch.features.common.CommonUrls.GITHUB_LINK
import com.routesearch.features.common.CommonUrls.OPEN_BETA_LINK
import com.routesearch.features.common.CommonUrls.PLAY_STORE_LINK
import com.routesearch.features.common.CommonUrls.PRIVACY_POLICY_LINK
import com.routesearch.navigation.Navigator
import com.routesearch.ui.common.web.WebLauncher
import com.routesearch.util.appversion.AppVersionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
