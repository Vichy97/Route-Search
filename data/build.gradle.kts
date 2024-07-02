plugins {
  `android-library-convention`
  alias(libs.plugins.compose.compiler)
}
android {
  namespace = "com.routesearch.data"

  buildFeatures {
    compose = true
  }
}
dependencies {
  api(libs.koin.core)
  api(libs.kotlinx.collections.immutable)
  api(libs.kotlinx.datetime)

  implementation(platform(libs.coil.bom))
  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))

  implementation(project(":data:local"))
  implementation(project(":data:remote"))
  implementation(project(":util:common"))
  implementation(project(":util:coroutines"))

  implementation(libs.coil.base)
  implementation(libs.koin.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.logcat)
  implementation(libs.runtime.android)
}
