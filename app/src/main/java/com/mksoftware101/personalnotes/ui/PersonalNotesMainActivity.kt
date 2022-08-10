package com.mksoftware101.personalnotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mksoftware101.personalnotes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalNotesMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}