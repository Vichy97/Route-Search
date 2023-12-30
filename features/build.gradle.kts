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

  api(libs.compose.destinations.core)
  api(libs.foundation.android)
  api(libs.foundation.layout.android)
  api(libs.lifecycle.viewmodel.savedstate)
  api(libs.navigation.common)
  api(libs.navigation.runtime)
  api(libs.runtime.android)

  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))

  implementation(project(":navigation"))
  implementation(project(":ui-common"))
  implementation(project(":util:common"))

  implementation(libs.annotation)
  implementation(libs.constraintlayout.compose)
  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(libs.koin.compose)
  implementation(libs.koin.core)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.lifecycle.common)
  implementation(libs.lifecycle.runtime.compose)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.lifecycle.viewmodel.ktx)
  implementation(libs.material.icons.core.android)
  implementation(libs.material3)
  implementation(libs.runtime.saveable.android)
  implementation(libs.ui.android)
  implementation(libs.ui.graphics.android)
  implementation(libs.ui.text.android)
  implementation(libs.ui.tooling.preview.android)
  implementation(libs.ui.unit.android)

  debugRuntimeOnly(libs.ui.test.manifest)
  debugRuntimeOnly(libs.ui.tooling)
}
