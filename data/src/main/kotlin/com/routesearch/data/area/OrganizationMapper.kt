package com.routesearch.data.area

import com.routesearch.data.remote.AreaQuery
import kotlinx.collections.immutable.toImmutableList

@JvmName("remoteOrganizationsToOrganizations")
internal fun List<AreaQuery.Organization?>.toOrganizations() = filterNotNull().map { it.toOrganization() }
  .toImmutableList()

private fun AreaQuery.Organization.toOrganization() = Area.Organization(
  id = orgId.toString(),
  name = displayName,
  website = content?.website,
  description = content?.description,
  facebookUrl = content?.facebookLink,
  instagramUrl = content?.instagramLink,
)
