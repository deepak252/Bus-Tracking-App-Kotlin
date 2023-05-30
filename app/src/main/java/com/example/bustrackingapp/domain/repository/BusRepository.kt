package com.example.bustrackingapp.domain.repository

import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.domain.model.Bus

interface BusRepository {
    suspend fun getBusByVehNo( vehNo : String) : NetworkResult<Bus>?
}