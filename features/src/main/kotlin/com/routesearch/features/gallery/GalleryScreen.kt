package com.routesearch.features.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.features.common.views.ImagePlaceholder
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

private const val CROSS_FADE_DURATION_MS = 200

@Destination(
  navArgsDelegate = GalleryScreenArgs::class,
  style = GalleryTransitions::class,
)
@Composable
internal fun GalleryScreen() {
  val viewModel = koinViewModel<GalleryViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  Content(
    urls = viewState.urls,
    onBackClick = viewModel::onBackClick,
    onImageClick = viewModel::onImageClick,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
  urls: ImmutableList<String>,
  onBackClick: () -> Unit,
  onImageClick: (Int) -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        onBackClick = { onBackClick() },
        scrollBehavior = scrollBehavior,
      )
    },
  ) { padding ->
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier
        .padding(
          top = padding.calculateTopPadding(),
          start = 16.dp,
          end = 16.dp,
        ),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      items(urls.size) { index ->
        Image(
          url = urls[index],
          onClick = { onImageClick(index) },
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
  onBackClick: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
) = TopAppBar(
  title = { },
  navigationIcon = {
    NavigationButton(
      onClick = { onBackClick() },
    )
  },
  scrollBehavior = scrollBehavior,
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
private fun Image(
  url: String,
  onClick: (String) -> Unit,
) {
  val requestedImageSize = with(LocalDensity.current) { 200.dp.roundToPx() }

  SubcomposeAsyncImage(
    modifier = Modifier
      .clip(RoundedCornerShape(32.dp))
      .height(200.dp)
      .clickable { onClick(url) },
    loading = {
      ImagePlaceholder(
        modifier = Modifier.height(200.dp),
      )
    },
    model = ImageRequest.Builder(LocalContext.current)
      .data(url)
      .size(requestedImageSize)
      .crossfade(CROSS_FADE_DURATION_MS)
      .build(),
    contentDescription = null,
    contentScale = ContentScale.Crop,
  )
}
