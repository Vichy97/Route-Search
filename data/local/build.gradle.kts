plugins {
  `android-library-convention`
  id(libs.plugins.ksp.get().pluginId)
}
android {
  namespace = "com.routesearch.data.local"
}
dependencies {
  annotationProcessor(libs.room.compiler)

  ksp(libs.room.compiler)

  api(libs.room.runtime)

  implementation(libs.collection)
  implementation(libs.logcat)
  implementation(libs.room.common)
  implementation(libs.room.ktx)
  implementation(libs.sqlite)
}
