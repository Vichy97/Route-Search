package com.routesearch.features.organization

import androidx.lifecycle.SavedStateHandle
import com.ramcosta.composedestinations.generated.navArgs
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val organizationModule = module {

  viewModelOf(::OrganizationViewModel)

  factory<OrganizationScreenArgs> {
    get<SavedStateHandle>().navArgs<OrganizationScreenArgs>()
  }
}
