plugins {
    alias(libs.plugins.nowinandroid.android.feature.impl)
    alias(libs.plugins.nowinandroid.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.smmousavi.i_feature.search.impl"
}

dependencies {
    api(projects.iCore.domain)
    api(projects.iCore.designsystem)
    api(projects.iCore.presentation)
    api(projects.iCore.testing)

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)


    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}