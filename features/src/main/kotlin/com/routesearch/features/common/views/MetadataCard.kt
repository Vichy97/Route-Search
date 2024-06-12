package com.routesearch.features.common.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import com.routesearch.ui.common.compose.bold
import com.routesearch.ui.common.compose.clickable
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.common.date.monthYearFormat
import kotlinx.datetime.LocalDate
import java.time.Month

@Composable
internal fun MetadataCard(
  modifier: Modifier = Modifier,
  location: Location?,
  createdAt: String?,
  updatedAt: String?,
  firstAscent: String?,
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
      firstAscentIcon,
      firstAscentText,
    ) = createRefs()

    Icon(
      modifier = Modifier
        .constrainAs(locationIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(locationText.bottom)

          visibility = if (location != null) Visibility.Visible else Visibility.Gone
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

        visibility = if (location != null) Visibility.Visible else Visibility.Gone
      },
      location = location,
      onClick = onLocationClick,
    )

    Icon(
      modifier = Modifier
        .constrainAs(createdAtIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(createdAtText.bottom)

          visibility = if (createdAt != null) Visibility.Visible else Visibility.Gone
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

          visibility = if (createdAt != null) Visibility.Visible else Visibility.Gone
        },
      created = createdAt,
    )

    Icon(
      modifier = Modifier
        .constrainAs(updatedAtIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(updatedAtText.bottom)

          visibility = if (updatedAt != null) Visibility.Visible else Visibility.Gone
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

          visibility = if (updatedAt != null) Visibility.Visible else Visibility.Gone
        },
      updated = updatedAt,
    )

    Icon(
      modifier = Modifier
        .constrainAs(firstAscentIcon) {
          start.linkTo(parent.start)
          bottom.linkTo(firstAscentText.bottom)
        }
        .size(20.dp),
      imageVector = Icons.Default.Person,
      contentDescription = null,
    )

    FirstAscentText(
      modifier = Modifier
        .constrainAs(firstAscentText) {
          start.linkTo(
            anchor = firstAscentIcon.end,
            margin = 4.dp,
          )
          top.linkTo(
            anchor = updatedAtText.bottom,
            margin = 8.dp,
            goneMargin = 8.dp,
          )

          visibility = if (firstAscent != null) Visibility.Visible else Visibility.Gone
        },
      firstAscent = firstAscent ?: "",
    )
  }
}

@Composable
private fun LocationText(
  modifier: Modifier = Modifier,
  location: Location?,
  onClick: () -> Unit,
) {
  val locationString = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_location_title)) }

    append(" ")

    underline {
      clickable(
        tag = "location",
        onClick = { onClick() },
      ) {
        append(location?.displayString ?: "")
      }
    }
  }
  Text(
    modifier = modifier,
    text = locationString,
    style = MaterialTheme.typography.titleSmall,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

@Composable
private fun CreatedDateText(
  modifier: Modifier = Modifier,
  created: String?,
) {
  val createdDateText = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_created_title)) }
    append(" ")
    append(created ?: "")
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
  updated: String?,
) {
  val updatedDateText = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_updated_title)) }
    append(" ")
    append(updated ?: "")
  }
  Text(
    modifier = modifier,
    text = updatedDateText,
    style = MaterialTheme.typography.titleSmall,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

@Composable
private fun FirstAscentText(
  modifier: Modifier = Modifier,
  firstAscent: String,
) {
  val firstAscentText = buildAnnotatedString {
    bold { append(stringResource(R.string.metadata_card_fa_title)) }
    append(" ")
    append(firstAscent)
  }
  Text(
    modifier = modifier,
    text = firstAscentText,
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
      ).monthYearFormat(),
      updatedAt = LocalDate(
        year = 2024,
        month = Month.JANUARY,
        dayOfMonth = 1,
      ).monthYearFormat(),
      onLocationClick = { },
      firstAscent = "Joe Williams",
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
      ).monthYearFormat(),
      updatedAt = LocalDate(
        year = 2024,
        month = Month.JANUARY,
        dayOfMonth = 1,
      ).monthYearFormat(),
      onLocationClick = { },
      firstAscent = "Joe Williams",
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
      firstAscent = "Joe Williams",
    )
  }
}
