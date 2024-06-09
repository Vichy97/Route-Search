import java.util.Properties

pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

val properties = Properties().apply {
  file("local.properties").inputStream()
    .use { load(it) }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven {
      url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
      authentication {
        create<BasicAuthentication>("basic")
      }
      credentials {
        username = "mapbox"
        password = properties.getProperty("MAPBOX_ACCESS_TOKEN")
      }
    }
  }
}

rootProject.name = "Route Search"
include(
  ":app",
  ":data",
  ":data:local",
  ":data:remote",
  ":features",
  ":navigation",
  ":ui-common",
  ":util",
  ":util:common",
  ":util:coroutines",
  ":util:view",
  ":util:app-version",
)
