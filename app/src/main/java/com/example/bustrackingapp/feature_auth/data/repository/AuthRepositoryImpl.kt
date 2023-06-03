package com.example.bustrackingapp.feature_auth.data.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_auth.data.remote.api.AuthApiService
import com.example.bustrackingapp.feature_auth.data.remote.dto.response.AuthResponse
import com.example.bustrackingapp.feature_auth.data.remote.dto.request.SignInUserRequest
import com.example.bustrackingapp.feature_auth.data.remote.dto.request.SignUpUserRequest
import com.example.bustrackingapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : AuthRepository{

    override suspend fun signInUser(requestBody: SignInUserRequest): ApiResponse<AuthResponse> =  withContext(defaultDispatcher){
        authApiService.signInUser(requestBody)
    }

    override suspend fun signUpUser(requestBody: SignUpUserRequest): ApiResponse<AuthResponse> = withContext(defaultDispatcher){
        authApiService.signUpUser(requestBody)
    }
}