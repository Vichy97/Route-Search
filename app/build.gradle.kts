import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.util.Properties

plugins {
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)

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

    val localPropertiesFile = project.rootProject.file("local.properties")
    val properties = Properties()
    properties.load(localPropertiesFile.inputStream())
    buildConfigField(
      type = "String",
      name = "MAPBOX_ACCESS_TOKEN",
      value = "\"${properties.getProperty("MAPBOX_ACCESS_TOKEN")}\"",
    )
  }
  buildTypes {
    debug {
      isMinifyEnabled = false
    }
    release {
      isMinifyEnabled = false
    }
  }
  flavorDimensions += "environment"
  productFlavors {
    create("development") {
      dimension = "environment"
      applicationIdSuffix = ".dev"
      versionNameSuffix = "-dev"
    }
    create("production") {
      dimension = "environment"
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
      exclude(libs.lifecycle.viewmodel.savedstate)
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
  ksp(libs.compose.destinations.ksp)

  implementation(platform(libs.coil.bom))
  implementation(platform(libs.compose.bom))
  implementation(platform(libs.koin.bom))

  implementation(project(":data"))
  implementation(project(":features"))
  implementation(project(":navigation"))
  implementation(project(":ui-common"))
  implementation(project(":util"))
  implementation(project(":util:app-version"))
  implementation(project(":util:view"))
  implementation(libs.activity)
  implementation(libs.activity.compose)
  implementation(libs.coil)
  implementation(libs.coil.base)
  implementation(libs.compose.destinations.core)
  implementation(libs.core)
  implementation(libs.foundation.layout.android)
  implementation(libs.koin.android)
  implementation(libs.koin.compose)
  implementation(libs.koin.core)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.lifecycle.common)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.logcat)
  implementation(libs.mapbox.common)
  implementation(libs.material3)
  implementation(libs.navigation.common)
  implementation(libs.navigation.compose)
  implementation(libs.navigation.runtime)
  implementation(libs.runtime.android)
  implementation(libs.ui.android)
  implementation(libs.ui.graphics.android)

  debugImplementation(libs.leakcanary.android)

  debugRuntimeOnly(libs.ui.test.manifest)
  debugRuntimeOnly(libs.ui.tooling)

  testImplementation(libs.junit.jupiter)
  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.koin.test)
  testImplementation(libs.lifecycle.viewmodel.savedstate)
}
