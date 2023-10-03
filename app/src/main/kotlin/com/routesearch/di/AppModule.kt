package com.routesearch.di

import com.routesearch.data.dataModule
import com.routesearch.ui.common.commonUiModule

internal val appModule = dataModule + commonUiModule
