plugins {
  `android-library-convention`
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
  api(project(":data"))
  api(project(":ui-common"))

  api(libs.foundation.android)
  api(libs.foundation.layout.android)
  api(libs.navigation.common)
  api(libs.runtime.android)

  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))

  implementation(project(":navigation"))
  implementation(project(":util:common"))

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
  implementation(libs.lifecycle.viewmodel.savedstate)
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
