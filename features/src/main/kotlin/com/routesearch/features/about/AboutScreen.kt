package com.routesearch.features.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.routesearch.features.R
import com.routesearch.features.common.CommonUrls.GITHUB_LINK
import com.routesearch.features.common.CommonUrls.OPEN_BETA_LINK
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.isAnnotatedAtIndex
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.theme.RouteSearchTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AboutScreen() {
  val viewModel = koinViewModel<AboutViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        onBackClick = { viewModel.onBackClick() },
      )
    },
  ) { padding ->
    Content(
      modifier = Modifier
        .padding(top = padding.calculateTopPadding()),
      appVersion = viewState.appVersion,
      onGithubClick = viewModel::onGithubClick,
      onOpenBetaClick = viewModel::onOpenBetaClick,
      onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick,
      onPlayStoreClick = viewModel::onPlayStoreClick,
    )
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
  appVersion: String,
  onGithubClick: () -> Unit,
  onOpenBetaClick: () -> Unit,
  onPrivacyPolicyClick: () -> Unit,
  onPlayStoreClick: () -> Unit,
) = ConstraintLayout(
  modifier = modifier
    .fillMaxSize(),
) {
  val (
    title,
    infoColumn,
  ) = createRefs()

  Text(
    modifier = Modifier
      .constrainAs(title) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
      }
      .padding(
        horizontal = 16.dp,
      ),
    text = stringResource(R.string.about_screen_title),
    style = MaterialTheme.typography.headlineMedium,
  )

  InfoColumn(
    modifier = Modifier
      .constrainAs(infoColumn) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(title.bottom)
      },
    appVersion = appVersion,
    onGithubClick = { onGithubClick() },
    onOpenBetaClick = { onOpenBetaClick() },
    onPrivacyPolicyClick = { onPrivacyPolicyClick() },
    onPlayStoreClick = { onPlayStoreClick() },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
  onBackClick: () -> Unit,
) = TopAppBar(
  title = { },
  navigationIcon = {
    NavigationButton(
      onClick = { onBackClick() },
    )
  },
)

@Composable
private fun NavigationButton(
  onClick: () -> Unit,
) = IconButton(
  onClick = { onClick() },
) {
  Icon(
    imageVector = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription = null,
  )
}

@Composable
private fun InfoColumn(
  modifier: Modifier = Modifier,
  appVersion: String,
  onGithubClick: () -> Unit,
  onOpenBetaClick: () -> Unit,
  onPrivacyPolicyClick: () -> Unit,
  onPlayStoreClick: () -> Unit,
) = Column(
  modifier = modifier,
) {
  Text(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = stringResource(
      id = R.string.about_screen_version_number,
      formatArgs = arrayOf(appVersion),
    ),
    style = MaterialTheme.typography.bodyLarge,
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = stringResource(R.string.about_screen_google_play_store_message),
    onClick = { onPlayStoreClick() },
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = GITHUB_LINK,
    onClick = { onGithubClick() },
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = OPEN_BETA_LINK,
    onClick = { onOpenBetaClick() },
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = stringResource(R.string.about_screen_privacy_policy),
    onClick = { onPrivacyPolicyClick() },
  )
}

@Composable
private fun WebsiteText(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit,
) {
  val websiteAnnotation = "website"
  val url = buildAnnotatedString {
    underline {
      annotation(websiteAnnotation) {
        append(text)
      }
    }
  }
  ClickableText(
    modifier = modifier,
    text = url,
    style = MaterialTheme.typography.bodyMedium.copy(
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  ) {
    val annotationClicked = url.isAnnotatedAtIndex(
      index = it,
      annotation = websiteAnnotation,
    )
    if (annotationClicked) onClick()
  }
}

@PreviewLightDark
@Composable
private fun AboutScreenPreview() = RouteSearchTheme {
  Surface {
    Content(
      appVersion = "1.0",
      onGithubClick = { },
      onOpenBetaClick = { },
      onPrivacyPolicyClick = { },
      onPlayStoreClick = { },
    )
  }
}
