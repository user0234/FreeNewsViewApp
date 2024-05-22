// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    val kotlin_version = "1.9.22"
    repositories {
        google()
        mavenCentral()

    }

    dependencies {

        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        kotlin("plugin.serialization")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id ("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}

