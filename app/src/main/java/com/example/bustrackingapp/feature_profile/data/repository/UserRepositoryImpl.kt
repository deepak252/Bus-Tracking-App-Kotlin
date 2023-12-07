package com.example.bustrackingapp.feature_profile.data.repository

import com.example.bustrackingapp.feature_profile.data.remote.api.UserApiService
import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_profile.domain.repository.UserRepository
import com.example.bustrackingapp.feature_profile.data.remote.dto.UserDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userApiService : UserApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getUser(): ApiResponse<UserDto> = withContext(defaultDispatcher){
        userApiService.getUser()
    }

}