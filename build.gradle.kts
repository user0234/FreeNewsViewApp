// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    val kotlin_version = "1.8.0"
    repositories {
        google()
        mavenCentral()

    }

    dependencies {

        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}

