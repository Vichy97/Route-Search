package com.routesearch.features.common.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.routesearch.data.location.Location
import com.routesearch.features.R
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.isAnnotatedAtIndex
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.common.date.monthYearFormat
import kotlinx.datetime.LocalDate
import java.time.Month

@Composable
internal fun MetadataCard(
  modifier: Modifier = Modifier,
  location: Location?,
  createdAt: LocalDate?,
  updatedAt: LocalDate?,
  onLocationClick: () -> Unit,
) = Card(
  modifier = modifier,
) {
  ConstraintLayout(
    modifier = Modifier
      .padding(16.dp),
  ) {
    val (
      locationIcon,
      locationText,
      createdAtIcon,
      createdAtText,
      updatedAtIcon,
      updatedAtText,
    ) = createRefs()

    Icon(
      modifier = Modifier
        .constrainAs(locationIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(locationText.bottom)

          visibility = location?.let { Visibility.Visible } ?: Visibility.Gone
        }
        .size(20.dp),
      imageVector = Icons.Default.LocationOn,
      contentDescription = null,
    )
    LocationText(
      modifier = Modifier.constrainAs(locationText) {
        top.linkTo(parent.top)
        start.linkTo(
          anchor = locationIcon.end,
          margin = 4.dp,
        )

        visibility = location?.let { Visibility.Visible } ?: Visibility.Gone
      },
      location = location,
      onClick = onLocationClick,
    )

    Icon(
      modifier = Modifier
        .constrainAs(createdAtIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(createdAtText.bottom)

          visibility = createdAt?.let { Visibility.Visible } ?: Visibility.Gone
        }
        .size(20.dp),
      imageVector = Icons.Default.CalendarMonth,
      contentDescription = null,
    )
    CreatedDateText(
      modifier = Modifier
        .constrainAs(createdAtText) {
          top.linkTo(
            anchor = locationText.bottom,
            margin = 16.dp,
          )
          start.linkTo(
            anchor = createdAtIcon.end,
            margin = 4.dp,
          )

          visibility = createdAt?.let { Visibility.Visible } ?: Visibility.Gone
        },
      created = createdAt,
    )

    Icon(
      modifier = Modifier
        .constrainAs(updatedAtIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(updatedAtText.bottom)

          visibility = updatedAt?.let { Visibility.Visible } ?: Visibility.Gone
        }
        .size(20.dp),
      imageVector = Icons.Default.EditCalendar,
      contentDescription = null,
    )
    UpdatedDateText(
      modifier = Modifier
        .constrainAs(updatedAtText) {
          start.linkTo(
            anchor = updatedAtIcon.end,
            margin = 4.dp,
          )
          top.linkTo(
            anchor = createdAtText.bottom,
            margin = 8.dp,
          )

          visibility = updatedAt?.let { Visibility.Visible } ?: Visibility.Gone
        },
      updated = updatedAt,
    )
  }
}

@Composable
private fun LocationText(
  modifier: Modifier = Modifier,
  location: Location?,
  onClick: () -> Unit,
) {
  val locationAnnotation = "location"
  val locationString = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_location_title)) }

    append(" ")

    underline {
      annotation(locationAnnotation) {
        append(location?.displayString ?: "")
      }
    }
  }
  ClickableText(
    modifier = modifier,
    text = locationString,
    style = MaterialTheme.typography.titleSmall.copy(
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
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
  created: LocalDate?,
) {
  val createdDateText = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_created_title)) }
    append(" ")
    append(created?.monthYearFormat() ?: "")
  }
  Text(
    modifier = modifier,
    text = createdDateText,
    style = MaterialTheme.typography.titleSmall,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

@Composable
private fun UpdatedDateText(
  modifier: Modifier = Modifier,
  updated: LocalDate?,
) {
  val updatedDateText = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_updated_title)) }
    append(" ")
    append(updated?.monthYearFormat() ?: "")
  }
  Text(
    modifier = modifier,
    text = updatedDateText,
    style = MaterialTheme.typography.titleSmall,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

@PreviewLightDark
@Composable
private fun MetadataCardPreview() = RouteSearchTheme {
  Surface {
    MetadataCard(
      modifier = Modifier.padding(16.dp),
      location = Location(
        latitude = 123.45678,
        longitude = 987.6543,
      ),
      createdAt = LocalDate(
        year = 2023,
        month = Month.DECEMBER,
        dayOfMonth = 12,
      ),
      updatedAt = LocalDate(
        year = 2024,
        month = Month.JANUARY,
        dayOfMonth = 1,
      ),
      onLocationClick = { },
    )
  }
}

@PreviewLightDark
@Composable
private fun MetadataCardPreviewNoLocation() = RouteSearchTheme {
  Surface {
    MetadataCard(
      modifier = Modifier.padding(16.dp),
      location = null,
      createdAt = LocalDate(
        year = 2023,
        month = Month.DECEMBER,
        dayOfMonth = 12,
      ),
      updatedAt = LocalDate(
        year = 2024,
        month = Month.JANUARY,
        dayOfMonth = 1,
      ),
      onLocationClick = { },
    )
  }
}

@PreviewLightDark
@Composable
private fun MetadataCardPreviewNoDates() = RouteSearchTheme {
  Surface {
    MetadataCard(
      modifier = Modifier.padding(16.dp),
      location = Location(
        latitude = 123.45678,
        longitude = 987.6543,
      ),
      createdAt = null,
      updatedAt = null,
      onLocationClick = { },
    )
  }
}
