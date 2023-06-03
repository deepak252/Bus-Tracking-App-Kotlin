package com.example.bustrackingapp.core.data.repository

//import com.example.bustrackingapp.common.NetworkRequest
//import com.example.bustrackingapp.common.NetworkResult
//import com.example.bustrackingapp.core.data.remote.api.BusApiService
//import com.example.bustrackingapp.core.domain.models.Bus
//import com.example.bustrackingapp.core.domain.repository.BusRepository
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.withContext
//
//class BusRepositoryImpl(
//    private val busApiService: BusApiService,
//    private val defaultDispatcher : CoroutineDispatcher
//) : BusRepository, NetworkRequest() {
//    override suspend fun getBusByVehNo(vehNo: String) : NetworkResult<Bus> {
//        return withContext(defaultDispatcher){
//            getResult {
//                busApiService.getBusByVehNo(vehNo)
//            }
//        }
//    }
//}