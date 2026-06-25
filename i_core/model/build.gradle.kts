plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.compose)
    id("kotlinx-serialization")
}

android {
    namespace = "com.smmousavi.i_core.model"
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.json)
}