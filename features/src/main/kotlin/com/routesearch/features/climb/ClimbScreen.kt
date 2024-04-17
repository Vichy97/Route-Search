package com.routesearch.features.climb

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.climb.Climb
import com.routesearch.data.climb.Type
import com.routesearch.features.R
import com.routesearch.features.common.views.ExpandableText
import com.routesearch.features.common.views.Images
import com.routesearch.features.common.views.MetadataCard
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.getAnnotationAt
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.common.date.monthYearFormat
import org.koin.androidx.compose.koinViewModel

private const val MIN_GALLERY_IMAGE = 6

@OptIn(ExperimentalMaterial3Api::class)
@Destination(
  navArgsDelegate = ClimbScreenArgs::class,
  style = ClimbTransitions::class,
)
@Composable
fun ClimbScreen() {
  val viewModel = koinViewModel<ClimbViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        onBackClick = viewModel::onBackClick,
        scrollBehavior = scrollBehavior,
      )
    },
  ) { padding ->

    when (val currentViewState = viewState) {
      is ClimbViewState.Content -> Content(
        modifier = Modifier
          .padding(top = padding.calculateTopPadding())
          .verticalScroll(rememberScrollState()),
        climb = currentViewState.climb,
        onPathSectionClick = viewModel::onPathSectionClick,
        onLocationClick = viewModel::onLocationClick,
        onShowAllImagesClick = viewModel::onShowAllImagesClick,
        onBookmarkClick = viewModel::onBookmarkClick,
        onShareClick = viewModel::onShareClick,
      )

      is ClimbViewState.Loading -> Loading()
      ClimbViewState.Idle -> Unit
    }
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

@Composable
private fun Content(
  modifier: Modifier = Modifier,
  climb: Climb,
  onPathSectionClick: (String) -> Unit,
  onLocationClick: () -> Unit,
  onShowAllImagesClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  onShareClick: () -> Unit,
) = ConstraintLayout(
  modifier = modifier.fillMaxWidth(),
) {
  val (
    path,
    name,
    image,
    metadataCard,
    showAllImages,
    climbInfoCard,
    buttonRow,
    description,
    location,
    protection,
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

  TextButton(
    modifier = Modifier
      .constrainAs(showAllImages) {
        top.linkTo(image.bottom)
        end.linkTo(parent.end)

        visibility = if (climb.media.size >= MIN_GALLERY_IMAGE) Visibility.Visible else Visibility.Gone
      }
      .padding(
        end = 8.dp,
      ),
    onClick = { onShowAllImagesClick() },
  ) {
    Text(
      text = stringResource(R.string.climb_screen_show_all_images_button_label),
    )
  }

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
    createdAt = climb.metadata.createdAt?.monthYearFormat(),
    updatedAt = climb.metadata.updatedAt?.monthYearFormat(),
    onLocationClick = onLocationClick,
    firstAscent = climb.fa,
  )

  ClimbInfoCard(
    modifier = Modifier
      .constrainAs(climbInfoCard) {
        top.linkTo(
          anchor = image.bottom,
          margin = 16.dp,
        )
        end.linkTo(
          anchor = parent.end,
        )
      }
      .padding(end = 16.dp),
    grade = if (climb.type == Type.BOULDERING) {
      climb.grades?.vScale
    } else {
      climb.grades?.yds
    },
    type = climb.type.toString(),
    height = climb.length,
    pitches = if (climb.pitches.isNotEmpty()) climb.pitches.size else 1,
  )

  ButtonRow(
    modifier = Modifier.constrainAs(buttonRow) {
      top.linkTo(metadataCard.bottom)
      start.linkTo(parent.start)
    },
    onBookmarkClick = { onBookmarkClick() },
    onShareClick = { onShareClick() },
  )

  Description(
    modifier = Modifier
      .constrainAs(description) {
        top.linkTo(
          anchor = buttonRow.bottom,
          margin = 8.dp,
        )
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
      }
      .padding(horizontal = 16.dp),
    text = climb.description.general,
  )

  Location(
    modifier = Modifier
      .constrainAs(location) {
        top.linkTo(
          anchor = description.bottom,
          margin = 8.dp,
        )
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
        visibility = if (climb.description.location.isNotBlank()) Visibility.Visible else Visibility.Gone
      }
      .padding(horizontal = 16.dp),
    text = climb.description.location,
  )

  Protection(
    modifier = Modifier
      .constrainAs(protection) {
        top.linkTo(
          anchor = location.bottom,
          margin = 8.dp,
        )
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
        visibility = if (climb.description.protection.isNotBlank()) Visibility.Visible else Visibility.Gone
      }
      .padding(horizontal = 16.dp),
    text = climb.description.protection,
  )
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
private fun ButtonRow(
  modifier: Modifier,
  onBookmarkClick: () -> Unit,
  onShareClick: () -> Unit,
) = LazyRow(
  modifier = modifier.padding(
    top = 8.dp,
  ),
  contentPadding = PaddingValues(
    horizontal = 16.dp,
  ),
  horizontalArrangement = spacedBy(8.dp),
) {
  item {
    BookmarkButton(
      onClick = { onBookmarkClick() },
    )
  }
  item {
    ShareButton(
      onClick = { onShareClick() },
    )
  }
}

@Composable
private fun BookmarkButton(
  onClick: () -> Unit,
) = AssistChip(
  onClick = { onClick() },
  leadingIcon = {
    Icon(
      imageVector = Icons.Default.BookmarkBorder,
      contentDescription = null,
    )
  },
  label = { Text(stringResource(R.string.common_bookmark_button_label)) },
)

@Composable
private fun ShareButton(
  onClick: () -> Unit,
) = AssistChip(
  onClick = { onClick() },
  leadingIcon = {
    Icon(
      imageVector = Icons.Default.Share,
      contentDescription = null,
    )
  },
  label = { Text(stringResource(R.string.common_share_button_label)) },
)

@Composable
private fun Description(
  modifier: Modifier = Modifier,
  text: String,
) = Column(
  modifier = modifier,
) {
  DescriptionHeader()

  if (text.isNotBlank()) {
    ExpandableText(
      text = text,
      maxLinesBeforeExpandable = 5,
    )
  } else {
    DescriptionPlaceholder()
  }
}

@Composable
private fun Location(
  modifier: Modifier = Modifier,
  text: String,
) = Column(
  modifier = modifier,
) {
  LocationHeader()

  if (text.isNotBlank()) {
    ExpandableText(
      text = text,
      maxLinesBeforeExpandable = 5,
    )
  } else {
    DescriptionPlaceholder()
  }
}

@Composable
private fun Protection(
  modifier: Modifier = Modifier,
  text: String,
) = Column(
  modifier = modifier,
) {
  ProtectionHeader()

  if (text.isNotBlank()) {
    ExpandableText(
      text = text,
      maxLinesBeforeExpandable = 5,
    )
  } else {
    DescriptionPlaceholder()
  }
}

@Composable
private fun DescriptionHeader() = Text(
  text = stringResource(R.string.climb_screen_description_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun LocationHeader() = Text(
  text = stringResource(R.string.climb_screen_location_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun ProtectionHeader() = Text(
  text = stringResource(R.string.climb_screen_protection_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun DescriptionPlaceholder(modifier: Modifier = Modifier) = Text(
  modifier = modifier,
  text = stringResource(R.string.climb_screen_description_placeholder),
)

@PreviewLightDark
@Composable
private fun ClimbScreenPreview() = RouteSearchTheme {
  Content(
    climb = fakeClimbs[0],
    onPathSectionClick = { },
    onLocationClick = { },
    onShowAllImagesClick = { },
    onBookmarkClick = { },
    onShareClick = { },
  )
}
