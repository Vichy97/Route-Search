import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  id(libs.plugins.androidApplication.get().pluginId)
  id(libs.plugins.kotlinAndroid.get().pluginId)
}
android {
  namespace = "com.routesearch"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.routesearch"
    minSdk = 28
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    vectorDrawables {
      useSupportLibrary = true
    }
  }
  buildTypes {
    debug {
      isMinifyEnabled = false
    }
    release {
      isMinifyEnabled = false
    }
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
  kotlin {
    jvmToolchain(17)
  }
  buildFeatures {
    buildConfig = true
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}
dependencyAnalysis {
  issues {
    onUnusedDependencies {
      exclude(libs.leakcanary.android.get().toString())
    }
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
dependencies {
  implementation(platform(libs.compose.bom))

  implementation(project(":data"))
  implementation(project(":ui-common"))

  implementation(libs.activity)
  implementation(libs.activity.compose)
  implementation(libs.animation.android)
  implementation(libs.foundation.layout.android)
  implementation(libs.koin.android)
  implementation(libs.koin.core)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.logcat)
  implementation(libs.material3)
  implementation(libs.navigation.common)
  implementation(libs.navigation.compose)
  implementation(libs.navigation.runtime)
  implementation(libs.runtime.android)
  implementation(libs.ui.android)
  implementation(libs.ui.text.android)
  implementation(libs.ui.tooling.preview.android)

  debugImplementation(libs.leakcanary.android)

  debugRuntimeOnly(libs.ui.test.manifest)
  debugRuntimeOnly(libs.ui.tooling)

  testImplementation(libs.junit.jupiter)
  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.koin.test)
  testImplementation(libs.mockito.core)
}
