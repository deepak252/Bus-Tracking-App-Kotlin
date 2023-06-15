package com.example.bustrackingapp.feature_bus_stop.domain.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes

interface BusStopRepository {
    suspend fun getAllBusStops() : ApiResponse<List<BusStopWithRoutes>>
    suspend fun getNearbyBusStops(lat : Double, lng : Double) : ApiResponse<List<BusStopWithRoutes>>
    suspend fun getBusStop(stopNo : String) : ApiResponse<BusStopWithRoutes>
}