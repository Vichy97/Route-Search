plugins {
  `kotlin-dsl`
}
repositories {
  mavenCentral()
  google()
}
dependencies {
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
  implementation(libs.kotlin.gradle.plugin)
  implementation(libs.gradle.build.tools)
}