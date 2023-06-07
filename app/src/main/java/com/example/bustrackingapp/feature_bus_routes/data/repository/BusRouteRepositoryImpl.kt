package com.example.bustrackingapp.feature_bus_routes.data.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_bus_routes.data.remote.api.BusRouteApiService
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_routes.domain.repository.BusRouteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class BusRouteRepositoryImpl(
    private val routeApiService : BusRouteApiService,
    private val defaultDispatcher : CoroutineDispatcher
) : BusRouteRepository {

    override suspend fun getAllBusRoutes(): ApiResponse<List<BusRouteWithStops>>  = withContext(defaultDispatcher){
        routeApiService.getAllBusRoutes()
    }

    override suspend fun getBusRoute(routeNo: String): ApiResponse<BusRouteWithStops> = withContext(defaultDispatcher){
        routeApiService.getBusRoute(routeNo)
    }

}