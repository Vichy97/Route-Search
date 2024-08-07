package com.routesearch.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult
import com.routesearch.features.R
import com.routesearch.features.common.views.CragMap
import com.routesearch.features.common.views.ErrorPlaceholder
import com.routesearch.ui.common.theme.RouteSearchTheme
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Destination(
  style = SearchScreenTransitions::class,
)
@RootNavGraph(start = true)
@Composable
internal fun SearchScreen() {
  val viewModel = koinViewModel<SearchViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  SearchScreenContent(
    viewState = viewState,
    onSearchQueryChange = viewModel::onSearchQueryChange,
    onSearchExpandedChange = viewModel::onSearchExpandedChange,
    onBackClick = viewModel::onBackClick,
    onClearClick = viewModel::onClearClick,
    onSearch = viewModel::onSearch,
    onAreaFilterClick = viewModel::onAreaFilterClick,
    onClimbFilterClick = viewModel::onClimbFilterClick,
    onAreaSearchResultClick = viewModel::onAreaSearchResultClick,
    onClimbSearchResultClick = viewModel::onClimbSearchResultClick,
    onSearchHistoryEntryClick = viewModel::onSearchHistoryEntryClick,
    onRetryClick = viewModel::onRetryClick,
    onAboutClick = viewModel::onAboutClick,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
  viewState: SearchViewState,
  onSearchQueryChange: (String) -> Unit,
  onSearchExpandedChange: (Boolean) -> Unit,
  onBackClick: () -> Unit,
  onClearClick: () -> Unit,
  onSearch: (String) -> Unit,
  onAreaFilterClick: () -> Unit,
  onClimbFilterClick: () -> Unit,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
  onSearchHistoryEntryClick: (String) -> Unit,
  onRetryClick: () -> Unit,
  onAboutClick: () -> Unit,
) = Scaffold { padding ->
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface),
  ) {
    val (map, searchBar, about) = createRefs()

    CragMap(
      modifier = Modifier.constrainAs(map) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)

        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
      },
      textColor = MaterialTheme.colorScheme.primary,
    )

    SearchBar(
      modifier = Modifier
        .wrapContentHeight()
        .constrainAs(searchBar) {
          top.linkTo(parent.top)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        },
      viewState = viewState,
      onSearchQueryChange = { onSearchQueryChange(it) },
      onExpandedChange = { onSearchExpandedChange(it) },
      onBackClick = { onBackClick() },
      onClearClick = { onClearClick() },
      onSearch = { onSearch(it) },
    ) {
      // Necessary because SearchBar doesn't properly handle dark mode colors for items within it's content layout
      Surface(
        color = SearchBarDefaults.colors().containerColor,
      ) {
        when (viewState) {
          is SearchViewState.Loading -> Loading(
            modifier = Modifier.fillMaxSize(),
          )

          is SearchViewState.ShowingHistory -> SearchHistoryList(
            modifier = Modifier.fillMaxSize(),
            history = viewState.searchHistory,
            onSearchHistoryEntryClick = { onSearchHistoryEntryClick(it) },
          )

          is SearchViewState.ShowingResults -> ShowingResults(
            modifier = Modifier.fillMaxSize(),
            viewState = viewState,
            onAreaFilterClick = { onAreaFilterClick() },
            onClimbFilterClick = { onClimbFilterClick() },
            onAreaSearchResultClick = { onAreaSearchResultClick(it) },
            onClimbSearchResultClick = { onClimbSearchResultClick(it) },
          )

          is SearchViewState.NetworkError -> NetworkError(
            modifier = Modifier.fillMaxSize(),
            onRetryClick = { onRetryClick() },
          )

          is SearchViewState.UnknownError -> UnknownError(
            modifier = Modifier.fillMaxSize(),
            onRetryClick = { onRetryClick() },
          )
        }
      }
    }

    TextButton(
      onClick = onAboutClick,
      modifier = Modifier
        .padding(
          end = 16.dp,
          bottom = padding.calculateBottomPadding(),
        )
        .constrainAs(about) {
          bottom.linkTo(parent.bottom)
          end.linkTo(parent.end)
        },
    ) {
      Text(
        text = stringResource(R.string.search_screen_about_button_label),
        color = Color.White,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
  modifier: Modifier = Modifier,
  viewState: SearchViewState,
  onSearchQueryChange: (String) -> Unit,
  onExpandedChange: (Boolean) -> Unit,
  onBackClick: () -> Unit,
  onClearClick: () -> Unit,
  onSearch: (String) -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) = SearchBar(
  inputField = {
    SearchBarInputField(
      viewState = viewState,
      onSearchQueryChange = { onSearchQueryChange(it) },
      onExpandedChange = { onExpandedChange(it) },
      onSearch = { onSearch(it) },
      onBackClick = { onBackClick() },
      onClearClick = { onClearClick() },
    )
  },
  expanded = viewState.searchExpanded,
  onExpandedChange = { onExpandedChange(it) },
  modifier = modifier,
  content = content,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarInputField(
  modifier: Modifier = Modifier,
  viewState: SearchViewState,
  onSearchQueryChange: (String) -> Unit,
  onExpandedChange: (Boolean) -> Unit,
  onSearch: (String) -> Unit,
  onBackClick: () -> Unit,
  onClearClick: () -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  InputField(
    modifier = modifier,
    query = viewState.searchQuery,
    onQueryChange = { onSearchQueryChange(it) },
    onSearch = {
      onSearch(it)
      keyboardController?.hide()
    },
    expanded = viewState.searchExpanded,
    onExpandedChange = { onExpandedChange(it) },
    enabled = true,
    placeholder = { SearchPlaceholder() },
    leadingIcon = {
      SearchBarLeadingIcon(
        searchActive = viewState.searchExpanded,
        onBackClick = { onBackClick() },
      )
    },
    trailingIcon = {
      SearchBarTrailingIcon(
        searchActive = viewState.searchExpanded,
        searchQuery = viewState.searchQuery,
        onClick = { onClearClick() },
      )
    },
  )
}

@Composable
private fun SearchPlaceholder() = Text(stringResource(R.string.search_screen_search_placeholder))

@Composable
private fun SearchBarLeadingIcon(
  searchActive: Boolean,
  onBackClick: () -> Unit,
) = if (searchActive) {
  Icon(
    modifier = Modifier.clickable(
      onClick = { onBackClick() },
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
        onClick = { onClick() },
      ),
      imageVector = Icons.Default.Clear,
      contentDescription = null,
    )
  }
}

@Composable
private fun Loading(
  modifier: Modifier = Modifier,
) = Box(
  modifier = modifier,
  contentAlignment = Alignment.Center,
) {
  CircularProgressIndicator()
}

@Composable
private fun ShowingResults(
  modifier: Modifier,
  viewState: SearchViewState.ShowingResults,
  onAreaFilterClick: () -> Unit,
  onClimbFilterClick: () -> Unit,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
) = Box {
  if (viewState.hasNoResults || viewState.allResultsFiltered) {
    NoResultsError(
      modifier = Modifier.fillMaxSize(),
    )
  }
  SearchResultsList(
    modifier = modifier,
    viewState = viewState,
    onAreaFilterClick = { onAreaFilterClick() },
    onClimbFilterClick = { onClimbFilterClick() },
    onAreaSearchResultClick = { onAreaSearchResultClick(it) },
    onClimbSearchResultClick = { onClimbSearchResultClick(it) },
  )
}

@Composable
private fun SearchResultsList(
  modifier: Modifier,
  viewState: SearchViewState.ShowingResults,
  onAreaFilterClick: () -> Unit,
  onClimbFilterClick: () -> Unit,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
) = LazyColumn(
  modifier = modifier,
  contentPadding = PaddingValues(bottom = 16.dp),
) {
  item {
    FilterRow(
      areaFilterSelected = viewState.areaFilterSelected,
      climbFilterSelected = viewState.climbFilterSelected,
      onAreaFilterClick = { onAreaFilterClick() },
      onClimbFilterClick = { onClimbFilterClick() },
    )
  }
  if (viewState.climbSearchResults.isNotEmpty() && viewState.allFiltersSelected) {
    item { ClimbSearchResultsHeader() }
  }
  if (viewState.climbFilterSelected) {
    itemsIndexed(viewState.climbSearchResults) { index, searchResult ->
      ClimbSearchResult(
        result = searchResult,
        onClick = { onClimbSearchResultClick(it) },
      )
      if (index < viewState.climbSearchResults.size - 1) {
        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp),
        )
      }
    }
  }
  if (viewState.areaSearchResults.isNotEmpty() && viewState.allFiltersSelected) {
    item { AreaSearchResultsHeader() }
  }
  if (viewState.areaFilterSelected) {
    itemsIndexed(viewState.areaSearchResults) { index, areaSearchResult ->
      AreaSearchResult(
        result = areaSearchResult,
        onClick = { onAreaSearchResultClick(it) },
      )
      if (index < viewState.areaSearchResults.size - 1) {
        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp),
        )
      }
    }
  }
}

