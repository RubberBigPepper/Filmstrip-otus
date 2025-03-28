plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.akmanaev.filmstrip"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.akmanaev.filmstrip"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.espresso.contrib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.androidx.constraint.layout)

    implementation(libs.tickaroo.core)
    implementation(libs.tickaroo.converter)
    implementation(libs.tickaroo.annotation)
    implementation(libs.tickaroo.htmlescape)

    kapt(libs.tickaroo.processor)
    kapt(libs.tickaroo.processor.common)
    kapt(libs.tickaroo.xml)

    implementation(libs.okhttp.logging)
    implementation(libs.glide)

    //https://github.com/Marvel999/Android-Loading-Animation
    implementation(libs.android.loading.animation)
    
    implementation(libs.glide)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // https://github.com/denzcoskun/ImageSlideshow?tab=readme-ov-file
    implementation(libs.imageslideshow)

    testImplementation(libs.mockwebserver)
}

kapt {
    useBuildCache = false
    correctErrorTypes = true
}