plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id ("realm-android")
}

android {
    namespace = "com.example.assignmentfor8k"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.assignmentfor8k"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation(libs.androidx.recyclerview)

    // j unit testing -
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("com.google.truth:truth:1.4.2")

    // retrofit dependency
    implementation("com.squareup.retrofit2:retrofit:2.10.0") // Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    // webpage made offline

    implementation("com.github.diebietse:webpage-downloader:0.2.0")

    // room database dependency
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // lifecycle and viewModel

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0") // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    kapt("androidx.lifecycle:lifecycle-common-java8:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")


    val bumTech_version = "4.12.0"

    implementation("com.github.bumptech.glide:glide:$bumTech_version")  // Glide
    kapt("com.github.bumptech.glide:compiler:$bumTech_version")

    implementation("androidx.webkit:webkit:1.10.0")

    // Dependency injection with dagger

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")

    kapt("androidx.hilt:hilt-compiler:1.1.0")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.8.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

}