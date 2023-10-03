package com.routesearch.ui.common

import com.routesearch.ui.common.snackbar.SnackbarManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonUiModule = module {

  singleOf(::SnackbarManager)
}
