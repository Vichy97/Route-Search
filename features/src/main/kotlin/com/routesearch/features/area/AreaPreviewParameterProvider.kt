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
  isLeaf = false,
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
    gradeMap = mapOf(
      "5.4" to 1,
      "5.5" to 3,
      "5.6" to 8,
      "5.7" to 21,
      "5.8" to 35,
      "5.9" to 7,
      "5.10" to 92,
      "5.11" to 23,
      "5.12" to 6,
      "5.13" to 1,
    ),
    location = location,
    climbCount = Area.ClimbCount(
      total = 247,
      sport = 90,
      trad = 177,
      tr = 3,
      bouldering = 0,
    ),
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
    organizations = listOf(
      Area.Organization(
        id = "1",
        name = "Central Arizona Bolt Replacement Program",
        website = "https://cabrp.org",
      ),
      Area.Organization(
        id = "2",
        name = "Climbing Association of Southern Arizona",
        website = "https://www.theclimbershome.org",
      ),
      Area.Organization(
        id = "3",
        name = "Queen Creek Coalition",
        website = "http://theqcc.com",
      ),
    ),
    media = emptyList(),
  ),
  Area(
    id = "2",
    metadata = metadata.copy(
      isLeaf = true,
    ),
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
    gradeMap = mapOf(
      "5.7" to 1,
      "5.8" to 1,
    ),
    location = location,
    climbCount = Area.ClimbCount(
      total = 2,
      sport = 2,
      trad = 0,
      tr = 0,
      bouldering = 0,
    ),
    climbs = listOf(
      Area.Climb(
        id = "1",
        name = "[Redacted]",
        grades = Grades(
          yds = "5.8",
          vScale = null,
        ),
        type = Type.SPORT,
        numberOfPitches = 1,
      ),
      Area.Climb(
        id = "2",
        name = "Kitties Dihedral",
        grades = Grades(
          yds = "5.7",
          vScale = null,
        ),
        type = Type.SPORT,
        numberOfPitches = 1,
      ),
    ),
    children = emptyList(),
    organizations = emptyList(),
    media = emptyList(),
  ),
)

// Unfortunately we can't actually use PreviewParameterProvider yet because of a bug in Android Studio
@Suppress("Unused")
internal class AreaPreviewParameterProvider : PreviewParameterProvider<Area> {

  override val values = fakeAreas.asSequence()
}
