plugins {
  `android-library-convention`
}
android {
  namespace = "com.route_search.data"
}
dependencies {
  api(libs.koin.core)

  implementation(project(":data:network"))
}
