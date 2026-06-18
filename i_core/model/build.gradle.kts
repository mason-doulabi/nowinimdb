plugins {
    alias(libs.plugins.nowinandroid.jvm.library)
    id("kotlinx-serialization")
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.json)
}