package com.example.bustrackingapp.data.remote.api

import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.domain.model.Bus
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BusApiService{
    @GET("bus/getBusByVehicleNo")
    suspend fun getBusByVehNo(@Query("vehNo") vehNo : String) : ApiResponse<Bus>

}