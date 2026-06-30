import java.util.Properties

plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.jacoco)
    alias(libs.plugins.nowinandroid.hilt)
    id("kotlinx-serialization")
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

val rapidApiKey = localProperties.getProperty("x-rapidapi-key") ?: ""

val baseUrl = "https://imdb236.p.rapidapi.com/api/imdb/"

android {
    defaultConfig {
        buildConfigField(
            "String",
            "BASE_URL",
            "\"$baseUrl\"",
        )

        buildConfigField(
            "String",
            "API_KEY",
            "\"$rapidApiKey\"",
        )
    }
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.smmousavi.i_core.network"
    testOptions.unitTests.isIncludeAndroidResources = true

}

dependencies {
    api(libs.kotlinx.datetime)
    api(libs.retrofit.core)

    implementation(projects.iCore.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.kotlinx.coroutines.test)
}