package com.mksoftware101.personalnotes.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Formatter @Inject constructor() {

    companion object {
        const val descriptivePattern = "dd MMMM yyyy"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getISODateFormater() = DateTimeFormatter.ISO_LOCAL_DATE

    @RequiresApi(Build.VERSION_CODES.O)
    fun getISODateTimeFormatter() = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDescriptiveFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern(descriptivePattern)
}