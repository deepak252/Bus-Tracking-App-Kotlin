package com.example.bustrackingapp.feature_bus.domain.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute

interface BusRepository {
    suspend fun getBusByVehNo( vehNo : String) : ApiResponse<Bus>
    suspend fun getAllBuses() : ApiResponse<List<BusWithRoute>>
    suspend fun getBusesForRoute(routeNo : String) : ApiResponse<List<BusWithRoute>>
    suspend fun getBusesForStop(stopNo : String) : ApiResponse<List<BusWithRoute>>
    suspend fun getNearbyBuses(lat : Double, lng : Double) : ApiResponse<List<BusWithRoute>>

}