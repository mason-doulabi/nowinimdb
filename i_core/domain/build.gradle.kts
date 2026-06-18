plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.jacoco)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.smmousavi.domain"

}

dependencies {
    api(projects.iCore.model)

    implementation(libs.javax.inject)

    testImplementation(projects.core.testing)
}