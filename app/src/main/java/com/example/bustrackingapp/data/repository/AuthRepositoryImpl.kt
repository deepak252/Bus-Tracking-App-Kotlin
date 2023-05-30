package com.example.bustrackingapp.data.repository

import com.example.bustrackingapp.common.NetworkRequest
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.data.remote.api.AuthApiService
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.model.request.SignUpUserRequestBody
import com.example.bustrackingapp.domain.model.response.SignInUserResponse
import com.example.bustrackingapp.domain.model.response.SignUpUserResponse
import com.example.bustrackingapp.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : AuthRepository, NetworkRequest() {

    override suspend fun signInUser(requestBody: SignInUserRequestBody): NetworkResult<SignInUserResponse> {
        return withContext(defaultDispatcher){
            getResult {
                authApiService.signInUser(requestBody)
            }
        }
    }

    override suspend fun signUpUser(requestBody: SignUpUserRequestBody): NetworkResult<SignUpUserResponse> {
        return withContext(defaultDispatcher){
            getResult {
                authApiService.signUpUser(requestBody)
            }
        }
    }
}