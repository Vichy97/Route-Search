plugins {
  `android-library-convention`
  id(libs.plugins.ksp.get().pluginId)
}

android {
  namespace = "com.routesearch.features"

  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }
}

dependencies {
  ksp(libs.compose.destinations.ksp)

  api(project(":data"))

  api(libs.coil.compose.base)
  api(libs.compose.destinations.core)
  api(libs.foundation.android)
  api(libs.foundation.layout.android)
  api(libs.lifecycle.viewmodel.savedstate)
  api(libs.mapbox.compose)
  api(libs.navigation.common)
  api(libs.navigation.runtime)
  api(libs.runtime.android)
  api(libs.ui.android)
  api(libs.ui.text.android)

  implementation(platform(libs.coil.bom))
  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))

  implementation(project(":navigation"))
  implementation(project(":ui-common"))
  implementation(project(":util:app-version"))
  implementation(project(":util:common"))
  implementation(project(":util:markdown"))

  implementation(libs.animation)
  implementation(libs.animation.core)
  implementation(libs.annotation)
  implementation(libs.coil.base)
  implementation(libs.coil.compose)
  implementation(libs.constraintlayout.compose)
  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(libs.koin.compose)
  implementation(libs.koin.core)
  implementation(libs.kotlinx.collections.immutable)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.datetime)
  implementation(libs.lifecycle.common)
  implementation(libs.lifecycle.runtime.compose)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.mapbox.android)
  implementation(libs.mapbox.android.core)
  implementation(libs.mapbox.base)
  implementation(libs.mapbox.style)
  implementation(libs.material.icons.core.android)
  implementation(libs.material.icons.extended.android)
  implementation(libs.material3)
  implementation(libs.runtime.saveable.android)
  implementation(libs.ui.graphics.android)
  implementation(libs.ui.tooling.preview.android)
  implementation(libs.ui.unit.android)
  implementation(libs.vico.compose.m3)
  implementation(libs.vico.core)
  implementation(libs.zoomable)

  debugRuntimeOnly(libs.ui.test.manifest)
  debugRuntimeOnly(libs.ui.tooling)
}
