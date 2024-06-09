package com.routesearch.features.common.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.collections.immutable.ImmutableList
import logcat.LogPriority.DEBUG
import logcat.LogPriority.WARN
import logcat.asLog
import logcat.logcat
import java.lang.RuntimeException

private const val LogTag = "Images"

/**
 * A composable to show either a carousel of images or a placeholder if no images are present.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Images(
  modifier: Modifier = Modifier,
  urls: ImmutableList<String>,
  onImageClick: (Int) -> Unit,
) = if (urls.isEmpty()) {
  ImagePlaceholder(
    modifier = modifier
      .padding(horizontal = 8.dp),
  )
} else if (urls.size == 1) {
  // Carousel with one item looks odd, so we use a normal AsyncImage here.
  SubcomposeAsyncImage(
    modifier = modifier
      .padding(horizontal = 8.dp)
      .clip(RoundedCornerShape(32.dp))
      .clickable { onImageClick(0) },
    model = urls.first(),
    loading = { ImagePlaceholder() },
    error = {
      ImagePlaceholder(
        icon = Icons.Default.BrokenImage,
      )
    },
    onLoading = { onImageLoading(urls.first()) },
    onSuccess = { onImageLoadingSuccess(urls.first()) },
    onError = {
      onImageLoadingError(
        url = urls.first(),
        error = it.result.throwable,
      )
    },
    contentDescription = null,
    contentScale = ContentScale.FillWidth,
  )
} else {
  val carouselState = rememberCarouselState(
    initialItem = 0,
    itemCount = { urls.size },
  )
  HorizontalMultiBrowseCarousel(
    modifier = modifier
      .height(250.dp)
      .padding(horizontal = 8.dp),
    state = carouselState,
    preferredItemWidth = 300.dp,
    itemSpacing = 8.dp,
  ) { index ->
    val url = urls[index]
    SubcomposeAsyncImage(
      modifier = Modifier
        .fillMaxSize()
        .clickable { onImageClick(index) },
      loading = { ImagePlaceholder() },
      error = {
        ImagePlaceholder(
          icon = Icons.Default.BrokenImage,
        )
      },
      model = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .build(),
      onLoading = { onImageLoading(url) },
      onSuccess = { onImageLoadingSuccess(url) },
      onError = {
        onImageLoadingError(
          url = url,
          error = it.result.throwable,
        )
      },
      contentDescription = null,
      contentScale = ContentScale.Crop,
    )
  }
}

private fun onImageLoading(url: String) = logcat(
  tag = LogTag,
  priority = DEBUG,
) { "Loading image $url" }

private fun onImageLoadingSuccess(url: String) = logcat(
  tag = LogTag,
  priority = DEBUG,
) { "Done loading image $url" }

private fun onImageLoadingError(
  url: String,
  error: Throwable,
) = logcat(
  tag = LogTag,
  priority = WARN,
) {
  ImageLoadingException(
    message = "Error loading image $url",
    cause = error,
  ).asLog()
}

private class ImageLoadingException(
  message: String,
  cause: Throwable,
) : RuntimeException(message, cause)
