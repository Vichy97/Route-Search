package com.routesearch.features.area

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.area.Area
import com.routesearch.data.climb.Grades
import com.routesearch.data.climb.Type
import com.routesearch.data.climb.getDisplayName
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
  navArgsDelegate = AreaScreenArgs::class,
)
@Composable
fun AreaScreen() {
  val viewModel = koinViewModel<AreaViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  when (val currentViewState = viewState) {
    is AreaViewState.Content -> Content(
      area = currentViewState.area,
      onBackClick = viewModel::onBackClick,
      onPathSectionClick = viewModel::onPathSectionClick,
      onLocationClick = viewModel::onLocationClick,
      onClimbClick = viewModel::onClimbClick,
      onAreaClick = viewModel::onAreaClick,
    )

    is AreaViewState.Loading -> Loading()
    AreaViewState.Idle -> Unit
  }
}

@Composable
private fun Loading() = Box(
  modifier = Modifier.fillMaxSize(),
  contentAlignment = Alignment.Center,
) { CircularProgressIndicator() }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
  area: Area,
  onBackClick: () -> Unit,
  onPathSectionClick: (String) -> Unit,
  onLocationClick: () -> Unit,
  onClimbClick: (String) -> Unit,
  onAreaClick: (String) -> Unit,
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
        metadataCard,
        description,
        areaContent,
      ) = createRefs()

      Path(
        modifier = Modifier.constrainAs(path) {
          start.linkTo(parent.start)
          top.linkTo(parent.top)
        },
        path = area.path,
        onPathSectionClick = onPathSectionClick,
      )

      Text(
        modifier = Modifier.constrainAs(name) {
          start.linkTo(parent.start)
          top.linkTo(path.bottom)
        },
        text = area.name,
        style = MaterialTheme.typography.headlineMedium,
      )

      Images(
        modifier = Modifier
          .constrainAs(image) {
            top.linkTo(name.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
          }
          .heightIn(
            min = 250.dp,
            max = 250.dp,
          )
          .padding(top = 16.dp),
        urls = area.media,
      )

      MetadataCard(
        modifier = Modifier
          .constrainAs(metadataCard) {
            start.linkTo(parent.start)
            top.linkTo(image.bottom)
          }
          .padding(top = 16.dp),
        area = area,
        onLocationClick = onLocationClick,
      )

      Description(
        modifier = Modifier
          .constrainAs(description) {
            top.linkTo(metadataCard.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
          }
          .padding(top = 8.dp),
        text = area.description,
      )

      ListContent(
        modifier = Modifier
          .constrainAs(areaContent) {
            top.linkTo(description.bottom)
          }
          .padding(top = 16.dp),
        area = area,
        onClimbClick = onClimbClick,
        onAreaClick = onAreaClick,
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
    imageVector = Icons.Default.ArrowBack,
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
    bold { append(path.last()) }
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
  area: Area,
  onLocationClick: () -> Unit,
) = Card(
  modifier = modifier,
) {
  LocationText(
    modifier = Modifier.padding(
      top = 16.dp,
      start = 16.dp,
      end = 16.dp,
    ),
    location = area.location,
    onClick = onLocationClick,
  )
  CreatedDateText(
    modifier = Modifier.padding(
      top = 16.dp,
      start = 16.dp,
      end = 16.dp,
    ),
    created = area.metadata.createdAt,
  )
  UpdatedDateText(
    modifier = Modifier.padding(
      top = 8.dp,
      start = 16.dp,
      end = 16.dp,
      bottom = 16.dp,
    ),
    updated = area.metadata.updatedAt,
  )
}

@Composable
private fun LocationText(
  modifier: Modifier = Modifier,
  location: Location,
  onClick: () -> Unit,
) {
  val locationAnnotation = "location"
  val locationString = buildAnnotatedString {
    bold { append(stringResource(R.string.area_screen_location_title)) }

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
    bold { append(stringResource(R.string.area_screen_created_title)) }
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
    bold { append(stringResource(R.string.area_screen_updated_title)) }
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
  text = stringResource(R.string.area_screen_description_header),
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
    .fadingEdges(if (canExpand && !expanded) Edge.Bottom else Edge.None),
  text = text,
  maxLines = if (expanded) Int.MAX_VALUE else 5,
  onTextLayout = onTextLayout,
)

@Composable
private fun DescriptionPlaceholder(modifier: Modifier = Modifier) = Text(
  modifier = modifier,
  text = stringResource(R.string.area_screen_description_placeholder),
)

@Composable
private fun ExpandDescriptionButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) = TextButton(
  modifier = modifier,
  onClick = onClick,
) { Text(stringResource(R.string.area_screen_expand_description_button_label)) }

@Composable
fun ListContent(
  modifier: Modifier = Modifier,
  area: Area,
  onClimbClick: (String) -> Unit,
  onAreaClick: (String) -> Unit,
) = if (area.children.isEmpty()) {
  ClimbList(
    modifier = modifier,
    area = area,
    onClimbClick = onClimbClick,
  )
} else {
  AreaList(
    modifier = modifier,
    area = area,
    onAreaClick = onAreaClick,
  )
}

@Composable
private fun AreaList(
  modifier: Modifier = Modifier,
  area: Area,
  onAreaClick: (String) -> Unit,
) = Card(
  modifier = modifier,
  colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surfaceVariant,
  ),
) {
  Text(
    modifier = Modifier.padding(
      vertical = 8.dp,
      horizontal = 16.dp,
    ),
    text = pluralStringResource(
      id = R.plurals.area_screen_number_of_areas,
      count = area.children.size,
      formatArgs = arrayOf(area.children.size),
    ),
    style = MaterialTheme.typography.titleLarge,
  )
  Divider(
    color = MaterialTheme.colorScheme.onPrimaryContainer,
  )
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentSize(),
  ) {
    area.children.forEachIndexed { index, child ->
      AreaListItem(
        areaChild = child,
        onClick = onAreaClick,
      )
      if (index < area.children.size - 1) {
        Divider(
          modifier = Modifier.padding(
            horizontal = 16.dp,
          ),
          color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
      }
    }
  }
}

@Composable
fun AreaListItem(
  areaChild: Area.Child,
  onClick: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClick(areaChild.id) },
  headlineContent = { Text(areaChild.name) },
  supportingContent = { Text(areaChild.subtitle()) },
  colors = ListItemDefaults.colors(
    containerColor = Color.Transparent,
  ),
)

