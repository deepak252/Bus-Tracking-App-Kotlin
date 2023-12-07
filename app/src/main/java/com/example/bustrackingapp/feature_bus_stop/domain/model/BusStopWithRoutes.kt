package com.example.bustrackingapp.feature_bus_stop.domain.model

import com.example.bustrackingapp.core.domain.models.BusRoute
import com.example.bustrackingapp.core.domain.models.Location

data class BusStopWithRoutes(
    val _id: String,
    val stopNo: String,
    val name: String,
    val location: Location,
    val routes: List<BusRoute>,
    val createdAt: String,
    val updatedAt: String
)
