package com.routesearch.di

import android.app.Application
import org.junit.jupiter.api.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

class AppModuleTest : KoinTest {

  @Test
  fun checkKoinModule() = koinApplication {
    androidContext(mock(Application::class.java))
    modules(appModule)
  }.checkModules()
}
