package com.routesearch.data.climb

import androidx.compose.runtime.Immutable
import com.routesearch.data.location.Location
import kotlinx.datetime.LocalDate

@Immutable
data class Climb(
  val id: String,
  val metadata: Metadata,
  val name: String,
  val pathTokens: List<String>,
  val location: Location?,
  val ancestorIds: List<String>,
  val description: Description,
  val length: Int?,
  val boltCount: Int?,
  val fa: String,
  val type: Type?,
  val grades: Grades?,
  val pitches: List<Pitch>,
  val media: List<String>,
) {

  val hasMetadata = location != null && metadata.createdAt != null && metadata.updatedAt != null

  @Immutable
  data class Metadata(
    val leftRightIndex: Int?,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?,
  )

  @Immutable
  data class Description(
    val general: String,
    val location: String,
    val protection: String,
  )
}
