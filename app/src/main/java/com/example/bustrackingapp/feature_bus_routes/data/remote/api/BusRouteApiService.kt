package com.example.bustrackingapp.feature_bus_routes.data.remote.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import retrofit2.http.GET
import retrofit2.http.Query

interface BusRouteApiService {
    @GET("busRoute/getAllBusRoutes")
    suspend fun getAllBusRoutes() : ApiResponse<List<BusRouteWithStops>>

    @GET("busRoute/getBusRoute")
    suspend fun getBusRoute(@Query("routeNo") routeNo : String) : ApiResponse<BusRouteWithStops>


}