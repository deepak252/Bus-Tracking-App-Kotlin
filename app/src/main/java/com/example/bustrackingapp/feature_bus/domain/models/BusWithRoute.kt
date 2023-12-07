package com.example.bustrackingapp.feature_bus.domain.models

import com.example.bustrackingapp.core.domain.models.BusInfo
import com.example.bustrackingapp.core.domain.models.BusRoute
import com.example.bustrackingapp.core.domain.models.Location
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops

data class BusWithRoute(
    val _id: String,
    val vehNo: String,
    val status: String?,
    val info: BusInfo,
    val location: Location?,
    val route: BusRoute,
    val createdAt: String,
    val updatedAt: String,
)