@Composable
private fun SearchHistoryList(
  modifier: Modifier,
  history: List<String>,
  onSearchHistoryEntryClick: (String) -> Unit,
) = LazyColumn(
  modifier = modifier,
) {
  items(history) {
    SearchHistoryEntry(
      text = it,
      onClick = { onSearchHistoryEntryClick(it) },
    )
  }
}

@Composable
private fun SearchHistoryEntry(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  ListItem(
    modifier = modifier
      .clickable {
        keyboardController?.hide()
        onClick()
      },
    leadingContent = {
      Icon(
        imageVector = Icons.Default.History,
        contentDescription = null,
      )
    },
    headlineContent = { Text(text) },
    colors = ListItemDefaults.colors(
      containerColor = Color.Transparent,
    ),
  )
}

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
    onClick = { onAreaFilterClick() },
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
    onClick = { onClimbFilterClick() },
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
private fun AreaSearchResultsHeader() = Text(
  modifier = Modifier.padding(
    top = 8.dp,
    start = 16.dp,
    end = 16.dp,
  ),
  text = stringResource(R.string.search_screen_areas_header),
  style = MaterialTheme.typography.titleLarge,
)

@Composable
private fun AreaSearchResult(
  result: AreaSearchResult,
  onClick: (String) -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  ListItem(
    modifier = Modifier.clickable {
      keyboardController?.hide()
      onClick(result.id)
    },
    headlineContent = { AreaSearchResultTitle(result) },
    colors = ListItemDefaults.colors(
      containerColor = Color.Transparent,
    ),
  )
}

