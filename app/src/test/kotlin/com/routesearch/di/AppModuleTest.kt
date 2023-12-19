package com.routesearch.di

import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

class AppModuleTest {

  @OptIn(KoinExperimentalAPI::class)
  @Test
  fun checkKoinModule() = appModule.verify()
}
