import org.gradle.api.JavaVersion
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
  /*
   * This plugin notation (as opposed to referencing the version catalog) is a limitation of the version catalog in
   * precompiled script plugins. See here https://github.com/gradle/gradle/issues/15383 for more context
   */
  id("org.jetbrains.kotlin.android")
  id("com.android.library")
}

android {
  compileSdk = 34
  lint.targetSdk = 34
  testOptions.targetSdk = 34

  defaultConfig {
    minSdk = 28
  }
  buildTypes {
    debug {
      isMinifyEnabled = false
    }
    release {
      isMinifyEnabled = false
    }
  }
  kotlin {
    jvmToolchain(17)
  }
}