package com.example.bustrackingapp.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {
    val logger = LoggerUtil(c = "DateTimeUtil")
    fun getDateTime() : LocalDateTime{
        val currentMoment = Clock.System.now()
        val datetime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
//        logger.info(datetime, "getDateTime")
        return datetime
//        datetimeInUtc.dayOfWeek.name
    }
    /**
     * Returns day in format : "sunday", "monday",...
     */
    fun getCurrentDay() : String{
        return getDateTime().dayOfWeek.name.lowercase()
    }

    /**
     * Returns day in format : "sunday", "monday",...
     * sunday:0, monday:1, ...
     */
    fun getDayFromNumber(dayNo : Int) : String?{
        if(dayNo>=0)
            return Constants.days[dayNo%7]
        return null
    }
    /**
     * Returns number ( 0 indexed )
     * sunday:0, monday:1, ...
     */
    fun getNumberFromDay(day : String) : Int{
        return Constants.days.indexOf(day)
    }

    /**
     * Returns time in format : "HH:MM"
     */
    fun getCurrentTime() : String{
        val time = getDateTime().time;
        return String.format("%02d:%02d", time.hour, time.minute)
    }

    /**
     * Takes time - "HH:MM", returns "HH:MM"
     */
    fun addDurationToTime(time: String, durationInSeconds: Int): String {
        val parts = time.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        val totalMinutes = hours * 60 + minutes
        val updatedTotalMinutes = totalMinutes + (durationInSeconds / 60)

        val updatedHours = (updatedTotalMinutes / 60) % 24
        val updatedMinutes = updatedTotalMinutes % 60

        return String.format("%02d:%02d", updatedHours, updatedMinutes)
    }


}