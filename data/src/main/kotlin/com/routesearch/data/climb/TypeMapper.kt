package com.routesearch.data.climb

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import com.routesearch.data.remote.fragment.TypeFragment as RemoteType

internal fun RemoteType.toTypes(): ImmutableList<Type> {
  val types = mutableListOf<Type>()

  if (bouldering == true) types.add(Type.BOULDERING)
  if (alpine == true) types.add(Type.ALPINE)
  if (deepwatersolo == true) types.add(Type.DEEP_WATER_SOLO)
  if (ice == true) types.add(Type.ICE)
  if (mixed == true) types.add(Type.MIXED)
  if (snow == true) types.add(Type.SNOW)
  if (sport == true) types.add(Type.SPORT)
  if (trad == true) types.add(Type.TRAD)
  if (tr == true) types.add(Type.TR)
  if (aid == true) types.add(Type.AID)

  return types.toImmutableList()
}

internal fun String.toTypes() = Type.valueOf(this)

fun List<String>.toTypes(): ImmutableList<Type> {
  return this.map { string ->
    Type.valueOf(string)
  }.toImmutableList()
}
