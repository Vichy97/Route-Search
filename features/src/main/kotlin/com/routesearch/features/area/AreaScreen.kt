package com.routesearch.features.area

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.area.Area
import com.routesearch.data.climb.Grades
import com.routesearch.data.climb.Type
import com.routesearch.features.R
import com.routesearch.ui.common.interaction.NoOpInteractionSource
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
      onClimbClick = viewModel::onClimbClicked,
      onChildClick = viewModel::onAreaClicked,
      onDownloadClick = viewModel::onDownloadClicked,
      onShareClick = viewModel::onSharedClicked,
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

@Composable
private fun Content(
  area: Area,
  onClimbClick: (String) -> Unit,
  onChildClick: (String) -> Unit,
  onDownloadClick: () -> Unit,
  onShareClick: () -> Unit,
) = ConstraintLayout(
  modifier = Modifier.fillMaxSize(),
) {
  val (
    areaTitle,
    areaDescription,
    seeMoreButton,
    downloadButton,
    shareButton,
    areaContent,
  ) = createRefs()

  Text(
    modifier = Modifier
      .constrainAs(areaTitle) {
        bottom.linkTo(areaDescription.top)
      }
      .padding(horizontal = 16.dp),
    text = area.name,
    style = MaterialTheme.typography.headlineLarge,
    color = MaterialTheme.colorScheme.inverseOnSurface,
  )

  var descriptionExpanded by rememberSaveable { mutableStateOf(false) }
  Description(
    modifier = Modifier
      .constrainAs(areaDescription) {
        bottom.linkTo(seeMoreButton.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        width = Dimension.fillToConstraints
      }
      .padding(horizontal = 16.dp),
    expanded = descriptionExpanded,
    text = area.description,
  )

  TextButton(
    onClick = { descriptionExpanded = !descriptionExpanded },
    interactionSource = NoOpInteractionSource(),
    modifier = Modifier.constrainAs(seeMoreButton) {
      top.linkTo(areaDescription.bottom)
    },
  ) {
    Text(
      text = if (descriptionExpanded) {
        stringResource(R.string.area_screen_see_less)
      } else {
        stringResource(R.string.area_screen_see_more)
      },
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.inversePrimary,
    )
  }
  DownloadButton(
    modifier = Modifier.constrainAs(downloadButton) {
      top.linkTo(seeMoreButton.bottom)
      start.linkTo(parent.start)
      end.linkTo(shareButton.start)
    },
    onClick = onDownloadClick,
  )
  ShareButton(
    modifier = Modifier.constrainAs(shareButton) {
      top.linkTo(seeMoreButton.bottom)
      start.linkTo(downloadButton.end)
      end.linkTo(parent.end)
    },
    onClick = onShareClick,
  )

  if (area.children.isEmpty()) {
    ClimbList(
      area = area,
      onClimbClicked = onClimbClick,
      modifier = Modifier
        .constrainAs(areaContent) {
          top.linkTo(downloadButton.bottom)
          bottom.linkTo(parent.bottom)
        },
    )
  } else {
    AreaList(
      area = area,
      onChildClicked = onChildClick,
      modifier = Modifier
        .constrainAs(areaContent) {
          top.linkTo(downloadButton.bottom)
          bottom.linkTo(parent.bottom)
        },
    )
  }
}

@Composable
private fun Description(
  modifier: Modifier,
  expanded: Boolean,
  text: String,
) = if (expanded) {
  ExpandedDescription(
    modifier = modifier,
    text = text,
  )
} else {
  CollapsedDescription(
    modifier = modifier,
    text = text,
  )
}

@Composable
private fun ExpandedDescription(
  modifier: Modifier,
  text: String,
) = Text(
  modifier = modifier
    .verticalScroll(rememberScrollState())
    .padding(horizontal = 16.dp),
  text = text,
  style = MaterialTheme.typography.bodyMedium,
  color = MaterialTheme.colorScheme.inverseOnSurface,
)

@Composable
private fun CollapsedDescription(
  modifier: Modifier,
  text: String,
) = Text(
  modifier = modifier,
  text = text,
  style = MaterialTheme.typography.bodyMedium,
  maxLines = 2,
  overflow = TextOverflow.Ellipsis,
  color = MaterialTheme.colorScheme.inverseOnSurface,
)

@Composable
private fun DownloadButton(
  modifier: Modifier,
  onClick: () -> Unit,
) = OutlinedButton(
  onClick = onClick,
  contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
  modifier = modifier,
) {
  Icon(
    modifier = Modifier.size(ButtonDefaults.IconSize),
    imageVector = Icons.Filled.ArrowDropDown,
    contentDescription = null,
  )
  Spacer(Modifier.size(ButtonDefaults.IconSpacing))
  Text(stringResource(R.string.area_screen_download_button))
}

@Composable
private fun ShareButton(
  modifier: Modifier,
  onClick: () -> Unit,
) = OutlinedButton(
  modifier = modifier,
  onClick = onClick,
  contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
) {
  Icon(
    modifier = Modifier.size(ButtonDefaults.IconSize),
    imageVector = Icons.Filled.Share,
    contentDescription = null,
  )
  Spacer(Modifier.size(ButtonDefaults.IconSpacing))
  Text(stringResource(R.string.area_screen_share_button))
}

@Composable
fun AreaChildItem(
  areaChild: Area.Child,
  onChildClicked: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onChildClicked(areaChild.id) },
  headlineContent = { Text(areaChild.name) },
  supportingContent = { Text(areaChild.subtitle()) },
)

