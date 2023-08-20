plugins {
  `android-library-convention`
  id(libs.plugins.apollo.get().pluginId)
}
android {
  namespace = "com.route_search.network"
  
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
    packageName.set("com.route_search.network")
  }
  generateKotlinModels.set(true)
}
dependencies {
  api(libs.koin.core)

  implementation(libs.apollo.runtime)
}