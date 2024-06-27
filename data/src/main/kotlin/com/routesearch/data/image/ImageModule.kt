package com.routesearch.data.image

import coil.disk.DiskCache
import coil.memory.MemoryCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val IMAGE_CACHE_DIR = "image_cache"
private const val MEMORY_CACHE_SIZE_PERCENT = 0.25
private const val DISK_CACHE_SIZE_PERCENT = 0.02

internal val imageModule = module {

  single {
    MemoryCache.Builder(androidContext())
      .maxSizePercent(MEMORY_CACHE_SIZE_PERCENT)
      .build()
  }

  single {
    DiskCache.Builder()
      .directory(androidContext().cacheDir.resolve(IMAGE_CACHE_DIR))
      .maxSizePercent(DISK_CACHE_SIZE_PERCENT)
      .build()
  }

  single<coil.ImageLoader> {
    coil.ImageLoader.Builder(androidContext())
      .memoryCache { get<MemoryCache>() }
      .diskCache { get<DiskCache>() }
      .build()
  }

  singleOf(::CoilImageLoader) bind ImageLoader::class
}
