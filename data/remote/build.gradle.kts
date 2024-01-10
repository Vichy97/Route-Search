import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.util.Properties

plugins {
  `android-library-convention`
  id(libs.plugins.apollo.get().pluginId)
  id(libs.plugins.ksp.get().pluginId)
}
android {
  namespace = "com.routesearch.data.remote"

  buildFeatures {
    buildConfig = true
  }
  defaultConfig {
    val localPropertiesFile = project.rootProject.file("local.properties")
    val properties = Properties()
    properties.load(localPropertiesFile.inputStream())

    buildConfigField(
      type = "String",
      name = "TYPE_SENSE_API_KEY",
      value = properties.getProperty("TYPE_SENSE_API_KEY"),
    )
    buildConfigField(
      type = "String",
      name = "TYPE_SENSE_HOST",
      value = properties.getProperty("TYPE_SENSE_HOST"),
    )
  }
  productFlavors {
    getByName("development") {
      buildConfigField(
        type = "String",
        name = "API_URL",
        value = "\"https://stg-api.openbeta.io/\"",
      )
    }
    getByName("production") {
      buildConfigField(
        type = "String",
        name = "API_URL",
        value = "\"https://api.openbeta.io/\"",
      )
    }
  }
}
apollo {
  service("open_beta") {
    packageName.set("com.routesearch.data.remote")
  }
  generateKotlinModels.set(true)
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
  ksp(libs.moshi.kotlin.codegen)

  api(libs.koin.core)
  api(libs.moshi)

  implementation(platform(libs.koin.bom))

  implementation(project(":util:common"))

  implementation(libs.apollo.api)
  implementation(libs.apollo.runtime)
  implementation(libs.logcat)
  implementation(libs.typesense.java)

  testImplementation(libs.junit.jupiter)
}
