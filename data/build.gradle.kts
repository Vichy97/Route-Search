plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.data"
}
dependencies {
  api(libs.koin.core)

  implementation(platform(libs.koin.bom))

  implementation(project(":data:local"))
  implementation(project(":data:remote"))
}
