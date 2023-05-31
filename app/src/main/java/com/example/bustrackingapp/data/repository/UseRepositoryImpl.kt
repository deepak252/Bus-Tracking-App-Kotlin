package com.example.bustrackingapp.data.repository

import com.example.bustrackingapp.common.NetworkRequest
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.data.remote.api.UserApiService
import com.example.bustrackingapp.domain.model.User
import com.example.bustrackingapp.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userApiService : UserApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : UserRepository, NetworkRequest() {

    override suspend fun getUser(): NetworkResult<User> {
        return withContext(defaultDispatcher){
            getResult {
                userApiService.getUser()
            }
        }
    }

}