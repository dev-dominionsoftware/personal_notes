object Deps {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val logging = "com.jakewharton.timber:timber:${Versions.timber}"
}

object TestDeps {
    const val junit = "junit:junit:${TestVersions.junit}"
    const val androidExtJunit = "androidx.test.ext:junit:${TestVersions.androidExtJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${TestVersions.espressoCore}"
}