package com.routesearch.features.area

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.area.Area
import com.routesearch.features.R
import com.routesearch.features.common.views.ErrorPlaceholder
import com.routesearch.features.common.views.Images
import com.routesearch.features.common.views.MarkdownView
import com.routesearch.features.common.views.MetadataCard
import com.routesearch.features.common.views.VScaleGradeChart
import com.routesearch.features.common.views.YdsGradeChart
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.clickable
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.compose.url
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.common.date.monthYearFormat
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

private const val MIN_GALLERY_IMAGE = 6

@Destination(
  navArgsDelegate = AreaScreenArgs::class,
  style = AreaTransitions::class,
)
@Composable
fun AreaScreen() {
  val viewModel = koinViewModel<AreaViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  AreaScreenScaffold(
    scrollable = viewState is AreaViewState.Content,
    path = viewState.path,
    name = viewState.name,
    onBackClick = viewModel::onBackClick,
    onHomeClick = viewModel::onHomeClick,
    onPathSectionClick = viewModel::onPathSectionClick,
  ) {
    when (val currentViewState = viewState) {
      is AreaViewState.Content -> Content(
        area = currentViewState.area,
        onLocationClick = viewModel::onLocationClick,
        onBookmarkClick = viewModel::onBookmarkClick,
        onDownloadClick = viewModel::onDownloadClick,
        onShareClick = viewModel::onShareClick,
        onOrganizationClick = viewModel::onOrganizationClick,
        onClimbClick = viewModel::onClimbClick,
        onFilterClimbsClick = viewModel::onFilterClimbsClick,
        onAreaClick = viewModel::onAreaClick,
        onShowAllImagesClick = viewModel::onShowAllImagesClick,
        onImageClick = viewModel::onImageClick,
      )

      is AreaViewState.Loading -> Loading(
        modifier = Modifier.fillMaxSize(),
      )

      is AreaViewState.NetworkError -> NetworkError(
        onRetryClick = viewModel::onRetryClick,
      )

      is AreaViewState.UnknownError -> UnknownError(
        onRetryClick = viewModel::onRetryClick,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AreaScreenScaffold(
  scrollable: Boolean,
  path: ImmutableList<String>,
  name: String,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit,
  onPathSectionClick: (String) -> Unit,
  content: @Composable () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        onBackClick = { onBackClick() },
        onHomeClick = { onHomeClick() },
        scrollBehavior = scrollBehavior,
      )
    },
  ) { padding ->
    Column(
      modifier = if (scrollable) {
        Modifier.verticalScroll(rememberScrollState())
      } else {
        Modifier
      },
    ) {
      Path(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            top = padding.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
          ),
        path = path,
        onPathSectionClick = { onPathSectionClick(it) },
      )

      Text(
        modifier = Modifier
          .padding(horizontal = 16.dp),
        text = name,
        style = MaterialTheme.typography.headlineMedium,
      )

      content()
    }
  }
}

