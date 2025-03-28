plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("kotlin-kapt")
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.example.qr_scan_face_demo"
    compileSdk = 34
    ndkVersion = "25.1.8937393"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17) //
    }

    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "com.example.qr_scan_face_demo"
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }


}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

    // CameraX (para el escáner QR)
    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.1.0")

    // BiometricPrompt API
    implementation("androidx.biometric:biometric:1.2.0-alpha04")

    // Room Database
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    // EncryptedSharedPreferences
    implementation("androidx.security:security-crypto:1.1.0-alpha03")

    // CameraX
    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.1.0")

    // MLKit para códigos QR
    implementation("com.google.mlkit:barcode-scanning:17.0.3")

    // AppCompat (esto es CLAVE si estás usando AppCompatActivity)
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("androidx.camera:camera-view:1.1.0")

    implementation("androidx.security:security-crypto:1.1.0-alpha03")
}

flutter {
    source = "../.."
}
