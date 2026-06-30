plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.compose)
    alias(libs.plugins.nowinandroid.android.library.jacoco)
    alias(libs.plugins.nowinandroid.hilt)
}

android {
    namespace = "com.smmousavi.i_core.presentation"
}

dependencies {
    api(projects.iCore.common)
    api(libs.androidx.metrics)
    api(projects.core.analytics)
    api(projects.iCore.designsystem)
    api(projects.iCore.model)

    implementation(libs.androidx.browser)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
    androidTestImplementation(projects.core.testing)
}