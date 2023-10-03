package com.routesearch.ui.common.snackbar

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SnackbarManager {

  private val _snackbarEvents = MutableSharedFlow<SnackbarEvent>(extraBufferCapacity = 1)
  val snackbarEvents = _snackbarEvents.asSharedFlow()

  fun showSnackbar(@StringRes message: Int) {
    _snackbarEvents.tryEmit(SnackbarEvent(message))
  }
}
