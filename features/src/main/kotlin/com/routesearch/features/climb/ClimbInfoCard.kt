package com.routesearch.features.climb

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.routesearch.features.R
import com.routesearch.ui.common.theme.RouteSearchTheme

@Composable
internal fun ClimbInfoCard(
  modifier: Modifier = Modifier,
  grade: String?,
  type: String?,
  height: Int?,
  pitches: Int,
) = Card(
  modifier = modifier,
) {
  ConstraintLayout(
    modifier = Modifier
      .padding(16.dp),
  ) {
    val (
      gradeText,
      typeText,
      heightText,
      pitchesText,
    ) = createRefs()

    Text(
      modifier = Modifier
        .constrainAs(gradeText) {
          start.linkTo(parent.start)
          top.linkTo(parent.top)

          visibility = if (grade != null) Visibility.Visible else Visibility.Gone
        },
      text = grade ?: stringResource(R.string.climb_info_card_grade_na_label),
      style = MaterialTheme.typography.headlineSmall,
    )

    Text(
      modifier = Modifier
        .constrainAs(typeText) {
          start.linkTo(parent.start)
          top.linkTo(gradeText.bottom)

          visibility = if (type != null) Visibility.Visible else Visibility.Gone
        },
      text = type ?: "",
      style = MaterialTheme.typography.labelLarge,
    )

    Text(
      modifier = Modifier
        .constrainAs(heightText) {
          start.linkTo(parent.start)
          top.linkTo(typeText.bottom)

          visibility = if (height != null) Visibility.Visible else Visibility.Gone
        },
      text = stringResource(
        id = R.string.climb_info_card_height_ft_label,
        formatArgs = arrayOf(height ?: 0),
      ),
      style = MaterialTheme.typography.labelLarge,
    )

    Text(
      modifier = Modifier
        .constrainAs(pitchesText) {
          start.linkTo(parent.start)
          top.linkTo(heightText.bottom)
        },
      text = pluralStringResource(
        id = R.plurals.climb_info_card_number_of_pitches,
        count = pitches,
        formatArgs = arrayOf(pitches),
      ),
      style = MaterialTheme.typography.labelLarge,
    )
  }
}

@PreviewLightDark
@Composable
private fun ClimbInfoCardPreview() = RouteSearchTheme {
  Surface {
    ClimbInfoCard(
      modifier = Modifier.padding(16.dp),
      grade = "5.9",
      type = "Trad",
      height = 700,
      pitches = 10,
    )
  }
}
