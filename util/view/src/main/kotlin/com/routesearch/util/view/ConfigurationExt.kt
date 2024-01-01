package com.routesearch.util.view

import android.content.res.Configuration

val Configuration.isInDarkMode: Boolean
  get() = uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
