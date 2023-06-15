package com.example.bustrackingapp.feature_bus_stop.data.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes
import retrofit2.http.GET
import retrofit2.http.Query


interface BusStopApiService {

    @GET("busStop/getAllBusStops")
    suspend fun getAllBusStops() : ApiResponse<List<BusStopWithRoutes>>

    @GET("busStop/getNearbyBusStops")
    suspend fun getNearbyBusStops(
        @Query("lat") lat : Double,
        @Query("lng") lng : Double
    ) : ApiResponse<List<BusStopWithRoutes>>

    @GET("busStop/getBusStop")
    suspend fun getBusStop(@Query("stopNo") stopNo : String) : ApiResponse<BusStopWithRoutes>
}