package com.example.bustrackingapp.data.repository

import com.example.bustrackingapp.common.NetworkRequest
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.data.remote.api.BusApiService
import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.domain.model.Bus
import com.example.bustrackingapp.domain.repository.BusRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BusRepositoryImpl(
    private val busApiService: BusApiService,
    private val defaultDispatcher : CoroutineDispatcher
) : BusRepository, NetworkRequest() {
    override suspend fun getBusByVehNo(vehNo: String) : NetworkResult<Bus> {
        return withContext(defaultDispatcher){
            getResult {
                busApiService.getBusByVehNo(vehNo)
            }
        }
    }
}