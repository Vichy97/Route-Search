package com.routesearch.di

import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkKoinModules

class AppModuleTest : KoinTest {

  @Test
  fun checkKoinModule() {

    checkKoinModules(appModule)
  }
}
