package com.routesearch.data.climb

import androidx.compose.runtime.Immutable
import com.routesearch.data.location.Location
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

@Immutable
data class Climb(
  val id: String,
  val metadata: Metadata,
  val name: String,
  val pathTokens: ImmutableList<String>,
  val location: Location?,
  val ancestorIds: ImmutableList<String>,
  val description: Description,
  val length: Int?,
  val boltCount: Int?,
  val fa: String,
  val types: ImmutableList<Type>,
  val grade: String?,
  val pitches: ImmutableList<Pitch>,
  val media: ImmutableList<String>,
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
