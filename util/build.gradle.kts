plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.util"
}
dependencies {
  api(libs.koin.core)

  implementation(platform(libs.koin.bom))
}
