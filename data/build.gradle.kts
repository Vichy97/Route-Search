plugins {
  `android-library-convention`
}
android {
  namespace = "com.routesearch.data"
}
dependencies {
  api(libs.koin.core)
  api(libs.kotlinx.datetime)

  implementation(platform(libs.koin.bom))

  implementation(project(":data:local"))
  implementation(project(":data:remote"))
  implementation(project(":util:common"))
  implementation(project(":util:coroutines"))

  implementation(libs.kotlinx.coroutines.core)
}
