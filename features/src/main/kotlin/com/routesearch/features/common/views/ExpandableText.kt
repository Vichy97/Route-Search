package com.routesearch.features.common.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow

/**
 * Composable for text that can be expanded by clicking on it.
 *
 * @param maxLinesBeforeExpandable Max lines before the text will be ellipsized and have the option to expand.
 */
@Composable
internal fun ExpandableText(
  modifier: Modifier = Modifier,
  text: String,
  maxLinesBeforeExpandable: Int,
) {
  var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
  val canExpand by remember { derivedStateOf { textLayoutResult?.didOverflowHeight ?: false } }
  var expanded by remember { mutableStateOf(false) }

  Text(
    modifier = modifier
      .clickable(
        enabled = canExpand && !expanded,
      ) { expanded = true }
      .animateContentSize(),
    text = text,
    maxLines = if (expanded) Int.MAX_VALUE else maxLinesBeforeExpandable,
    overflow = TextOverflow.Ellipsis,
    onTextLayout = { textLayoutResult = it },
  )
}
