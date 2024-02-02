package com.routesearch.data.area

import com.routesearch.data.remote.AreaQuery

@JvmName("remoteOrganizationsToOrganizations")
internal fun List<AreaQuery.Organization?>.toOrganizations() = filterNotNull().map { it.toOrganization() }

private fun AreaQuery.Organization.toOrganization() = Area.Organization(
  id = orgId.toString(),
  name = displayName,
  website = content?.website,
)
