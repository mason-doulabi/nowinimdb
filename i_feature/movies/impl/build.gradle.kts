plugins {
    alias(libs.plugins.nowinandroid.android.feature.impl)
    alias(libs.plugins.nowinandroid.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.smmousavi.i_feature.movies.impl"
}

dependencies {
    api(projects.iCore.domain)
    api(projects.iCore.designsystem)
    api(projects.iCore.presentation)

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}