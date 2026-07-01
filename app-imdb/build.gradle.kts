import com.google.samples.apps.nowinandroid.FlavorDimension
import com.google.samples.apps.nowinandroid.NiaFlavor

plugins {
    alias(libs.plugins.nowinandroid.android.application)
    alias(libs.plugins.nowinandroid.android.application.compose)
    alias(libs.plugins.nowinandroid.hilt)
}

android {
    defaultConfig {
        applicationId = "com.smmousavi.imdb"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        // The UI catalog does not depend on content from the app, however, it depends on modules
        // which do, so we must specify a default value for the contentType dimension.
        missingDimensionStrategy(FlavorDimension.contentType.name, NiaFlavor.demo.name)
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = "com.smmousavi.imdb"

    buildTypes {
        release {
            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.named("debug").get()
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)
    implementation(libs.coil.kt)

    implementation(projects.iCore.common)
    implementation(projects.iCore.data)
    implementation(projects.iCore.database)
    implementation(projects.iCore.designsystem)
    implementation(projects.iCore.navigation)
    implementation(projects.iCore.domain)
    implementation(projects.iCore.model)
    implementation(projects.iCore.navigation)
    implementation(projects.iCore.network)
    implementation(projects.iCore.presentation)

    implementation(projects.iFeature.movies.api)
    implementation(projects.iFeature.movies.impl)

    implementation(projects.iFeature.search.api)
    implementation(projects.iFeature.search.impl)

    implementation(projects.iFeature.profile.api)
    implementation(projects.iFeature.profile.impl)

    implementation(projects.iFeature.details)
}

dependencyGuard {
    configuration("releaseRuntimeClasspath")
}
