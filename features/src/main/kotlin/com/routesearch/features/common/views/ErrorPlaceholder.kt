package com.routesearch.features.common.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.routesearch.features.R

@Composable
internal fun ErrorPlaceholder(
  modifier: Modifier = Modifier,
  image: ImageVector,
  message: String,
  showRetry: Boolean = false,
  onRetryClick: () -> Unit = { },
) = Column(
  modifier = modifier,
  verticalArrangement = Arrangement.spacedBy(
    space = 8.dp,
    alignment = Alignment.CenterVertically,
  ),
  horizontalAlignment = Alignment.CenterHorizontally,
) {
  Icon(
    modifier = Modifier.size(64.dp),
    imageVector = image,
    contentDescription = null,
  )
  Text(message)
  if (showRetry) {
    Button(
      onClick = { onRetryClick() },
    ) {
      Text(stringResource(R.string.common_retry_label))
    }
  }
}
