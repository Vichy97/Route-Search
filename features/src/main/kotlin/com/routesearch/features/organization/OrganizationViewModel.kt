package com.routesearch.features.organization

import androidx.lifecycle.ViewModel
import com.routesearch.ui.common.web.WebLauncher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class OrganizationViewModel(
  args: OrganizationScreenArgs,
  private val webLauncher: WebLauncher,
) : ViewModel() {

  private val _viewState = MutableStateFlow(
    OrganizationViewState(
      name = args.name,
      websiteUrl = args.websiteUrl,
      description = args.description,
      facebookUrl = args.facebookUrl,
      instagramUrl = args.instagramUrl,
    ),
  )
  val viewState = _viewState.asStateFlow()

  fun onWebsiteClick() {
    val url = requireNotNull(viewState.value.websiteUrl)
    webLauncher.launchUrl(url)
  }

  fun onFacebookClick() {
    val url = requireNotNull(viewState.value.facebookUrl)
    webLauncher.launchUrl(url)
  }

  fun onInstagramClick() {
    val url = requireNotNull(viewState.value.instagramUrl)
    webLauncher.launchUrl(url)
  }
}
