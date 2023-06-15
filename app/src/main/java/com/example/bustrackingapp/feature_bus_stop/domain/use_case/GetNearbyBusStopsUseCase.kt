package com.example.bustrackingapp.feature_bus_stop.domain.use_case

import com.example.bustrackingapp.core.util.ApiHandler
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes
import com.example.bustrackingapp.feature_bus_stop.domain.repository.BusStopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyBusStopsUseCase @Inject constructor(
    private val busStopRepository: BusStopRepository,
) : ApiHandler() {
    operator fun invoke(
        lat : Double, lng : Double
    ) : Flow<Resource<List<BusStopWithRoutes>>>  = makeRequest(
        apiCall = {
            busStopRepository.getNearbyBusStops(lat,lng)
        }
    )
}