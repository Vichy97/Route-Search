plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.data"
}
dependencies {
  api(libs.koin.core)

  implementation(project(":data:network"))
}
