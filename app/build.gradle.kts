@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.compose.compiler)

    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.myoutfit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myoutfit"
        minSdk = 30
        targetSdk = 33 // Atualizado para 33, já que você está utilizando recursos do Android 13
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.name.contains("kotlin-stdlib")) {
            useVersion("2.1.0")
            because("Evitar conflitos com versões futuras do Kotlin")
        }
    }
}

dependencies {
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)

    // Google Auth
    implementation(libs.play.services.auth)
    implementation(libs.credentials)
    implementation(libs.googleid)
    implementation (libs.play.services.ads)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.foundation.layout.android)
    implementation(libs.animation.core.android)
    implementation(libs.junit.ktx)
    implementation(libs.core.ktx)
    implementation(libs.core.ktx)
    implementation(libs.core.ktx)
    implementation(libs.core.ktx)
    implementation(libs.core.ktx)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.mediation.test.suite)
    implementation(libs.firebase.auth)
    implementation(libs.credentials.play.services.auth)
    testImplementation(libs.testng)
    androidTestImplementation(libs.testng)
    androidTestImplementation(libs.core.testing)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Room
    implementation(libs.room.runtime.android)  // Room Runtime
    implementation(libs.room.ktx)              // Room KTX
    ksp(libs.room.compiler)            // 'room-compiler' para geração de implementações

    // Compose + Material3

    implementation(libs.coil.compose)
    implementation(libs.navigation.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.compose.ui)
    implementation(libs.material3)
    implementation(libs.ui.text.google.fonts)
    implementation(libs.compose.ui.tooling.preview)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //TESTES
    // Coroutines para testes
    testImplementation(platform(libs.kotlinx.coroutines.bom)) // <- BOM dentro de testImplementation!
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.core.testing)
    testImplementation(libs.mockito.kotlin.v540)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core.v351)
    androidTestImplementation(libs.junit.v121)
    // Kotlin
    implementation(libs.kotlin.stdlib)
}