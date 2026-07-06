plugins {
    alias(libs.plugins.android.application)
    // TAMBAHKAN LINE INI
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.suci_loyalty"

    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.suci_loyalty"
        minSdk = 27
        targetSdk = 36
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Retrofit & OkHttp untuk API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Glide untuk Gambar
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Tambahan untuk Dots Indicator Onboarding
    implementation("com.tbuonomo:dotsindicator:5.1.0")

    // ==========================================
    // TAMBAHKAN DEPENDENSI ROOM DI BAWAH INI
    // ==========================================
    val room_version = "2.7.0"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // ==========================================
    // PERTEMUAN 13: KAMERA, QR CODE & BARCODE SCANNER
    // ==========================================

    // ZXing – Generate QR Code sebagai Bitmap
    implementation("com.google.zxing:core:3.5.3")

    // CameraX – Preview kamera real-time untuk scan QR
    val camerax_version = "1.3.4"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:$camerax_version")

    // ML Kit – Deteksi Barcode & QR Code
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    // Scanner modern praktis tanpa CameraX setup
    implementation("com.google.android.gms:play-services-code-scanner:16.1.0")
}