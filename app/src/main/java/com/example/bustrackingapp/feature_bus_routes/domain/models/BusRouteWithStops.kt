package com.example.bustrackingapp.feature_bus_routes.domain.models

import com.example.bustrackingapp.core.domain.models.BusStop
import com.example.bustrackingapp.core.domain.models.Timing

data class BusRouteWithStops(
    val _id: String,
    val routeNo: String,
    val name: String,
    val stops: List<BusStop>,
    val timings: List<Timing>,
    val rating: Int,
    val createdAt: String,
    val updatedAt: String
)