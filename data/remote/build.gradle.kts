import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  `android-library-convention`
  id(libs.plugins.apollo.get().pluginId)
}
android {
  namespace = "com.routesearch.network"

  buildFeatures {
    buildConfig = true
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
  api(libs.koin.core)

  implementation(libs.apollo.api)
  implementation(libs.apollo.runtime)

  testImplementation(libs.junit.jupiter)
}