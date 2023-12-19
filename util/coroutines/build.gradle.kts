plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.util.coroutines"
}
dependencies {
  api(libs.koin.core)
  api(libs.kotlinx.coroutines.core)

  implementation(platform(libs.koin.bom))
}
