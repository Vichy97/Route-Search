package com.routesearch.features

import com.routesearch.features.about.aboutModule
import com.routesearch.features.area.areaModule
import com.routesearch.features.climb.climbModule
import com.routesearch.features.gallery.galleryModule
import com.routesearch.features.imageviewer.imageViewerModule
import com.routesearch.features.organization.organizationModule
import com.routesearch.features.search.searchModule
import org.koin.dsl.module

val featuresModule = module {

  includes(
    areaModule,
    searchModule,
    climbModule,
    galleryModule,
    organizationModule,
    aboutModule,
    imageViewerModule,
  )
}
