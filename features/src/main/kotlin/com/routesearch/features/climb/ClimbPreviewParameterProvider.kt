package com.routesearch.features.climb

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.routesearch.data.climb.Climb
import com.routesearch.data.location.Location
import kotlinx.datetime.LocalDate
import java.time.Month

internal val fakeClimbs = listOf(
  Climb(
    id = "1",
    metadata = Climb.Metadata(
      leftRightIndex = 1,
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
    ),
    name = "The Totem Pole",
    pathTokens = listOf(
      "USA",
      "Arizona",
      "Central Arizona",
      "Queen Creek Canyon",
      "Lower Devil's Canyon",
      "Totem-Proto Area",
    ),
    location = Location(
      latitude = 31.12,
      longitude = 32.13,
    ),
    ancestorIds = emptyList(),
    description = Climb.Description(
      general = """
            A tall, slender tower in a scenic location with good climbing and a tiny summit make for a great route. 
            Start low on the NE side and climb up and right, slowly circling around to the West side to a belay. 
            Belay here to avoid rope drag or, with a long rope, continue up to the top.
      """.trimIndent(),
      location = """
            Walk down into the main canyon from the parking area. There will be 2 tall towers on the right. The upper 
            one is Proto-pipe (it looks just like one) and the lower one is the Totem Pole.
      """.trimIndent(),
      protection = """
        Mostly bolted, but up high (at the crux) it helps to place a nut or two to reduce the slight runout.
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

@Suppress("Unused")
// Unfortunately we can't actually use PreviewParameterProvider yet because of a bug in Android Studio
internal class ClimbPreviewParameterProvider : PreviewParameterProvider<Climb> {

  override val values = fakeClimbs.asSequence()
}
