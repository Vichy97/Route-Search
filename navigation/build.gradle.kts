plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.navigation"
}
dependencies {
  api(libs.koin.core)
  api(libs.kotlinx.coroutines.core)
  api(libs.navigation.common)

  implementation(platform(libs.koin.bom))
}
