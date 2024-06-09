package com.routesearch.features.common.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.routesearch.ui.common.theme.RouteSearchTheme

@Composable
internal fun ImagePlaceholder(
  modifier: Modifier = Modifier,
  icon: ImageVector = Icons.Default.Image,
) = Card(
  modifier = modifier,
  shape = RoundedCornerShape(32.dp),
) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      modifier = Modifier.size(48.dp),
      imageVector = icon,
      contentDescription = null,
    )
  }
}

@Preview
@Composable
private fun ImagePlaceholderPreview() = RouteSearchTheme {
  Surface {
    ImagePlaceholder(
      modifier = Modifier
        .padding(16.dp)
        .height(250.dp),
    )
  }
}
