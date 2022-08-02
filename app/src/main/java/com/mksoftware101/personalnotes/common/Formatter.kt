package com.mksoftware101.personalnotes.common

import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Formatter @Inject constructor() {

    companion object {
        const val descriptivePattern = "dd MMMM yyyy"
    }

    fun getISODateFormater(): DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun getISODateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun getDescriptiveFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern(descriptivePattern)
}