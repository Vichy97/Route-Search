plugins {
  `android-library-convention`
  id(libs.plugins.ksp.get().pluginId)
}
android {
  namespace = "com.routesearch.data.local"
}
dependencies {
  ksp(libs.room.compiler)

  api(libs.koin.core)
  api(libs.room.runtime)

  implementation(platform(libs.koin.bom))

  implementation(project(":util:common"))

  implementation(libs.annotation)
  implementation(libs.collection)
  implementation(libs.datastore.core)
  implementation(libs.datastore.preferences)
  implementation(libs.datastore.preferences.core)
  implementation(libs.koin.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.logcat)
  implementation(libs.room.common)
  implementation(libs.room.ktx)
  implementation(libs.sqlite)

  annotationProcessor(libs.room.compiler)
}
