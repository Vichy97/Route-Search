package com.routesearch.features.climb

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.climb.Climb
import com.routesearch.data.location.Location
import com.routesearch.features.R
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.getAnnotationAt
import com.routesearch.ui.common.compose.isAnnotatedAtIndex
import com.routesearch.ui.common.compose.modifier.Edge
import com.routesearch.ui.common.compose.modifier.fadingEdges
import com.routesearch.ui.common.compose.underline
import com.routesearch.util.common.date.monthYearFormat
import kotlinx.datetime.LocalDate
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
          start = 16.dp,
          end = 16.dp,
          bottom = 16.dp,
        )
        .verticalScroll(scrollState),
    ) {
      val (
        path,
        name,
        image,
        metadetaCard,
        description,
      ) = createRefs()

      Path(
        modifier = Modifier.constrainAs(path) {
          start.linkTo(parent.start)
          top.linkTo(parent.top)
        },
        path = climb.pathTokens,
        onPathSectionClick = onPathSectionClick,
      )

      Text(
        modifier = Modifier.constrainAs(name) {
          start.linkTo(parent.start)
          top.linkTo(path.bottom)
        },
        text = climb.name,
        style = MaterialTheme.typography.headlineMedium,
      )

      Images(
        modifier = Modifier
          .constrainAs(image) {
            start.linkTo(parent.start)
            top.linkTo(name.bottom)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
          }
          .heightIn(
            min = 250.dp,
            max = 250.dp,
          )
          .padding(top = 16.dp),
        urls = climb.media,
      )

      MetadataCard(
        modifier = Modifier
          .constrainAs(metadetaCard) {
            start.linkTo(parent.start)
            top.linkTo(image.bottom)

            visibility = if (climb.hasMetadata) Visibility.Visible else Visibility.Gone
          }
          .padding(top = 16.dp),
        climb = climb,
        onLocationClick = onLocationClick,
      )

      Description(
        modifier = Modifier
          .constrainAs(description) {
            start.linkTo(parent.start)
            top.linkTo(metadetaCard.bottom)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
          }
          .padding(top = 8.dp),
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
private fun Images(
  modifier: Modifier = Modifier,
  urls: List<String>,
) {
  if (urls.isEmpty()) {
    Card(
      modifier = modifier,
    ) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        Icon(
          modifier = Modifier.size(48.dp),
          imageVector = Icons.Default.AccountCircle,
          contentDescription = null,
        )
      }
    }
  } else {
    AsyncImage(
      modifier = modifier
        .clip(RoundedCornerShape(8.dp)),
      model = urls.first(),
      placeholder = ColorPainter(
        color = MaterialTheme.colorScheme.surfaceVariant,
      ),
      contentDescription = null,
      contentScale = ContentScale.FillWidth,
    )
  }
}

@Composable
private fun MetadataCard(
  modifier: Modifier = Modifier,
  climb: Climb,
  onLocationClick: () -> Unit,
) = Card(
  modifier = modifier,
) {
  climb.location?.let {
    LocationText(
      modifier = Modifier.padding(
        top = 16.dp,
        start = 16.dp,
        end = 16.dp,
      ),
      location = it,
      onClick = onLocationClick,
    )
  }

  climb.metadata.createdAt?.let {
    CreatedDateText(
      modifier = Modifier.padding(
        top = 16.dp,
        start = 16.dp,
        end = 16.dp,
      ),
      created = it,
    )
  }

  climb.metadata.updatedAt?.let {
    UpdatedDateText(
      modifier = Modifier.padding(
        top = 8.dp,
        start = 16.dp,
        end = 16.dp,
        bottom = 16.dp,
      ),
      updated = it,
    )
  }
}

@Composable
private fun LocationText(
  modifier: Modifier = Modifier,
  location: Location,
  onClick: () -> Unit,
) {
  val locationAnnotation = "location"
  val locationString = buildAnnotatedString {
    bold { append(stringResource(R.string.climb_screen_location_title)) }

    append(" ")

    underline {
      annotation(locationAnnotation) {
        append(location.displayString)
      }
    }
  }
  ClickableText(
    modifier = modifier,
    text = locationString,
    style = MaterialTheme.typography.titleSmall.copy(
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    ),
  ) {
    val annotationClicked = locationString.isAnnotatedAtIndex(
      index = it,
      annotation = locationAnnotation,
    )
    if (annotationClicked) onClick()
  }
}

@Composable
private fun CreatedDateText(
  modifier: Modifier = Modifier,
  created: LocalDate,
) {
  val createdDateText = buildAnnotatedString {
    bold { append(stringResource(R.string.climb_screen_created_title)) }
    append(" ")
    append(created.monthYearFormat())
  }
  Text(
    modifier = modifier,
    text = createdDateText,
    style = MaterialTheme.typography.titleSmall,
  )
}

@Composable
private fun UpdatedDateText(
  modifier: Modifier = Modifier,
  updated: LocalDate,
) {
  val updatedDateText = buildAnnotatedString {
    bold { append(stringResource(R.string.climb_screen_updated_title)) }
    append(" ")
    append(updated.monthYearFormat())
  }
  Text(
    modifier = modifier,
    text = updatedDateText,
    style = MaterialTheme.typography.titleSmall,
  )
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
  style = MaterialTheme.typography.headlineSmall,
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

@Preview
@Composable
private fun ClimbScreenPreview() = MaterialTheme {
  Surface {
    Content(
      Climb(
        id = "1",
        metadata = Climb.Metadata(
          leftRightIndex = null,
          createdAt = LocalDate(
            year = 2000,
            monthNumber = 1,
            dayOfMonth = 1,
          ),
          updatedAt = LocalDate(
            year = 2000,
            monthNumber = 1,
            dayOfMonth = 1,
          ),
        ),
        name = "Here is a climb!",
        pathTokens = listOf(
          "USA",
          "Washington",
          "Walla Walla",
        ),
        location = Location(
          latitude = 31.12,
          longitude = 32.13,
        ),
        ancestorIds = emptyList(),
        description = Climb.Description(
          general = """
            According to all known laws of aviation, there is no 
            way a bee should be able to fly. Its wings are too 
            small to get its fat little body off the ground. The 
            bee, of course, flies anyway because bees don't care 
            what humans think is impossible.
          """.trimIndent(),
          location = """
            Yellow, black. 
            Yellow, black. Yellow, black. Yellow, black. Ooh, 
            black and yellow!
          """.trimIndent(),
          protection = """
            Let's shake it up a little. Barry! 
            Breakfast is ready! Ooming! Hang on a second. Hello? 
            - Barry? - Adam?
          """.trimIndent(),
        ),
        length = null,
        boltCount = null,
        fa = "",
        type = null,
        grades = null,
        pitches = emptyList(),
        media = emptyList(),
      ),
      onBackClick = { },
      onPathSectionClick = { },
      onLocationClick = { },
    )
  }
}
