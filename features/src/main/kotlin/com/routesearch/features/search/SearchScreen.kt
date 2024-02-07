package com.routesearch.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult
import com.routesearch.features.R
import com.routesearch.ui.common.theme.RouteSearchTheme
import org.koin.androidx.compose.koinViewModel

@Destination
@RootNavGraph(start = true)
@Composable
fun SearchScreen() {
  val viewModel = koinViewModel<SearchViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  SearchScreenContent(
    viewState = viewState,
    onSearchQueryChange = viewModel::onSearchQueryChange,
    onSearchActiveChange = viewModel::onSearchActiveChange,
    onBackClick = viewModel::onBackClick,
    onClearClick = viewModel::onClearClick,
    onSearch = viewModel::onSearch,
    onAreaFilterClick = viewModel::onAreaFilterClick,
    onClimbFilterClick = viewModel::onClimbFilterClick,
    onAreaSearchResultClick = viewModel::onAreaSearchResultClick,
    onClimbSearchResultClick = viewModel::onClimbSearchResultClick,
  )
}

@Composable
private fun SearchScreenContent(
  viewState: SearchViewState,
  onSearchQueryChange: (String) -> Unit,
  onSearchActiveChange: (Boolean) -> Unit,
  onBackClick: () -> Unit,
  onClearClick: () -> Unit,
  onSearch: (String) -> Unit,
  onAreaFilterClick: () -> Unit,
  onClimbFilterClick: () -> Unit,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
) = ConstraintLayout(
  modifier = Modifier.fillMaxSize()
    .background(MaterialTheme.colorScheme.surface),
) {
  val (searchBar) = createRefs()

  SearchBar(
    modifier = Modifier
      .wrapContentHeight()
      .constrainAs(searchBar) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
      },
    viewState = viewState,
    onSearchQueryChange = onSearchQueryChange,
    onSearchActiveChange = onSearchActiveChange,
    onBackClick = onBackClick,
    onClearClick = onClearClick,
    onSearch = onSearch,
  ) {
    SearchResultsList(
      modifier = Modifier.fillMaxSize(),
      viewState = viewState,
      onAreaFilterClick = onAreaFilterClick,
      onClimbFilterClick = onClimbFilterClick,
      onAreaSearchResultClick = onAreaSearchResultClick,
      onClimbSearchResultClick = onClimbSearchResultClick,
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
  modifier: Modifier = Modifier,
  viewState: SearchViewState,
  onSearchQueryChange: (String) -> Unit,
  onSearchActiveChange: (Boolean) -> Unit,
  onBackClick: () -> Unit,
  onClearClick: () -> Unit,
  onSearch: (String) -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) = SearchBar(
  modifier = modifier,
  query = viewState.searchQuery,
  onQueryChange = onSearchQueryChange,
  onSearch = onSearch,
  active = viewState.searchActive,
  onActiveChange = onSearchActiveChange,
  placeholder = { SearchPlaceholder() },
  leadingIcon = {
    SearchBarLeadingIcon(
      searchActive = viewState.searchActive,
      onBackClick = onBackClick,
    )
  },
  trailingIcon = {
    SearchBarTrailingIcon(
      searchActive = viewState.searchActive,
      searchQuery = viewState.searchQuery,
      onClick = onClearClick,
    )
  },
  content = content,
)

@Composable
private fun SearchPlaceholder() = Text(stringResource(R.string.search_screen_search_placeholder))

@Composable
private fun SearchBarLeadingIcon(
  searchActive: Boolean,
  onBackClick: () -> Unit,
) = if (searchActive) {
  Icon(
    modifier = Modifier.clickable(
      onClick = onBackClick,
    ),
    imageVector = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription = null,
  )
} else {
  Icon(
    imageVector = Icons.Default.Search,
    contentDescription = null,
  )
}

@Composable
private fun SearchBarTrailingIcon(
  searchActive: Boolean,
  searchQuery: String,
  onClick: () -> Unit,
) {
  if (searchActive && searchQuery.isNotEmpty()) {
    Icon(
      modifier = Modifier.clickable(
        onClick = onClick,
      ),
      imageVector = Icons.Default.Clear,
      contentDescription = null,
    )
  }
}

@Composable
private fun SearchResultsList(
  modifier: Modifier,
  viewState: SearchViewState,
  onAreaFilterClick: () -> Unit,
  onClimbFilterClick: () -> Unit,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
) = LazyColumn(
  modifier = modifier,
) {
  item {
    FilterRow(
      areaFilterSelected = viewState.areaFilterSelected,
      climbFilterSelected = viewState.climbFilterSelected,
      onAreaFilterClick = onAreaFilterClick,
      onClimbFilterClick = onClimbFilterClick,
    )
  }
  if (viewState.climbSearchResults.isNotEmpty() && viewState.allFiltersSelected) {
    item { ClimbSearchResultsHeader() }
  }
  if (viewState.climbFilterSelected) {
    items(viewState.climbSearchResults) {
      ClimbSearchResult(
        result = it,
        onClick = onClimbSearchResultClick,
      )
    }
  }
  if (viewState.areaSearchResults.isNotEmpty() && viewState.allFiltersSelected) {
    item { AreaSearchResultsHeader() }
  }
  if (viewState.areaFilterSelected) {
    items(viewState.areaSearchResults) {
      AreaSearchResult(
        result = it,
        onClick = onAreaSearchResultClick,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterRow(
  areaFilterSelected: Boolean,
  climbFilterSelected: Boolean,
  onAreaFilterClick: () -> Unit,
  onClimbFilterClick: () -> Unit,
) = Row(
  modifier = Modifier
    .fillMaxWidth()
    .padding(
      horizontal = 16.dp,
      vertical = 8.dp,
    )
    .wrapContentHeight(),
) {
  FilterChip(
    selected = areaFilterSelected,
    onClick = onAreaFilterClick,
    label = { Text(stringResource(R.string.search_screen_area_filter_text)) },
    leadingIcon = {
      if (areaFilterSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
        )
      }
    },
  )
  Spacer(modifier = Modifier.width(8.dp))
  FilterChip(
    selected = climbFilterSelected,
    onClick = onClimbFilterClick,
    label = { Text(stringResource(R.string.search_screen_climb_filter_text)) },
    leadingIcon = {
      if (climbFilterSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
        )
      }
    },
  )
}

@Composable
fun AreaSearchResultsHeader() = Text(
  modifier = Modifier.padding(
    horizontal = 16.dp,
    vertical = 8.dp,
  ),
  text = stringResource(R.string.search_screen_areas_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun AreaSearchResult(
  result: AreaSearchResult,
  onClick: (String) -> Unit,
) {
  ListItem(
    modifier = Modifier.clickable { onClick(result.id) },
    headlineContent = {
      Text(
        text = result.title,
        overflow = TextOverflow.Ellipsis,
      )
    },
  )
  Divider(
    modifier = Modifier.padding(
      horizontal = 16.dp,
    ),
  )
}

private val AreaSearchResult.title: AnnotatedString
  get() {
    val lastPathTokenStartIndex = pathText.length - pathTokens.last().length
    val spanStyles = listOf(
      AnnotatedString.Range(
        SpanStyle(
          fontWeight = FontWeight.Bold,
        ),
        start = lastPathTokenStartIndex,
        end = pathText.length,
      ),
    )

    return AnnotatedString(
      text = pathText,
      spanStyles = spanStyles,
    )
  }

@Composable
fun ClimbSearchResultsHeader() = Text(
  modifier = Modifier.padding(
    vertical = 8.dp,
    horizontal = 16.dp,
  ),
  text = stringResource(R.string.search_screen_climbs_header),
  style = MaterialTheme.typography.headlineSmall,
)

@Composable
private fun ClimbSearchResult(
  result: ClimbSearchResult,
  onClick: (String) -> Unit,
) {
  ListItem(
    modifier = Modifier.clickable { onClick(result.id) },
    overlineContent = {
      Text(
        text = result.pathText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    },
    headlineContent = {
      Text(result.name)
    },
    supportingContent = {
      Text(result.subtitle)
    },
  )
  Divider(
    modifier = Modifier.padding(
      horizontal = 16.dp,
    ),
  )
}

@PreviewLightDark
@Composable
private fun InactivePreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState(),
    onSearchQueryChange = { },
    onSearchActiveChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
  )
}

@PreviewLightDark
@Composable
private fun ActivePreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState(
      searchActive = true,
      searchQuery = "Atlantis",
      climbSearchResults = listOf(
        ClimbSearchResult(
          id = "c1",
          name = "Atlantis",
          pathTokens = listOf("USA", "Utah", "Southwest Utah", "Saint George", "Black Rocks", "Shady Side"),
          grade = "5.10b",
          type = "sport",
        ),
      ),
      areaSearchResults = listOf(
        AreaSearchResult(
          id = "a1",
          name = "Atlantis",
          pathTokens = listOf("USA", "Arizona", "Central Arizona", "Queen Creek Canyon", "Atlantis"),
          totalClimbs = 50,
        ),
        AreaSearchResult(
          id = "a2",
          name = "Atlantis Area",
          pathTokens = listOf("USA", "California", "Joshua Tree National Park", "Lost Horse Area", "Atlantis Area"),
          totalClimbs = 50,
        ),
      ),
    ),
    onSearchQueryChange = { },
    onSearchActiveChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
  )
}
