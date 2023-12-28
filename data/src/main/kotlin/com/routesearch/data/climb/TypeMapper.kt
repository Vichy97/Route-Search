package com.routesearch.data.climb

import com.routesearch.data.remote.fragment.TypeFragment as RemoteType

internal fun RemoteType.toType() = when {
  aid == true -> Type.AID
  bouldering == true -> Type.BOULDERING
  alpine == true -> Type.ALPINE
  deepwatersolo == true -> Type.DEEP_WATER_SOLO
  ice == true -> Type.ICE
  mixed == true -> Type.MIXED
  sport == true -> Type.SPORT
  snow == true -> Type.SNOW
  tr == true -> Type.TR
  trad == true -> Type.TRAD
  else -> throw IllegalArgumentException("Invalid type $this")
}

internal fun String.toType() = Type.valueOf(this)
