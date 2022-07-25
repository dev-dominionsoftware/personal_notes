plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.googleServices)
    id(Plugins.firebaseCrashlytics)
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.mksoftware101.personalnotes"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(Deps.coreKtx)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)

    // Logging
    implementation(Deps.logging)

    // Firebase
    implementation(platform(Deps.firebaseBom))
    implementation(Deps.firebaseAnalyticsKtx)
    implementation(Deps.firebaseCrashlyticsKtx)

    // Tests
    testImplementation(TestDeps.junit)
    androidTestImplementation(TestDeps.androidExtJunit)
    androidTestImplementation(TestDeps.espresso)
}