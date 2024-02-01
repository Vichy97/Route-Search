package com.routesearch.features.area

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.routesearch.data.area.Area
import com.routesearch.data.climb.Grades
import com.routesearch.data.climb.Type
import com.routesearch.data.location.Location
import kotlinx.datetime.LocalDate
import java.time.Month

private val metadata = Area.Metadata(
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
)
private val location = Location(
  latitude = 33.27466,
  longitude = -111.03448,
)
internal val fakeAreas = listOf(
  Area(
    id = "1",
    metadata = metadata,
    name = "Lower Devil's Canyon",
    description = "",
    path = listOf(
      "USA",
      "Arizona",
      "Central Arizona",
      "Queen Creek Canyon",
      "Lower Devil's Canyon",
    ),
    ancestorIds = emptyList(),
    location = location,
    totalClimbs = 247,
    climbs = emptyList(),
    children = listOf(
      Area.Child(
        id = "1",
        name = "Totem-Proto Area",
        totalClimbs = 20,
        numberOfChildren = 0,
      ),
      Area.Child(
        id = "2",
        name = "Road Area",
        totalClimbs = 13,
        numberOfChildren = 0,
      ),
      Area.Child(
        id = "3",
        name = "Refuge, The",
        totalClimbs = 120,
        numberOfChildren = 9,
      ),
      Area.Child(
        id = "4",
        name = "Lion's Den",
        totalClimbs = 2,
        numberOfChildren = 0,
      ),
    ),
    media = emptyList(),
  ),
  Area(
    id = "2",
    metadata = metadata,
    name = "Lion's Den",
    description = """
        This is a magical glen that seems to get little to no traffic and has a lot of potential for new route 
        development. Will hope to put up some of the ones I've scoped soon. For now, I post the two established climbs 
        -- "[Redacted]" and "Kitties Dihedral" -- and some photos. It should, realistically, be within the 
        "Glitter Box" area, but it's easiest to have it listed as a new area within Lower Devils.
    """.trimIndent(),
    path = listOf(
      "USA",
      "Arizona",
      "Central Arizona",
      "Queen Creek Canyon",
      "Lower Devil's Canyon",
      "Lion's Den",
    ),
    ancestorIds = emptyList(),
    location = location,
    totalClimbs = 2,
    climbs = listOf(
      Area.Climb(
        id = "1",
        name = "[Redacted]",
        grades = Grades(
          yds = "5.8",
          vScale = null,
        ),
        type = Type.SPORT,
      ),
      Area.Climb(
        id = "2",
        name = "Kitties Dihedral",
        grades = Grades(
          yds = "5.7",
          vScale = null,
        ),
        type = Type.SPORT,
      ),
    ),
    children = emptyList(),
    media = emptyList(),
  ),
)

// Unfortunately we can't actually use PreviewParameterProvider yet because of a bug in Android Studio
@Suppress("Unused")
internal class AreaPreviewParameterProvider : PreviewParameterProvider<Area> {

  override val values = fakeAreas.asSequence()
}