@Composable
private fun AreaSearchResultTitle(result: AreaSearchResult) {
  val lastPathTokenStartIndex = result.pathText.length - result.pathTokens.last().length
  val spanStyles = listOf(
    AnnotatedString.Range(
      SpanStyle(
        fontWeight = FontWeight.Bold,
      ),
      start = lastPathTokenStartIndex,
      end = result.pathText.length,
    ),
  )

  val titleString = AnnotatedString(
    text = result.pathText,
    spanStyles = spanStyles,
  )

  Text(
    text = titleString,
    overflow = TextOverflow.Ellipsis,
  )
}

@Composable
private fun ClimbSearchResultsHeader() = Text(
  modifier = Modifier.padding(
    top = 8.dp,
    start = 16.dp,
    end = 16.dp,
  ),
  text = stringResource(R.string.search_screen_climbs_header),
  style = MaterialTheme.typography.headlineSmall,
)

@Composable
private fun ClimbSearchResult(
  result: ClimbSearchResult,
  onClick: (String) -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  ListItem(
    modifier = Modifier.clickable {
      keyboardController?.hide()
      onClick(result.id)
    },
    overlineContent = {
      Text(
        text = result.pathText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    },
    headlineContent = { Text(result.name) },
    supportingContent = { Text(result.subtitle) },
    colors = ListItemDefaults.colors(
      containerColor = Color.Transparent,
    ),
  )
}

@Composable
private fun NoResultsError(
  modifier: Modifier = Modifier,
) = ErrorPlaceholder(
  modifier = modifier,
  image = Icons.Rounded.Search,
  message = stringResource(R.string.search_screen_no_results_message),
)

@Composable
private fun NetworkError(
  modifier: Modifier = Modifier,
  onRetryClick: () -> Unit,
) = ErrorPlaceholder(
  modifier = modifier,
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
  modifier = modifier,
  image = Icons.Rounded.Warning,
  message = stringResource(R.string.common_generic_error_message),
  showRetry = true,
  onRetryClick = { onRetryClick() },
)

@PreviewLightDark
@Composable
private fun InactivePreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState.ShowingHistory(),
    onSearchQueryChange = { },
    onSearchExpandedChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
    onSearchHistoryEntryClick = { },
    onRetryClick = { },
    onAboutClick = { },
  )
}

