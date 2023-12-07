package com.example.bustrackingapp.feature_bus.data.remote.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRouteAndStops
import retrofit2.http.GET
import retrofit2.http.Query

interface BusApiService {

    @GET("bus/getBusByVehicleNo")
    suspend fun getBusByVehicleNo(@Query("vehNo") vehNo : String) : ApiResponse<Bus>

    @GET("bus/getAllBuses")
    suspend fun getAllBuses() : ApiResponse<List<BusWithRoute>>

    @GET("bus/getBusesForRoute")
    suspend fun getBusesForRoute(@Query("routeNo") routeNo : String) : ApiResponse<List<BusWithRoute>>

    @GET("bus/getBusesForStop")
    suspend fun getBusesForStop(@Query("stopNo") stopNo : String) : ApiResponse<List<BusWithRoute>>

    @GET("bus/getNearbyBuses")
    suspend fun getNearbyBuses(
        @Query("lat") lat : Double,
        @Query("lng") lng : Double
    ) : ApiResponse<List<BusWithRoute>>
}

