import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

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
  sourceSets.getByName("androidTest") {
    java.srcDir("src/androidTest/kotlin")
  }
  sourceSets.getByName("main") {
    java.srcDir("src/main/kotlin")
  }
  sourceSets.getByName("test") {
    java.srcDir("src/test/kotlin")
  }
}
tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    exceptionFormat = TestExceptionFormat.FULL
    events = setOf(
      TestLogEvent.SKIPPED,
      TestLogEvent.PASSED,
      TestLogEvent.FAILED,
    )
    showStandardStreams = true
  }
}
