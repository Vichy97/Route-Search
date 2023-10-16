import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
  id(libs.plugins.androidApplication.get().pluginId) apply false
  id(libs.plugins.androidLibrary.get().pluginId) apply false
  id(libs.plugins.kotlinAndroid.get().pluginId) apply false
  id(libs.plugins.ksp.get().pluginId).version(libs.plugins.ksp.get().version.toString()) apply false
  id(libs.plugins.ktlint.get().pluginId).version(libs.plugins.ktlint.get().version.toString()) apply false
  id(libs.plugins.apollo.get().pluginId).version(libs.plugins.apollo.get().version.toString()) apply false

  id(libs.plugins.dependencyAnalysis.get().pluginId).version(libs.plugins.dependencyAnalysis.get().version.toString())
  id(libs.plugins.detekt.get().pluginId).version(libs.plugins.detekt.get().version.toString())
}
subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")
  apply(plugin = "io.gitlab.arturbosch.detekt")

  configure<KtlintExtension> {
    debug.set(true)
    android.set(true)
  }
  tasks.withType<Detekt>().configureEach {
    reports {
      html.required.set(true)
      xml.required.set(true)
      txt.required.set(true)
      sarif.required.set(true)
      md.required.set(true)
    }
    jvmTarget = JavaVersion.VERSION_17.toString()
    buildUponDefaultConfig = true
    allRules = false
    parallel = true
    config = files("$rootDir/detekt.yml")
  }
}
dependencyAnalysis {
  issues {
    all {
      onAny {
        severity("fail")
      }
    }
  }
  structure {
    val versionCatalogName = "libs"
    val versionCatalog = project.extensions.getByType<VersionCatalogsExtension>().named(versionCatalogName)
    versionCatalog.libraryAliases.forEach { alias ->
      val library = versionCatalog.findLibrary(alias).get()
      map.put(library.get().toString(), "${versionCatalogName}.${alias}")
    }
  }
}
tasks.register<Copy>("copyGitHooks") {
  group = "git hooks"
  description = "Copies the git hooks from /git-hooks to the .git folder."

  from(project.layout.projectDirectory.file("scripts/pre-commit"))
  into(project.layout.projectDirectory.dir(".git/hooks"))
  fileMode = 777
}
tasks.getByPath(":app:preBuild").dependsOn(":copyGitHooks")

