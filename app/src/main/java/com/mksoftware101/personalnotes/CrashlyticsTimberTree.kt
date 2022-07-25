package com.mksoftware101.personalnotes

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsTimberTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority < Log.ERROR) {
            return
        }

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
        crashlytics.setCustomKey(CRASHLYTICS_KEY_PRIORITY_NAME, mapLogLevelToName(priority))
        crashlytics.setCustomKey(CRASHLYTICS_KEY_TAG, tag ?: "")
        crashlytics.setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
        crashlytics.recordException(t ?: Throwable(message))
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_PRIORITY_NAME = "priority_name"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"

        fun mapLogLevelToName(level: Int): String =
            when (level) {
                2 -> "VERBOSE"
                3 -> "DEBUG"
                4 -> "INFO"
                5 -> "WARNING"
                6 -> "ERROR"
                7 -> "ASSERT"
                else -> "NO MAPPING! value was $level"
            }
    }
}