package com.routesearch.features.area

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.routesearch.data.climb.getDisplayName
import com.routesearch.features.R
import com.routesearch.features.common.views.ExpandableText
import com.routesearch.features.common.views.Images
import com.routesearch.features.common.views.MetadataCard
import com.routesearch.features.common.views.VScaleGradeChart
import com.routesearch.features.common.views.YdsGradeChart
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.getAnnotationAt
import com.routesearch.ui.common.theme.RouteSearchTheme
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
) {
  // Progress Indicators are broken in the latest compose BOM
  // CircularProgressIndicator()
}

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
        )
        .verticalScroll(scrollState),
    ) {
      val (
        path,
        name,
        image,
        metadataCard,
        ydsChartHeader,
        ydsChart,
        vScaleChartHeader,
        vScaleChart,
        description,
        organizations,
        listContent,
      ) = createRefs()

      Path(
        modifier = Modifier
          .constrainAs(path) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
          }
          .padding(
            horizontal = 16.dp,
          ),
        path = area.path,
        onPathSectionClick = onPathSectionClick,
      )

      Text(
        modifier = Modifier
          .constrainAs(name) {
            start.linkTo(parent.start)
            top.linkTo(path.bottom)
          }
          .padding(
            horizontal = 16.dp,
          ),
        text = area.name,
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
        urls = area.media,
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
        createdAt = area.metadata.createdAt,
        updatedAt = area.metadata.updatedAt,
        onLocationClick = onLocationClick,
      )

      Text(
        modifier = Modifier
          .constrainAs(ydsChartHeader) {
            top.linkTo(
              anchor = metadataCard.bottom,
              margin = 16.dp,
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
      )

      ListContent(
        modifier = Modifier
          .constrainAs(listContent) {
            top.linkTo(organizations.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            visibility = if (area.children.isEmpty() && area.climbs.isEmpty()) Visibility.Gone else Visibility.Visible
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
private fun DescriptionHeader() = Text(
  text = stringResource(R.string.area_screen_description_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun DescriptionPlaceholder(modifier: Modifier = Modifier) = Text(
  modifier = modifier,
  text = stringResource(R.string.area_screen_description_placeholder),
)

@Composable
private fun Organizations(
  modifier: Modifier = Modifier,
  organizations: List<Area.Organization>,
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
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(
      items = organizations,
      key = { it.id },
    ) {
      OrganizationCard(
        organization = it,
      )
    }
  }
}

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
) = ElevatedCard(
  modifier = modifier.widthIn(
    max = 200.dp,
  ),
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
  Divider()
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
  onClimbClick: (String) -> Unit,
) = Column(
  modifier = modifier,
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
  Divider()
  area.climbs.forEachIndexed { index, climb ->
    ClimbListItem(
      climb = climb,
      onClick = onClimbClick,
    )
    if (index < area.climbs.size - 1) {
      Divider(
        modifier = Modifier.padding(
          horizontal = 16.dp,
        ),
      )
    }
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
    climb.grades?.getDisplayName(climb.type)?.let {
      Text(
        text = it,
        fontWeight = FontWeight.Bold,
      )
    }
  },
)

@Composable
private fun ClimbListItemSubtitle(climb: Area.Climb) {
  val type = stringArrayResource(R.array.climb_types)[climb.type.ordinal]
  val pitches = pluralStringResource(
    id = R.plurals.area_screen_number_of_pitches,
    count = climb.numberOfPitches,
    formatArgs = arrayOf(climb.numberOfPitches),
  )
  Text("$type • $pitches")
}

@PreviewLightDark
@Composable
private fun AreaWithChildrenPreview() = RouteSearchTheme {
  Content(
    area = fakeAreas[0],
    onBackClick = { },
    onPathSectionClick = { },
    onLocationClick = { },
    onClimbClick = { },
    onAreaClick = { },
  )
}

@PreviewLightDark
@Composable
private fun AreaWithClimbsPreview() = RouteSearchTheme {
  Content(
    area = fakeAreas[1],
    onBackClick = { },
    onPathSectionClick = { },
    onLocationClick = { },
    onClimbClick = { },
    onAreaClick = { },
  )
}
