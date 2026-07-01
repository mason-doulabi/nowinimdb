plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.compose)
    alias(libs.plugins.nowinandroid.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.smmousavi.i_core.designsystem"
    testOptions.unitTests.isIncludeAndroidResources = true
    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    lintPublish(projects.lint)

    api(projects.core.designsystem)
    api(projects.iCore.model)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.coil.kt.compose)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.ui.testManifest)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.screenshotTesting)
}
