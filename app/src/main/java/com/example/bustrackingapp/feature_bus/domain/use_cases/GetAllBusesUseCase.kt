package com.example.bustrackingapp.feature_bus.domain.use_cases

import com.example.bustrackingapp.core.util.ApiHandler
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute
import com.example.bustrackingapp.feature_bus.domain.repository.BusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBusesUseCase @Inject constructor(
    private val busRepository: BusRepository
) : ApiHandler(){
    operator fun invoke() : Flow<Resource<List<BusWithRoute>>> = makeRequest(
        apiCall = {
            busRepository.getAllBuses()
        }
    )
}