pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
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
)
