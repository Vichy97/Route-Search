package com.routesearch.features.common.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy

/**
 * A composable to show either a carousel of images or a placeholder if no images are present.
 */
@Composable
internal fun Images(
  modifier: Modifier = Modifier,
  urls: List<String>,
) = if (urls.isEmpty()) {
  ImagePlaceholder(
    modifier = modifier
      .padding(horizontal = 8.dp),
  )
} else if (urls.size == 1) {
  // Carousel with one item looks odd, so we use a normal AsyncImage here.
  AsyncImage(
    modifier = modifier
      .padding(horizontal = 8.dp)
      .clip(RoundedCornerShape(32.dp)),
    model = urls.first(),
    placeholder = ColorPainter(
      color = MaterialTheme.colorScheme.surfaceVariant,
    ),
    contentDescription = null,
    contentScale = ContentScale.FillWidth,
  )
} else {
  AndroidView(
    factory = { context ->
      RecyclerView(context).apply {
        layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
        adapter = CarouselAdapter(urls)

        CarouselSnapHelper().attachToRecyclerView(this)
      }
    },
    modifier = modifier
      .padding(horizontal = 4.dp)
      .clipToBounds(),
  )
}