@Composable
private fun Area.Child.subtitle(): String {
  val climbsText = pluralStringResource(
    id = R.plurals.area_screen_number_of_climbs,
    count = totalClimbs,
    formatArgs = arrayOf(totalClimbs),
  )
  val areasText = pluralStringResource(
    id = R.plurals.area_screen_number_of_areas,
    count = numberOfChildren,
    formatArgs = arrayOf(numberOfChildren),
  )
  return "$climbsText - $areasText"
}

@Composable
private fun ClimbList(
  modifier: Modifier = Modifier,
  area: Area,
  onClimbClick: (String) -> Unit,
) = Card(
  modifier = modifier,
  colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surfaceVariant,
  ),
) {
  Text(
    modifier = Modifier.padding(
      vertical = 8.dp,
      horizontal = 16.dp,
    ),
    text = pluralStringResource(
      id = R.plurals.area_screen_number_of_climbs,
      count = area.climbs.size,
      formatArgs = arrayOf(area.climbs.size),
    ),
    style = MaterialTheme.typography.titleLarge,
  )
  Divider(
    color = MaterialTheme.colorScheme.onSurfaceVariant,
  )
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentSize(),
  ) {
    area.climbs.forEachIndexed { index, climb ->
      ClimbListItem(
        climb = climb,
        onClick = onClimbClick,
      )
      if (index < area.children.size - 1) {
        Divider(
          modifier = Modifier.padding(
            horizontal = 16.dp,
          ),
          color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
      }
    }
  }
}

@Composable
fun ClimbListItem(
  climb: Area.Climb,
  onClick: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClick(climb.id) },
  headlineContent = { Text(climb.name) },
  supportingContent = { Text(climb.type.toString()) },
  trailingContent = { climb.grades?.getDisplayName(climb.type)?.let { Text(it) } },
  colors = ListItemDefaults.colors(
    containerColor = Color.Transparent,
  ),
)

