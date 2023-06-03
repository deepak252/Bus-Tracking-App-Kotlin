package com.example.bustrackingapp.feature_auth.domain.repository

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_auth.data.remote.dto.response.AuthResponse
import com.example.bustrackingapp.feature_auth.data.remote.dto.request.SignInUserRequest
import com.example.bustrackingapp.feature_auth.data.remote.dto.request.SignUpUserRequest

interface AuthRepository {
    suspend fun signInUser(requestBody : SignInUserRequest) : ApiResponse<AuthResponse>
    suspend fun signUpUser(requestBody : SignUpUserRequest) : ApiResponse<AuthResponse>
}