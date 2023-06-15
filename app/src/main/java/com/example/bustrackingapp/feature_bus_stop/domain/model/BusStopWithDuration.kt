package com.example.bustrackingapp.feature_bus_stop.domain.model

import com.example.bustrackingapp.core.domain.models.BusStop

data class BusStopWithDuration(
    val _id : String,
    val stop : BusStop,
    val duration : Int,
)