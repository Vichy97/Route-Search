package com.routesearch.features.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.compose.url
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
    )
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
  appVersion: String,
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
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = stringResource(R.string.common_urls_github_repo),
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = stringResource(R.string.common_urls_open_beta),
  )
  WebsiteText(
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      ),
    text = stringResource(R.string.about_screen_privacy_policy),
  )
}

@Composable
private fun WebsiteText(
  modifier: Modifier = Modifier,
  text: String,
) {
  val url = buildAnnotatedString {
    underline {
      url(text) {
        append(text)
      }
    }
  }
  Text(
    modifier = modifier,
    text = url,
    style = MaterialTheme.typography.bodyMedium,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

@PreviewLightDark
@Composable
private fun AboutScreenPreview() = RouteSearchTheme {
  Surface {
    Content(
      appVersion = "1.0",
    )
  }
}
