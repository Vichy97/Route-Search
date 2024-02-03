package com.routesearch.features.climb

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.climb.Climb
import com.routesearch.features.R
import com.routesearch.features.common.views.Images
import com.routesearch.features.common.views.MetadataCard
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.getAnnotationAt
import com.routesearch.ui.common.compose.modifier.Edge
import com.routesearch.ui.common.compose.modifier.fadingEdges
import com.routesearch.ui.common.theme.RouteSearchTheme
import org.koin.androidx.compose.koinViewModel

@Destination(
  navArgsDelegate = ClimbScreenArgs::class,
)
@Composable
fun ClimbScreen() {
  val viewModel = koinViewModel<ClimbViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  when (val currentViewState = viewState) {
    is ClimbViewState.Content -> Content(
      climb = currentViewState.climb,
      onBackClick = viewModel::onBackClick,
      onPathSectionClick = viewModel::onPathSectionClick,
      onLocationClick = viewModel::onLocationClick,
    )

    is ClimbViewState.Loading -> Loading()
    ClimbViewState.Idle -> Unit
  }
}

@Composable
private fun Loading() = Box(
  modifier = Modifier.fillMaxSize(),
  contentAlignment = Alignment.Center,
) {
  // Progress Indicators are broken in the latest compose BOM
  // CircularProgressIndicator()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
  climb: Climb,
  onBackClick: () -> Unit,
  onPathSectionClick: (String) -> Unit,
  onLocationClick: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        onBackClick = onBackClick,
        scrollBehavior = scrollBehavior,
      )
    },
  ) { padding ->
    val scrollState = rememberScrollState()

    ConstraintLayout(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = padding.calculateTopPadding(),
          bottom = 16.dp,
        )
        .verticalScroll(scrollState),
    ) {
      val (
        path,
        name,
        image,
        metadataCard,
        description,
      ) = createRefs()

      Path(
        modifier = Modifier
          .constrainAs(path) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
          }
          .padding(horizontal = 16.dp),
        path = climb.pathTokens,
        onPathSectionClick = onPathSectionClick,
      )

      Text(
        modifier = Modifier
          .constrainAs(name) {
            start.linkTo(parent.start)
            top.linkTo(path.bottom)
          }
          .padding(horizontal = 16.dp),
        text = climb.name,
        style = MaterialTheme.typography.headlineMedium,
      )

      Images(
        modifier = Modifier
          .constrainAs(image) {
            top.linkTo(
              anchor = name.bottom,
              margin = 16.dp,
            )
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
          }
          .heightIn(
            min = 250.dp,
            max = 250.dp,
          )
          .padding(horizontal = 8.dp),
        urls = climb.media,
      )

      MetadataCard(
        modifier = Modifier
          .constrainAs(metadataCard) {
            top.linkTo(
              anchor = image.bottom,
              margin = 16.dp,
            )
            start.linkTo(parent.start)

            visibility = if (climb.hasMetadata) Visibility.Visible else Visibility.Gone
          }
          .padding(start = 16.dp)
          .wrapContentWidth(),
        location = climb.location,
        createdAt = climb.metadata.createdAt,
        updatedAt = climb.metadata.updatedAt,
        onLocationClick = onLocationClick,
      )

      Description(
        modifier = Modifier
          .constrainAs(description) {
            top.linkTo(
              anchor = metadataCard.bottom,
              margin = 8.dp,
            )
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
          }
          .padding(horizontal = 16.dp),
        text = climb.description.general,
      )
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
      onClick = onBackClick,
    )
  },
  scrollBehavior = scrollBehavior,
)

@Composable
private fun NavigationButton(
  onClick: () -> Unit,
) = IconButton(
  onClick = onClick,
) {
  Icon(
    imageVector = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription = null,
  )
}

@Composable
private fun Path(
  modifier: Modifier = Modifier,
  path: List<String>,
  onPathSectionClick: (String) -> Unit,
) {
  val pathString = buildAnnotatedString {
    path.dropLast(1).forEach {
      annotation(it) { append(it) }
      append(" / ")
    }
    bold {
      annotation(path.last()) {
        append(path.last())
      }
    }
  }
  ClickableText(
    modifier = modifier,
    text = pathString,
    style = MaterialTheme.typography.bodyLarge.copy(
      color = MaterialTheme.colorScheme.onSurface,
    ),
  ) { index ->
    pathString.getAnnotationAt(index)
      ?.let { onPathSectionClick(it) }
  }
}

@Composable
private fun Description(
  modifier: Modifier = Modifier,
  text: String,
) = Column(
  modifier = modifier,
) {
  var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
  val canExpand by remember { derivedStateOf { textLayoutResult?.didOverflowHeight ?: false } }
  var expanded by remember { mutableStateOf(false) }

  DescriptionHeader()

  if (text.isNotBlank()) {
    DescriptionContent(
      canExpand = canExpand,
      expanded = expanded,
      text = text,
      onTextLayout = { textLayoutResult = it },
    )
  } else {
    DescriptionPlaceholder()
  }

  if (canExpand && !expanded) {
    ExpandDescriptionButton(
      modifier = Modifier.offset(
        x = (-8).dp,
      ),
      onClick = { expanded = true },
    )
  }
}

@Composable
private fun DescriptionHeader() = Text(
  text = stringResource(R.string.climb_screen_description_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun DescriptionContent(
  modifier: Modifier = Modifier,
  canExpand: Boolean,
  expanded: Boolean,
  text: String,
  onTextLayout: (TextLayoutResult) -> Unit,
) = Text(
  modifier = modifier
    .animateContentSize()
    .fadingEdges(if (canExpand and !expanded) Edge.Bottom else Edge.None),
  text = text,
  maxLines = if (expanded) Int.MAX_VALUE else 5,
  onTextLayout = onTextLayout,
)

@Composable
private fun DescriptionPlaceholder(modifier: Modifier = Modifier) = Text(
  modifier = modifier,
  text = stringResource(R.string.climb_screen_description_placeholder),
)

@Composable
private fun ExpandDescriptionButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) = TextButton(
  modifier = modifier,
  onClick = onClick,
) { Text(stringResource(R.string.climb_screen_expand_description_button_label)) }

@PreviewLightDark
@Composable
private fun ClimbScreenPreview() = RouteSearchTheme {
  Content(
    climb = fakeClimbs[0],
    onBackClick = { },
    onPathSectionClick = { },
    onLocationClick = { },
  )
}
