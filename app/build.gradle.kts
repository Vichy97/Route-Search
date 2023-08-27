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
    java.srcDir("src/test/kotlin")
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
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.3"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
  implementation(project(":data"))

  implementation(libs.core.ktx)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.koin.android)
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.material3)

  debugImplementation(libs.leakcanary.android)
  debugImplementation(libs.ui.tooling)
  debugImplementation(libs.ui.test.manifest)

  testImplementation(libs.junit.jupiter)
  testImplementation(libs.koin.android.test)
}