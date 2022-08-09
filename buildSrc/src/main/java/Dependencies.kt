object Deps {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val coreSplashScreen = "androidx.core:core-splashscreen:${Versions.coreSplashScreen}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val logging = "com.jakewharton.timber:timber:${Versions.timber}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    object BindingCollectionAdapter {
        const val core =
            "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:${Versions.bindingCollectionAdapter}"
        const val recyclerView =
            "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:${Versions.bindingCollectionAdapter}"
    }

    object Navigation {
        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Lifecycle {
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"
        const val crashlyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
    }

    object DI {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object DB {
        const val room = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    }
}

object TestDeps {
    const val junit = "junit:junit:${TestVersions.junit}"
    const val androidExtJunit = "androidx.test.ext:junit:${TestVersions.androidExtJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${TestVersions.espressoCore}"
}

object BuildScriptDeps {
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val firebaseCrashlyticsGradle =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsGradle}"
    const val hiltAndroidGradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}