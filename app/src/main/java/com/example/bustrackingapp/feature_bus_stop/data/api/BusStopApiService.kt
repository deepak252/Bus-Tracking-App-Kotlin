package com.example.bustrackingapp.feature_bus_stop.data.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes
import retrofit2.http.GET


interface BusStopApiService {

    @GET("busStop/getAllBusStops")
    suspend fun getAllBusStops() : ApiResponse<List<BusStopWithRoutes>>
}