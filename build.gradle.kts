import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
  id(libs.plugins.androidApplication.get().pluginId) apply false
  id(libs.plugins.androidLibrary.get().pluginId) apply false
  id(libs.plugins.apollo.get().pluginId).version(libs.plugins.apollo.get().version.toString()) apply false
  id(libs.plugins.kotlinAndroid.get().pluginId) apply false
  id(libs.plugins.ktlint.get().pluginId).version(libs.plugins.ktlint.get().version.toString()) apply false
}

subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  configure<KtlintExtension> {
    debug.set(true)
    android.set(true)
  }
}