@Preview
@Composable
private fun AreaScreenChildrenPreview() = MaterialTheme {
  Surface {
    Content(
      Area(
        id = "1",
        metadata = Area.Metadata(
          createdAt = LocalDate.parse("2023-12-01"),
          updatedAt = LocalDate.parse("2023-12-01"),
        ),
        name = "Atlantis",
        description = """
       According to all known laws of aviation, there is no 
       way a bee should be able to fly. Its wings are too 
       small to get its fat little body off the ground. The 
       bee, of course, flies anyway because bees don't care 
       what humans think is impossible. Yellow, black. 
       Yellow, black. Yellow, black. Yellow, black. Ooh, 
       black and yellow! Let's shake it up a little. Barry! 
       Breakfast is ready! Ooming! Hang on a second. Hello? 
       - Barry? - Adam?
        """.trimIndent(),
        path = listOf(
          "USA",
          "Arizona",
          "Central Arizona",
          "Queen Creek Canyon",
        ),
        ancestorIds = emptyList(),
        location = Location(
          latitude = 37.81176,
          longitude = -119.64678,
        ),
        totalClimbs = 34,
        climbs = emptyList(),
        children = listOf(
          Area.Child(
            id = "1",
            name = "Billy",
            totalClimbs = 15,
            numberOfChildren = 5,
          ),
          Area.Child(
            id = "2",
            name = "Bobby",
            totalClimbs = 4,
            numberOfChildren = 1,
          ),
          Area.Child(
            id = "3",
            name = "Jimmy",
            totalClimbs = 0,
            numberOfChildren = 1,
          ),
          Area.Child(
            id = "4",
            name = "Linus",
            totalClimbs = 25,
            numberOfChildren = 6,
          ),
          Area.Child(
            id = "5",
            name = "Stinky",
            totalClimbs = 18,
            numberOfChildren = 4,
          ),
        ),
        media = emptyList(),
      ),
      onBackClick = { },
      onPathSectionClick = { },
      onLocationClick = { },
      onClimbClick = { },
      onAreaClick = { },
    )
  }
}

@Preview
@Composable
private fun AreaScreenClimbPreview() = MaterialTheme {
  Surface {
    Content(
      Area(
        id = "1",
        metadata = Area.Metadata(
          createdAt = LocalDate.parse("2023-12-01"),
          updatedAt = LocalDate.parse("2023-12-01"),
        ),
        name = "fake area",
        description = """
       According to all known laws of aviation, there is no 
       way a bee should be able to fly. Its wings are too 
       small to get its fat little body off the ground. The 
       bee, of course, flies anyway because bees don't care 
       what humans think is impossible. Yellow, black. 
       Yellow, black. Yellow, black. Yellow, black. Ooh, 
       black and yellow! Let's shake it up a little. Barry! 
       Breakfast is ready! Ooming! Hang on a second. Hello? 
       - Barry? - Adam?
        """.trimIndent(),
        path = listOf(
          "USA",
          "Arizona",
          "Central Arizona",
          "Queen Creek Canyon",
        ),
        ancestorIds = emptyList(),
        location = Location(
          latitude = 37.81176,
          longitude = -119.64678,
        ),
        totalClimbs = 5,
        climbs = listOf(
          Area.Climb(
            id = "1",
            grades = Grades(
              yds = "5.9",
              vScale = null,
            ),
            name = "Billy",
            type = Type.AID,
          ),
          Area.Climb(
            id = "2",
            grades = Grades(
              yds = "5.9",
              vScale = null,
            ),
            name = "Bobby",
            type = Type.TRAD,
          ),
          Area.Climb(
            id = "3",
            grades = Grades(
              yds = null,
              vScale = "v7",
            ),
            name = "Jimmy",
            type = Type.BOULDERING,
          ),
          Area.Climb(
            id = "4",
            grades = null,
            name = "Linus",
            type = Type.SNOW,
          ),
          Area.Climb(
            id = "5",
            grades = null,
            name = "Stinky",
            type = Type.DEEP_WATER_SOLO,
          ),
        ),
        children = emptyList(),
        media = emptyList(),
      ),
      onBackClick = { },
      onPathSectionClick = { },
      onLocationClick = { },
      onClimbClick = { },
      onAreaClick = { },
    )
  }
}
