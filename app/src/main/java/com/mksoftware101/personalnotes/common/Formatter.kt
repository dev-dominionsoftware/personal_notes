package com.mksoftware101.personalnotes.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Formatter @Inject constructor() {

    companion object {
        const val descriptivePattern = "dd MMMM yyyy"
    }

    fun getISODateFormater() = DateTimeFormatter.ISO_LOCAL_DATE

    fun getISODateTimeFormatter() = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun getDescriptiveFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern(descriptivePattern)
}