@Composable
fun AreaClimbItem(
  climb: Area.Climb,
  onClimbClicked: (String) -> Unit,
) = ListItem(
  modifier = Modifier.clickable { onClimbClicked(climb.id) },
  headlineContent = { Text(climb.name) },
  supportingContent = { Text(climb.type.toString()) },
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
private fun AreaList(
  area: Area,
  modifier: Modifier,
  onChildClicked: (String) -> Unit,
) = LazyColumn(
  modifier = modifier,
) {
  itemsIndexed(
    items = area.children,
    key = { _, item -> item.id },
  ) { index, child ->
    AreaChildItem(
      child,
      onChildClicked = onChildClicked,
    )
    if (index < area.children.size) {
      Divider(
        modifier = Modifier.padding(
          horizontal = 16.dp,
        ),
      )
    }
  }
}

@Composable
private fun ClimbList(
  area: Area,
  modifier: Modifier,
  onClimbClicked: (String) -> Unit,
) = LazyColumn(
  modifier = modifier,
) {
  itemsIndexed(
    items = area.climbs,
    key = { _, item -> item.id },
  ) { index, climb ->
    AreaClimbItem(
      climb,
      onClimbClicked = onClimbClicked,
    )
    if (index < area.totalClimbs) {
      Divider(
        modifier = Modifier.padding(
          horizontal = 16.dp,
        ),
      )
    }
  }
}

@Preview
@Composable
private fun AreaScreenChildrenPreview() = MaterialTheme {
  Surface {
    Content(
      Area(
        id = "1",
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
          "Phoenix",
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
      onShareClick = { },
      onDownloadClick = { },
      onClimbClick = { },
      onChildClick = { },
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
          "Phoenix",
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
            grades = Grades(
              yds = "5.9",
              vScale = null,
            ),
            name = "Linus",
            type = Type.SNOW,
          ),
          Area.Climb(
            id = "5",
            grades = Grades(
              yds = "5.9",
              vScale = null,
            ),
            name = "Stinky",
            type = Type.DEEP_WATER_SOLO,
          ),
        ),
        children = emptyList(),
        media = emptyList(),
      ),
      onShareClick = { },
      onDownloadClick = { },
      onClimbClick = { },
      onChildClick = { },
    )
  }
}