@Composable
private fun Loading(
  modifier: Modifier = Modifier,
) = Box(
  modifier = modifier
    .fillMaxSize(),
  contentAlignment = Alignment.Center,
) {
  CircularProgressIndicator()
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
  area: Area,
  onLocationClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  onDownloadClick: () -> Unit,
  onShareClick: () -> Unit,
  onOrganizationClick: (Area.Organization) -> Unit,
  onFilterClimbsClick: () -> Unit,
  onClimbClick: (String) -> Unit,
  onAreaClick: (String) -> Unit,
  onShowAllImagesClick: () -> Unit,
  onImageClick: (Int) -> Unit,
) = ConstraintLayout(
  modifier = modifier
    .fillMaxWidth(),
) {
  val (
    image,
    metadataCard,
    showAllImages,
    ydsChartHeader,
    ydsChart,
    vScaleChartHeader,
    vScaleChart,
    description,
    buttonRow,
    organizations,
    listContent,
  ) = createRefs()

  Images(
    modifier = Modifier
      .constrainAs(image) {
        top.linkTo(
          anchor = parent.top,
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
    urls = area.media,
    onImageClick = { onImageClick(it) },
  )

  MetadataCard(
    modifier = Modifier
      .constrainAs(metadataCard) {
        start.linkTo(parent.start)
        top.linkTo(image.bottom)
      }
      .padding(
        top = 16.dp,
        start = 16.dp,
        end = 16.dp,
      )
      .wrapContentWidth(),
    location = area.location,
    createdAt = area.metadata.createdAt.monthYearFormat(),
    updatedAt = area.metadata.updatedAt.monthYearFormat(),
    onLocationClick = { onLocationClick() },
    firstAscent = null,
  )

  TextButton(
    modifier = Modifier
      .constrainAs(showAllImages) {
        top.linkTo(image.bottom)
        end.linkTo(parent.end)

        visibility = if (area.media.size >= MIN_GALLERY_IMAGE) Visibility.Visible else Visibility.Gone
      }
      .padding(
        end = 8.dp,
      ),
    onClick = { onShowAllImagesClick() },
  ) {
    Text(
      text = stringResource(R.string.area_screen_show_all_images_button_label),
    )
  }

  ButtonRow(
    modifier = Modifier.constrainAs(buttonRow) {
      top.linkTo(metadataCard.bottom)
      start.linkTo(parent.start)
    },
    onBookmarkClick = { onBookmarkClick() },
    onDownloadClick = { onDownloadClick() },
    onShareClick = { onShareClick() },
  )

  Text(
    modifier = Modifier
      .constrainAs(ydsChartHeader) {
        top.linkTo(
          anchor = buttonRow.bottom,
          margin = 8.dp,
        )
        start.linkTo(parent.start)

        visibility = if (area.climbCount.roped >= 10) Visibility.Visible else Visibility.Gone
      }
      .padding(
        start = 16.dp,
      ),
    text = stringResource(
      id = R.string.area_screen_yds_header,
      formatArgs = arrayOf(area.climbCount.roped),
    ),
    style = MaterialTheme.typography.titleMedium,
  )

  YdsGradeChart(
    modifier = Modifier
      .constrainAs(ydsChart) {
        top.linkTo(
          anchor = ydsChartHeader.bottom,
          margin = 4.dp,
        )
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
        visibility = if (area.climbCount.roped >= 10) Visibility.Visible else Visibility.Gone
      }
      .heightIn(
        max = 100.dp,
      )
      .padding(
        horizontal = 16.dp,
      ),
    gradeMap = area.gradeMap,
  )

  Text(
    modifier = Modifier
      .constrainAs(vScaleChartHeader) {
        top.linkTo(
          anchor = ydsChart.bottom,
          margin = 16.dp,
        )
        start.linkTo(parent.start)

        visibility = if (area.climbCount.bouldering >= 10) Visibility.Visible else Visibility.Gone
      }
      .padding(
        start = 16.dp,
      ),
    text = stringResource(
      id = R.string.area_screen_v_scale_header,
      formatArgs = arrayOf(area.climbCount.bouldering),
    ),
    style = MaterialTheme.typography.titleMedium,
  )

  VScaleGradeChart(
    modifier = Modifier
      .constrainAs(vScaleChart) {
        top.linkTo(
          anchor = vScaleChartHeader.bottom,
          margin = 4.dp,
        )
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
        visibility = if (area.climbCount.bouldering >= 10) Visibility.Visible else Visibility.Gone
      }
      .heightIn(
        max = 100.dp,
      )
      .padding(
        horizontal = 16.dp,
      ),
    gradeMap = area.gradeMap,
  )

  Description(
    modifier = Modifier
      .constrainAs(description) {
        top.linkTo(vScaleChart.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
      }
      .padding(
        top = 8.dp,
        start = 16.dp,
        end = 16.dp,
      ),
    text = area.description,
  )

  Organizations(
    modifier = Modifier
      .constrainAs(organizations) {
        top.linkTo(description.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
        visibility = if (area.organizations.isEmpty()) Visibility.Gone else Visibility.Visible
      }
      .padding(top = 8.dp),
    organizations = area.organizations,
    onOrganizationClick = { onOrganizationClick(it) },
  )

  ListContent(
    modifier = Modifier
      .constrainAs(listContent) {
        top.linkTo(organizations.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(
          anchor = parent.bottom,
          margin = 16.dp,
        )

        width = Dimension.fillToConstraints
      }
      .padding(top = 16.dp),
    area = area,
    onFilterClimbsClick = { onFilterClimbsClick() },
    onClimbClick = { onClimbClick(it) },
    onAreaClick = { onAreaClick(it) },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
) = TopAppBar(
  title = { },
  navigationIcon = {
    NavigationButton(
      onClick = { onBackClick() },
    )
  },
  actions = {
    IconButton(
      onClick = { onHomeClick() },
    ) {
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = null,
      )
    }
  },
  scrollBehavior = scrollBehavior,
)

@Composable
private fun NavigationButton(
  onClick: () -> Unit,
) = IconButton(
  onClick = { onClick() },
) {
  Icon(
    imageVector = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription = null,
  )
}

@Composable
private fun Path(
  modifier: Modifier = Modifier,
  path: ImmutableList<String>,
  onPathSectionClick: (String) -> Unit,
) {
  val pathString = buildAnnotatedString {
    path.dropLast(1).forEach {
      clickable(
        tag = it,
        onClick = { onPathSectionClick(it) },
      ) { append(it) }
      append(" / ")
    }
    bold { append(path.last()) }
  }
  Text(
    modifier = modifier,
    text = pathString,
    style = MaterialTheme.typography.bodyLarge,
  )
}

@Composable
private fun Description(
  modifier: Modifier = Modifier,
  text: String,
) = Column(
  modifier = modifier,
) {
  DescriptionHeader()

  if (text.isNotBlank()) {
    MarkdownView(text)
  } else {
    DescriptionPlaceholder()
  }
}

@Composable
private fun DescriptionHeader() = Text(
  text = stringResource(R.string.area_screen_description_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun DescriptionPlaceholder() {
  val descriptionString = buildAnnotatedString {
    append(stringResource(R.string.area_screen_description_placeholder))

    append(" ")

    val openBetaUrl = stringResource(R.string.common_urls_open_beta)
    underline {
      url(openBetaUrl) {
        append(openBetaUrl)
      }
      append("!")
    }
  }
  Text(
    text = descriptionString,
    style = MaterialTheme.typography.bodyLarge,
  )
}

@Composable
private fun Organizations(
  modifier: Modifier = Modifier,
  organizations: ImmutableList<Area.Organization>,
  onOrganizationClick: (Area.Organization) -> Unit,
) = Column(modifier = modifier) {
  OrganizationsHeader(
    modifier = Modifier.padding(
      horizontal = 16.dp,
    ),
  )

  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp),
    contentPadding = PaddingValues(
      horizontal = 16.dp,
    ),
    horizontalArrangement = spacedBy(16.dp),
  ) {
    items(
      items = organizations,
      key = { it.id },
    ) {
      OrganizationCard(
        organization = it,
        onClick = { onOrganizationClick(it) },
      )
    }
  }
}

@Composable
private fun ButtonRow(
  modifier: Modifier,
  onBookmarkClick: () -> Unit,
  onDownloadClick: () -> Unit,
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
    DownloadButton(
      onClick = { onDownloadClick() },
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
private fun DownloadButton(
  onClick: () -> Unit,
) = AssistChip(
  onClick = { onClick() },
  leadingIcon = {
    Icon(
      imageVector = Icons.Default.Download,
      contentDescription = null,
    )
  },
  label = { Text(stringResource(R.string.common_download_button_label)) },
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
private fun OrganizationsHeader(
  modifier: Modifier = Modifier,
) = Text(
  modifier = modifier,
  text = stringResource(R.string.area_screen_organizations_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun OrganizationCard(
  modifier: Modifier = Modifier,
  organization: Area.Organization,
  onClick: () -> Unit,
) = ElevatedCard(
  modifier = modifier.widthIn(
    max = 200.dp,
  ),
  onClick = { onClick() },
) {
  ListItem(
    headlineContent = {
      Text(
        text = organization.name,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    },
    supportingContent = {
      organization.website?.let {
        Text(
          text = it,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
    },
  )
}

@Composable
private fun ListContent(
  modifier: Modifier = Modifier,
  area: Area,
  onFilterClimbsClick: () -> Unit,
  onClimbClick: (String) -> Unit,
  onAreaClick: (String) -> Unit,
) = if (area.children.isEmpty() && area.climbs.isNotEmpty()) {
  ClimbList(
    modifier = modifier,
    area = area,
    onFilterClick = { onFilterClimbsClick() },
    onClimbClick = { onClimbClick(it) },
  )
} else if (area.climbs.isEmpty() && area.children.isNotEmpty()) {
  AreaList(
    modifier = modifier,
    area = area,
    onAreaClick = { onAreaClick(it) },
  )
} else {
  EmptyAreaPlaceholder(
    modifier = modifier,
  )
}

@Composable
private fun AreaList(
  modifier: Modifier = Modifier,
  area: Area,
  onAreaClick: (String) -> Unit,
) = Column(
  modifier = modifier,
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
  HorizontalDivider()
  area.children.forEachIndexed { index, child ->
    AreaListItem(
      areaChild = child,
      onClick = { onAreaClick(it) },
    )
    if (index < area.children.size - 1) {
      HorizontalDivider(
        modifier = Modifier.padding(
          horizontal = 16.dp,
        ),
      )
    }
  }
}

@Composable
private fun AreaListItem(
  areaChild: Area.Child,
  onClick: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClick(areaChild.id) },
  headlineContent = { Text(areaChild.name) },
  supportingContent = { AreaListItemSubtitle(areaChild) },
)

@Composable
private fun AreaListItemSubtitle(area: Area.Child) {
  val climbsText = pluralStringResource(
    id = R.plurals.area_screen_number_of_climbs,
    count = area.totalClimbs,
    formatArgs = arrayOf(area.totalClimbs),
  )
  val areasText = pluralStringResource(
    id = R.plurals.area_screen_number_of_areas,
    count = area.numberOfChildren,
    formatArgs = arrayOf(area.numberOfChildren),
  )
  val subtitle = StringBuilder().apply {
    append(climbsText)
    if (area.numberOfChildren > 0) {
      append(" • ")
      append(areasText)
    }
  }.toString()

  Text(subtitle)
}

@Composable
private fun ClimbList(
  modifier: Modifier = Modifier,
  area: Area,
  onFilterClick: () -> Unit,
  onClimbClick: (String) -> Unit,
) = Column(
  modifier = modifier,
) {
  ClimbListHeader(
    area = area,
    onFilterClick = { onFilterClick() },
  )
  HorizontalDivider()
  area.climbs.forEachIndexed { index, climb ->
    ClimbListItem(
      climb = climb,
      onClick = { onClimbClick(it) },
    )
    if (index < area.climbs.size - 1) {
      HorizontalDivider(
        modifier = Modifier.padding(
          horizontal = 16.dp,
        ),
      )
    }
  }
}

@Composable
private fun ClimbListHeader(
  modifier: Modifier = Modifier,
  area: Area,
  onFilterClick: () -> Unit,
) = Row(
  modifier = modifier.fillMaxWidth(),
  horizontalArrangement = Arrangement.SpaceBetween,
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
  IconButton(
    onClick = { onFilterClick() },
  ) {
    Icon(
      imageVector = Icons.Default.Tune,
      contentDescription = null,
    )
  }
}

@Composable
private fun ClimbListItem(
  climb: Area.Climb,
  onClick: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClick(climb.id) },
  headlineContent = { Text(climb.name) },
  supportingContent = { ClimbListItemSubtitle(climb) },
  trailingContent = {
    climb.grade?.let {
      Text(
        text = it,
        fontWeight = FontWeight.Bold,
      )
    }
  },
)

@Composable
private fun ClimbListItemSubtitle(climb: Area.Climb) {
  val typeNames = stringArrayResource(id = R.array.climb_types)
  val types = climb.types.joinToString(
    separator = ", ",
  ) { typeNames[it.ordinal] }
  val pitches = pluralStringResource(
    id = R.plurals.area_screen_number_of_pitches,
    count = climb.numberOfPitches,
    formatArgs = arrayOf(climb.numberOfPitches),
  )
  Text("$types • $pitches")
}

@Composable
private fun EmptyAreaPlaceholder(
  modifier: Modifier = Modifier,
) = Column(
  modifier = modifier,
) {
  Text(
    modifier = Modifier.padding(
      horizontal = 16.dp,
    ),
    text = stringResource(R.string.area_screen_empty_area_header),
    style = MaterialTheme.typography.titleLarge,
  )
  val descriptionString = buildAnnotatedString {
    append(stringResource(R.string.area_screen_empty_area_placeholder))

    append(" ")

    val openBetaUrl = stringResource(R.string.common_urls_open_beta)
    underline {
      url(openBetaUrl) {
        append(openBetaUrl)
      }
      append("!")
    }
  }
  Text(
    modifier = Modifier.padding(
      horizontal = 16.dp,
    ),
    text = descriptionString,
    style = MaterialTheme.typography.bodyLarge,
  )
}

@Composable
private fun NetworkError(
  modifier: Modifier = Modifier,
  onRetryClick: () -> Unit,
) = ErrorPlaceholder(
  modifier = modifier.fillMaxSize(),
  image = Icons.Rounded.WifiOff,
  message = stringResource(R.string.common_network_error_message),
  showRetry = true,
  onRetryClick = { onRetryClick() },
)

@Composable
private fun UnknownError(
  modifier: Modifier = Modifier,
  onRetryClick: () -> Unit,
) = ErrorPlaceholder(
  modifier = modifier.fillMaxSize(),
  image = Icons.Rounded.Warning,
  message = stringResource(R.string.common_generic_error_message),
  showRetry = true,
  onRetryClick = { onRetryClick() },
)

@PreviewLightDark
@Composable
private fun LoadingPreview() = RouteSearchTheme {
  val area = fakeAreas[0]

  Surface {
    AreaScreenScaffold(
      scrollable = false,
      path = area.path,
      name = area.name,
      onBackClick = { },
      onHomeClick = { },
      onPathSectionClick = { },
    ) {
      Loading()
    }
  }
}

@PreviewLightDark
@Composable
private fun AreaWithChildrenPreview() = RouteSearchTheme {
  val area = fakeAreas[0]
  Surface {
    AreaScreenScaffold(
      scrollable = true,
      path = area.path,
      name = area.name,
      onBackClick = { },
      onHomeClick = { },
      onPathSectionClick = { },
    ) {
      Content(
        area = area,
        onLocationClick = { },
        onBookmarkClick = { },
        onDownloadClick = { },
        onShareClick = { },
        onOrganizationClick = { },
        onFilterClimbsClick = { },
        onClimbClick = { },
        onAreaClick = { },
        onShowAllImagesClick = { },
        onImageClick = { },
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun AreaWithClimbsPreview() = RouteSearchTheme {
  Surface {
    val area = fakeAreas[1]
    AreaScreenScaffold(
      scrollable = true,
      path = area.path,
      name = area.name,
      onBackClick = { },
      onHomeClick = { },
      onPathSectionClick = { },
    ) {
      Content(
        area = area,
        onLocationClick = { },
        onBookmarkClick = { },
        onDownloadClick = { },
        onShareClick = { },
        onOrganizationClick = { },
        onFilterClimbsClick = { },
        onClimbClick = { },
        onAreaClick = { },
        onShowAllImagesClick = { },
        onImageClick = { },
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun EmptyAreaPreview() = RouteSearchTheme {
  Surface {
    val area = fakeAreas[2]
    AreaScreenScaffold(
      scrollable = true,
      path = area.path,
      name = area.name,
      onBackClick = { },
      onHomeClick = { },
      onPathSectionClick = { },
    ) {
      Content(
        area = area,
        onLocationClick = { },
        onBookmarkClick = { },
        onDownloadClick = { },
        onShareClick = { },
        onOrganizationClick = { },
        onFilterClimbsClick = { },
        onClimbClick = { },
        onAreaClick = { },
        onShowAllImagesClick = { },
        onImageClick = { },
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun NetworkErrorPreview() = RouteSearchTheme {
  Surface {
    val area = fakeAreas[2]
    AreaScreenScaffold(
      scrollable = false,
      path = area.path,
      name = area.name,
      onBackClick = { },
      onHomeClick = { },
      onPathSectionClick = { },
    ) {
      NetworkError(
        onRetryClick = { },
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun UnknownErrorPreview() = RouteSearchTheme {
  Surface {
    val area = fakeAreas[2]
    AreaScreenScaffold(
      scrollable = false,
      path = area.path,
      name = area.name,
      onBackClick = { },
      onHomeClick = { },
      onPathSectionClick = { },
    ) {
      UnknownError(
        onRetryClick = { },
      )
    }
  }
}
