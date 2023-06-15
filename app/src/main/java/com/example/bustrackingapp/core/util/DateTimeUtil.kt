package com.example.bustrackingapp.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {
    val logger = LoggerUtil(c = "DateTimeUtil")
    fun getDateTime(){
        val currentMoment = Clock.System.now()
        val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        logger.info(datetimeInUtc, "getDateTime")
    }

}