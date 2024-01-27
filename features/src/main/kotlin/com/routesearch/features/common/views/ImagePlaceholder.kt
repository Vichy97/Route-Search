package com.routesearch.features.common.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.routesearch.ui.common.theme.RouteSearchTheme

@Composable
fun ImagePlaceholder(
  modifier: Modifier = Modifier,
) = Card(
  modifier = modifier,
) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      modifier = Modifier.size(48.dp),
      imageVector = Icons.Default.Image,
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
        .heightIn(
          max = 250.dp,
        ),
    )
  }
}
