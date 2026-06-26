plugins {
    alias(libs.plugins.nowinandroid.android.feature.api)
}

android {
    namespace = "com.smmousavi.i_feature.profile.api"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}