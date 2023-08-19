plugins {
  `android-library-convention`
  id(libs.plugins.apollo.get().pluginId)
}
android {
  namespace = "com.route_search.network"
}
apollo {
  service("open_beta") {
    packageName.set("com.route_search.network")
  }
  generateKotlinModels.set(true)
}
dependencies {
  implementation(libs.apollo.runtime)
}