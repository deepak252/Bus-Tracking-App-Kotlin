package com.example.bustrackingapp.feature_bus_routes.domain.models

import com.example.bustrackingapp.core.domain.models.RouteSchedule
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithDuration

data class BusRouteWithStops(
    val _id: String,
    val routeNo: String,
    val name: String,
    val stops: List<BusStopWithDuration>,
    val schedule: List<RouteSchedule>,
    val rating: Int,
    val createdAt: String,
    val updatedAt: String
)