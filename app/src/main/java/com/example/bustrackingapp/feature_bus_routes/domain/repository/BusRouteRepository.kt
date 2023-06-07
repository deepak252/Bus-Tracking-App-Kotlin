package com.example.bustrackingapp.feature_bus_routes.domain.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops

interface BusRouteRepository {
    suspend fun getAllBusRoutes() : ApiResponse<List<BusRouteWithStops>>
    suspend fun getBusRoute(routeNo : String) : ApiResponse<BusRouteWithStops>
}