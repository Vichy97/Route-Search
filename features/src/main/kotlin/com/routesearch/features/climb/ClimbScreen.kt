package com.routesearch.features.climb

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.routesearch.data.climb.Climb
import com.routesearch.features.R
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
    )

    is ClimbViewState.Loading -> Loading()
    ClimbViewState.Idle -> Unit
  }
}

@Composable
private fun Loading() = Box(
  modifier = Modifier.fillMaxSize(),
  contentAlignment = Alignment.Center,
) { CircularProgressIndicator() }

@Composable
private fun Content(
  climb: Climb,
) = ConstraintLayout(
  modifier = Modifier.fillMaxSize(),
) {
  val (
    title,
    descriptionHeader,
    description,
    locationHeader,
    location,
    protectionHeader,
    protection,
  ) = createRefs()

  Text(
    modifier = Modifier
      .constrainAs(title) {
        top.linkTo(parent.top)
        bottom.linkTo(descriptionHeader.top)
      }
      .padding(horizontal = 16.dp),
    text = climb.name,
    style = MaterialTheme.typography.headlineLarge,
  )
  Text(
    modifier = Modifier
      .constrainAs(descriptionHeader) {
        top.linkTo(title.bottom)
        bottom.linkTo(description.top)
      }
      .padding(horizontal = 16.dp),
    text = stringResource(R.string.climb_screen_description_header),
    style = MaterialTheme.typography.headlineMedium,
  )
  Text(
    modifier = Modifier
      .constrainAs(description) {
        top.linkTo(descriptionHeader.bottom)
        bottom.linkTo(locationHeader.top)
      }
      .padding(horizontal = 16.dp),
    text = climb.description.general,
    style = MaterialTheme.typography.bodyMedium,
  )
  Text(
    modifier = Modifier
      .constrainAs(locationHeader) {
        top.linkTo(description.bottom)
        bottom.linkTo(location.top)
      }
      .padding(horizontal = 16.dp),
    text = stringResource(R.string.climb_screen_location_header),
    style = MaterialTheme.typography.headlineMedium,
  )
  Text(
    modifier = Modifier
      .constrainAs(location) {
        top.linkTo(locationHeader.bottom)
        bottom.linkTo(protectionHeader.top)
      }
      .padding(horizontal = 16.dp),
    text = climb.description.location,
    style = MaterialTheme.typography.bodyMedium,
  )
  Text(
    modifier = Modifier
      .constrainAs(protectionHeader) {
        top.linkTo(location.bottom)
        bottom.linkTo(protection.top)
      }
      .padding(horizontal = 16.dp),
    text = stringResource(R.string.climb_screen_protection_header),
    style = MaterialTheme.typography.headlineMedium,
  )
  Text(
    modifier = Modifier
      .constrainAs(protection) {
        top.linkTo(protectionHeader.bottom)
        bottom.linkTo(parent.bottom)
      }
      .padding(horizontal = 16.dp),
    text = climb.description.protection,
    style = MaterialTheme.typography.bodyMedium,
  )
}

@Preview
@Composable
private fun ClimbScreenPreview() = MaterialTheme {
  Surface {
    Content(
      Climb(
        id = "1",
        metadata = Climb.Metadata(
          leftRightIndex = null,
          createdAt = null,
          updatedAt = null,
        ),
        name = "Here is a climb!",
        pathTokens = emptyList(),
        location = null,
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
    )
  }
}