@PreviewLightDark
@Composable
private fun NoResultsPreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState.ShowingResults(
      searchExpanded = true,
      searchQuery = "Atlantis",
    ),
    onSearchQueryChange = { },
    onSearchExpandedChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
    onSearchHistoryEntryClick = { },
    onRetryClick = { },
    onAboutClick = { },
  )
}

@PreviewLightDark
@Composable
private fun ShowingSearchResultsPreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState.ShowingResults(
      searchExpanded = true,
      searchQuery = "Atlantis",
      climbSearchResults = persistentListOf(
        ClimbSearchResult(
          id = "c1",
          name = "Atlantis",
          pathTokens = persistentListOf("USA", "Utah", "Southwest Utah", "Saint George", "Black Rocks", "Shady Side"),
          grade = "5.10b",
          type = "sport",
        ),
      ),
      areaSearchResults = persistentListOf(
        AreaSearchResult(
          id = "a1",
          name = "Atlantis",
          pathTokens = persistentListOf("USA", "Arizona", "Central Arizona", "Queen Creek Canyon", "Atlantis"),
          totalClimbs = 50,
        ),
        AreaSearchResult(
          id = "a2",
          name = "Atlantis Area",
          pathTokens = persistentListOf(
            "USA",
            "California",
            "Joshua Tree National Park",
            "Lost Horse Area",
            "Atlantis Area",
          ),
          totalClimbs = 50,
        ),
      ),
    ),
    onSearchQueryChange = { },
    onSearchExpandedChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
    onSearchHistoryEntryClick = { },
    onRetryClick = { },
    onAboutClick = { },
  )
}

@PreviewLightDark
@Composable
private fun ShowingHistoryPreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState.ShowingHistory(
      searchExpanded = true,
      searchQuery = "",
      searchHistory = persistentListOf(
        "Atlantis",
        "The Pond",
        "Yosemite",
      ),
    ),
    onSearchQueryChange = { },
    onSearchExpandedChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
    onSearchHistoryEntryClick = { },
    onRetryClick = { },
    onAboutClick = { },
  )
}

@PreviewLightDark
@Composable
private fun NetworkErrorPreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState.NetworkError(
      searchExpanded = true,
      searchQuery = "",
    ),
    onSearchQueryChange = { },
    onSearchExpandedChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
    onSearchHistoryEntryClick = { },
    onRetryClick = { },
    onAboutClick = { },
  )
}

@PreviewLightDark
@Composable
private fun UnknownErrorPreview() = RouteSearchTheme {
  SearchScreenContent(
    viewState = SearchViewState.UnknownError(
      searchExpanded = true,
      searchQuery = "",
    ),
    onSearchQueryChange = { },
    onSearchExpandedChange = { },
    onBackClick = { },
    onClearClick = { },
    onSearch = { },
    onAreaFilterClick = { },
    onClimbFilterClick = { },
    onAreaSearchResultClick = { },
    onClimbSearchResultClick = { },
    onSearchHistoryEntryClick = { },
    onRetryClick = { },
    onAboutClick = { },
  )
}
