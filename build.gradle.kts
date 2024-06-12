import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
  alias(libs.plugins.apollo) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.ktlint) apply false

  id(libs.plugins.androidApplication.get().pluginId) apply false
  id(libs.plugins.androidLibrary.get().pluginId) apply false
  id(libs.plugins.kotlinAndroid.get().pluginId) apply false

  alias(libs.plugins.dependencyAnalysis)
  alias(libs.plugins.detekt)
  alias(libs.plugins.sortDependencies)
}

// TODO: Stop using subprojects and favor composition using convention plugins
subprojects {
  apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
  apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
  apply(plugin = rootProject.libs.plugins.sortDependencies.get().pluginId)

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
    bundle("vico") {
      includeGroup("com.patrykandpatrick.vico")
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
  description = "Copies git pre-commit hooks from scripts folder to the .git folder."

  from(project.layout.projectDirectory.file("scripts/pre-commit"))
  into(project.layout.projectDirectory.dir(".git/hooks"))
  fileMode = 777
}
tasks.getByPath(":app:preBuild").dependsOn(":copyGitHooks")

