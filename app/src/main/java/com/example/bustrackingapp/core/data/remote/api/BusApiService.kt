package com.example.bustrackingapp.core.data.remote.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.core.domain.models.Bus
import retrofit2.http.GET
import retrofit2.http.Query

interface BusApiService{
    @GET("bus/getBusByVehicleNo")
    suspend fun getBusByVehNo(@Query("vehNo") vehNo : String) : ApiResponse<Bus>

}