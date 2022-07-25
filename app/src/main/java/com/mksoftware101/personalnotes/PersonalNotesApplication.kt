package com.mksoftware101.personalnotes

import android.app.Application
import timber.log.Timber

class PersonalNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTimberTree())
        }
    }
}