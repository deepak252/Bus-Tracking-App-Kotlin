package com.example.bustrackingapp.core.util

import android.util.Log
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops

object BusRouteUtil {

    fun getScheduleForDay(
        busRoute : BusRouteWithStops,
        selectedDay : String
    ) : List<List<String>>{
        val list = mutableListOf<MutableList<String>>()
        val departureTimings = busRoute.schedule.firstOrNull{
            it.day==selectedDay // for selected day
        }?.departureTime

        departureTimings?.forEachIndexed{i,time->
            var duration = 0
            busRoute.stops.forEachIndexed { j, busStopWithDuration ->
                duration+=busStopWithDuration.duration
                val stTime = DateTimeUtil.addDurationToTime(time,duration)
                if(i==0){
                    list.add(mutableListOf(stTime))
                }else{
                    list[j].add(stTime)
                }
            }
        }
        return list
    }


    fun getNextArrival(
        busRoute : BusRouteWithStops,
    ) : List<String>{
        val list = mutableListOf<String>()
        var timings = getScheduleForDay(
            busRoute, DateTimeUtil.getCurrentDay()
        )
        val currTime =  DateTimeUtil.getCurrentTime()
        timings.forEach { stopTimings->
            for((i,time) in stopTimings.withIndex()){
                if(time>currTime){
                    list.add("$time, Today")
                    break
                }
                if(i==stopTimings.size-1){
                    list.add("NA")
                }
            }
        }

        val nextDay = DateTimeUtil.getDayFromNumber(
            DateTimeUtil.getNumberFromDay(
                DateTimeUtil.getCurrentDay()
            )+1
        )
        if(nextDay!=null){
            timings = getScheduleForDay(
                busRoute, nextDay
            )
            timings.forEachIndexed{ i, stopTimings->
                if(list[i]=="NA"&& stopTimings.isNotEmpty()){
                    list[i] = "${stopTimings.first()}, Tomorrow"
                }
            }
        }
        return list
    }

}