package com.routesearch.ui.common.interaction

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.emptyFlow

class NoOpInteractionSource : MutableInteractionSource {

  override val interactions = emptyFlow<Interaction>()

  override suspend fun emit(interaction: Interaction) = Unit

  override fun tryEmit(interaction: Interaction) = true
}
