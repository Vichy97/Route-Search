plugins {
  `android-library-convention`
  alias(libs.plugins.compose.compiler)
}
android {
  namespace = "com.routesearch.ui.common"

  buildFeatures {
    compose = true
  }
}
dependencies {
  api(libs.annotation)
  api(libs.foundation.android)
  api(libs.runtime.android)
  api(libs.ui.android)
  api(libs.ui.text.android)

  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))
  implementation(libs.koin.core)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.logcat)
  implementation(libs.material3)
  implementation(libs.ui.graphics.android)
  implementation(libs.ui.unit.android)
}
