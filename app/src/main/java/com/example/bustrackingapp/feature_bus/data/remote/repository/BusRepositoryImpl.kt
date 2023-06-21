package com.example.bustrackingapp.feature_bus.data.remote.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.feature_bus.data.remote.api.BusApiService
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute
import com.example.bustrackingapp.feature_bus.domain.repository.BusRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BusRepositoryImpl(
    private val busApiService : BusApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BusRepository {

    override suspend fun getBusByVehNo(vehNo: String): ApiResponse<Bus> = withContext(defaultDispatcher){
        busApiService.getBusByVehicleNo(vehNo)
    }

    override suspend fun getAllBuses(): ApiResponse<List<BusWithRoute>> = withContext(defaultDispatcher){
        busApiService.getAllBuses()
    }

    override suspend fun getBusesForRoute(routeNo: String): ApiResponse<List<BusWithRoute>> = withContext(defaultDispatcher){
        busApiService.getBusesForRoute(routeNo)
    }

    override suspend fun getBusesForStop(stopNo: String): ApiResponse<List<BusWithRoute>> = withContext(defaultDispatcher){
        busApiService.getBusesForStop(stopNo)
    }

    override suspend fun getNearbyBuses(lat: Double, lng: Double): ApiResponse<List<BusWithRoute>>
        = withContext(defaultDispatcher){
        busApiService.getNearbyBuses(lat,lng)
    }
}