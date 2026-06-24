plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.hilt)
}

android {
    namespace = "com.smmousavi.i_core.testing"


}

dependencies {
    api(libs.kotlinx.coroutines.test)
    api(projects.iCore.common)
    api(projects.iCore.data)
    api(projects.iCore.model)


    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.datetime)
}