import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.util.Properties

plugins {
  `android-library-convention`
  id(libs.plugins.apollo.get().pluginId)
  id(libs.plugins.ksp.get().pluginId)
}
android {
  namespace = "com.routesearch.network"

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
  buildTypes {
    debug {
      buildConfigField("String", "API_URL", "\"https://stg-api.openbeta.io/\"")
    }
    release {
      buildConfigField("String", "API_URL", "\"https://api.openbeta.io/\"")
    }
  }
}
apollo {
  service("open_beta") {
    packageName.set("com.routesearch.network")
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

  implementation(libs.apollo.api)
  implementation(libs.apollo.runtime)
  implementation(libs.typesense.java)

  testImplementation(libs.junit.jupiter)
}
