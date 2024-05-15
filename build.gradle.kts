plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
buildscript {
    dependencies {
        // other plugins...
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath (libs.kotlin.serialization)
        classpath (libs.protobuf.gradle.plugin)
    }
}