plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.ui.common"

  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }
}
dependencies {
  api(libs.annotation)
  api(libs.runtime.android)

  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))

  implementation(libs.core)
  implementation(libs.foundation.android)
  implementation(libs.koin.core)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.material3)
  implementation(libs.ui.android)
  implementation(libs.ui.graphics.android)
  implementation(libs.ui.text.android)
  implementation(libs.ui.unit.android)
}
