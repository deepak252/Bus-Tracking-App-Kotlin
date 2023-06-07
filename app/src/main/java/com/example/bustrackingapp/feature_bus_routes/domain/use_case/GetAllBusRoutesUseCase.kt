package com.example.bustrackingapp.feature_bus_routes.domain.use_case

import com.example.bustrackingapp.core.util.ApiHandler
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_routes.domain.repository.BusRouteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBusRoutesUseCase @Inject constructor(
    private val busRouteRepository: BusRouteRepository
) : ApiHandler(){

    operator fun invoke() : Flow<Resource<List<BusRouteWithStops>>> = makeRequest(
        apiCall = {
            busRouteRepository.getAllBusRoutes()
        }
    )
}