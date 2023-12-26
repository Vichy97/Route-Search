package com.routesearch.features.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.routesearch.data.search.AreaSearchResult
import com.routesearch.data.search.ClimbSearchResult
import com.routesearch.features.R
import com.routesearch.ui.common.Screen
import com.routesearch.ui.common.theme.RouteSearchTheme
import org.koin.androidx.compose.koinViewModel

object SearchScreen : Screen {

  override val route = "search"

  @Composable
  override fun Content() {
    val viewModel = koinViewModel<SearchViewModel>()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    SearchScreenContent(
      viewState = viewState,
      onSearch = viewModel::onSearch,
      onSearchQueryChange = viewModel::onSearchQueryChange,
      onAreaSearchResultClick = viewModel::onAreaSearchResultClick,
      onClimbSearchResultClick = viewModel::onClimbSearchResultClick,
    )
  }
}

@Composable
private fun SearchScreenContent(
  viewState: SearchViewState,
  onSearchQueryChange: (String) -> Unit,
  onSearch: (String) -> Unit,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
) = ConstraintLayout(
  modifier = Modifier
    .fillMaxSize(),
) {
  val (
    searchBar,
  ) = createRefs()

  SearchBar(
    modifier = Modifier
      .wrapContentHeight()
      .constrainAs(searchBar) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
      },
    onSearchQueryChanged = onSearchQueryChange,
    onSearch = onSearch,
  ) {
    SearchResultsList(
      modifier = Modifier.fillMaxSize(),
      areaSearchResults = viewState.areaSearchResults,
      climbSearchResults = viewState.climbSearchResults,
      onAreaSearchResultClick = onAreaSearchResultClick,
      onClimbSearchResultClick = onClimbSearchResultClick,
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
  modifier: Modifier,
  onSearchQueryChanged: (String) -> Unit,
  onSearch: (String) -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) {
  var query by rememberSaveable { mutableStateOf("") }
  var active by rememberSaveable { mutableStateOf(false) }

  SearchBar(
    modifier = modifier,
    query = query,
    onQueryChange = {
      onSearchQueryChanged(it)
      query = it
    },
    onSearch = onSearch,
    active = active,
    onActiveChange = { active = it },
    placeholder = { Text(stringResource(R.string.search_screen_search_placeholder)) },
    leadingIcon = {
      if (active) {
        Icon(
          modifier = Modifier.clickable { active = false },
          imageVector = Icons.Default.ArrowBack,
          contentDescription = null,
        )
      } else {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = null,
        )
      }
    },
    content = content,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResultsList(
  modifier: Modifier,
  areaSearchResults: List<AreaSearchResult>,
  climbSearchResults: List<ClimbSearchResult>,
  onAreaSearchResultClick: (String) -> Unit,
  onClimbSearchResultClick: (String) -> Unit,
) = LazyColumn(
  modifier = modifier,
) {
  if (climbSearchResults.isNotEmpty()) {
    stickyHeader { ClimbSearchResultsHeader() }
  }
  items(climbSearchResults) {
    ClimbSearchResult(
      result = it,
      onClick = onClimbSearchResultClick,
    )
  }
  if (areaSearchResults.isNotEmpty()) {
    stickyHeader { AreaSearchResultsHeader() }
  }
  items(areaSearchResults) {
    AreaSearchResult(
      result = it,
      onClick = onAreaSearchResultClick,
    )
  }
}

@Composable
fun AreaSearchResultsHeader() = Text(
  text = "Areas",
  style = MaterialTheme.typography.headlineSmall,
)

@Composable
private fun AreaSearchResult(
  result: AreaSearchResult,
  onClick: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClick(result.id) },
  headlineContent = { Text(result.name) },
  supportingContent = { Text(result.pathTokens.joinToString(" / ")) },
)

@Composable
fun ClimbSearchResultsHeader() = Text(
  text = "Climbs",
  style = MaterialTheme.typography.headlineSmall,
)

@Composable
private fun ClimbSearchResult(
  result: ClimbSearchResult,
  onClick: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClick(result.id) },
  headlineContent = { Text(result.name) },
)

@Preview
@Composable
fun SearchScreenPreview() = RouteSearchTheme {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
  ) {
    SearchScreenContent(
      viewState = SearchViewState(),
      onSearch = { },
      onSearchQueryChange = { },
      onAreaSearchResultClick = { },
      onClimbSearchResultClick = { },
    )
  }
}