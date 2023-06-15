package com.example.bustrackingapp.feature_bus_stop.data.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_stop.data.api.BusStopApiService
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes
import com.example.bustrackingapp.feature_bus_stop.domain.repository.BusStopRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BusStopRepositoryImpl(
    private val stopApiService: BusStopApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BusStopRepository {
    override suspend fun getAllBusStops(): ApiResponse<List<BusStopWithRoutes>> = withContext(defaultDispatcher){
        stopApiService.getAllBusStops()
    }

    override suspend fun getNearbyBusStops(
        lat: Double,
        lng: Double
    ): ApiResponse<List<BusStopWithRoutes>> = withContext(defaultDispatcher){
        stopApiService.getNearbyBusStops(lat,lng)
    }

    override suspend fun getBusStop(stopNo : String): ApiResponse<BusStopWithRoutes> = withContext(defaultDispatcher){
        stopApiService.getBusStop(stopNo)
    }
}