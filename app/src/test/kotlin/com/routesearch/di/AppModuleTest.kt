package com.routesearch.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

class AppModuleTest {

  @OptIn(KoinExperimentalAPI::class)
  @Test
  fun checkKoinModule() = appModule.verify(
    extraTypes = listOf(
      SavedStateHandle::class,
      Context::class,
    ),
  )
}
