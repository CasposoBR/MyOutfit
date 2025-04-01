plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Para Hilt funcionar corretamente
    id("com.google.gms.google-services") version "4.4.2"
}

android {
    namespace = "com.example.myoutfit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myoutfit"
        minSdk = 26
        targetSdk = 34
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // 🔹 Firebase (BoM já gerencia versões)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.analytics)

    // 🔹 Google Services
    implementation(libs.play.services.auth.v2070)

    // 🔹 Hilt
    implementation(libs.hilt.android.v244)
    implementation(libs.androidx.appcompat)
    kapt(libs.hilt.android.compiler.v244)

    // 🔹 Jetpack Compose + Material3
    implementation(platform(libs.androidx.compose.bom.v20240202))
    implementation(libs.androidx.activity.compose.v182)
    implementation(libs.androidx.lifecycle.viewmodel.compose.v262)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)

    // 🔹 Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
}