// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
//}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Top-level build.gradle.kts

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    id("com.android.library") version libs.versions.agp.get() apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
