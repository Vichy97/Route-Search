package com.routesearch.features.imageviewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size.Companion.ORIGINAL
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.collections.immutable.ImmutableList
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(
  navArgs = ImageViewerScreenArgs::class,
)
@Composable
internal fun ImageViewerScreen() {
  val viewModel = koinViewModel<ImageViewerViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  Content(
    urls = viewState.urls,
    index = viewState.index,
    onBackClick = viewModel::onBackClick,
  )
}

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Content(
  urls: ImmutableList<String>,
  index: Int,
  onBackClick: () -> Unit,
) = Scaffold(
  modifier = Modifier.background(Color.Black),
  topBar = {
    TopAppBar(
      onBackClick = { onBackClick() },
    )
  },
) {
  val pagerState = rememberPagerState(
    initialPage = index,
    pageCount = { urls.size },
  )

  HorizontalPager(
    state = pagerState,
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.Black),
  ) { page ->
    val painter = rememberAsyncImagePainter(
      model = ImageRequest.Builder(LocalContext.current)
        .data(urls[page])
        .size(ORIGINAL)
        .crossfade(true)
        .build(),
    )
    val zoomState = rememberZoomState()

    if (painter.state is AsyncImagePainter.State.Loading) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
    } else {
      Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .fillMaxSize()
          .zoomable(zoomState),
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
  onBackClick: () -> Unit,
) = TopAppBar(
  colors = TopAppBarDefaults.topAppBarColors(
    containerColor = Color.Transparent,
    navigationIconContentColor = Color.White,
  ),
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
