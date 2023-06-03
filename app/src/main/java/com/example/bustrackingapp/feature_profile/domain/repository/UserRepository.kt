package com.example.bustrackingapp.feature_profile.domain.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_profile.data.remote.dto.UserDto


interface UserRepository {
    suspend fun getUser() : ApiResponse<UserDto>
}
//
//interface UserRepository {
//    suspend fun getUser() : NetworkResult<User>
//}