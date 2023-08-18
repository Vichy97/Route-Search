plugins {
  id(libs.plugins.androidApplication.get().pluginId) apply false
  id(libs.plugins.androidLibrary.get().pluginId) apply false
  id(libs.plugins.apollo.get().pluginId).version(libs.plugins.apollo.get().version.toString()) apply false
  id(libs.plugins.kotlinAndroid.get().pluginId) apply false
}